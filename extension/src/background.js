/**
 * OBZRAi Chromium MV3 service worker (Phase 2 / S10).
 * Caps captureVisibleTab at ≤ 2/sec. No other-tab reads.
 * Drive OAuth is mocked (no external accounts created).
 */

import {
  matchTopics,
  toThinItem,
  FIXTURE_TOPICS,
} from "../lib/observer-core.js";
import { ThinSyncEngine } from "./thin-sync.js";

const CAPTURE_MIN_INTERVAL_MS = 500;
let lastCaptureAt = 0;
const syncEngine = new ThinSyncEngine();

chrome.runtime.onInstalled.addListener(() => {
  chrome.sidePanel
    .setPanelBehavior({ openPanelOnActionClick: true })
    .catch(() => {});
});

chrome.commands.onCommand.addListener(async (command) => {
  if (command === "save-active-tab") {
    await saveActiveTab();
  }
});

chrome.runtime.onMessage.addListener((msg, _sender, sendResponse) => {
  (async () => {
    if (msg?.type === "SAVE_ACTIVE") {
      sendResponse(await saveActiveTab());
      return;
    }
    if (msg?.type === "LIST_ITEMS") {
      sendResponse({ items: await listItems() });
      return;
    }
    if (msg?.type === "SYNC_NOW") {
      sendResponse(await syncEngine.syncNow());
      return;
    }
    if (msg?.type === "SYNC_STATUS") {
      sendResponse(await syncEngine.status());
      return;
    }
    if (msg?.type === "SET_SYNC_OPT_IN") {
      await syncEngine.setOptIn(!!msg.optIn);
      sendResponse(await syncEngine.status());
      return;
    }
    sendResponse({ ok: false, error: "unknown" });
  })();
  return true;
});

async function saveActiveTab() {
  const now = Date.now();
  if (now - lastCaptureAt < CAPTURE_MIN_INTERVAL_MS) {
    return { ok: false, error: "rate_limited_2_per_sec" };
  }
  lastCaptureAt = now;

  const [tab] = await chrome.tabs.query({ active: true, currentWindow: true });
  if (!tab?.id) return { ok: false, error: "no_active_tab" };

  let dataUrl = null;
  let captureBlocked = false;
  try {
    dataUrl = await chrome.tabs.captureVisibleTab(tab.windowId, {
      format: "jpeg",
      quality: 70,
    });
  } catch {
    captureBlocked = true;
  }

  let pageText = "";
  try {
    const [{ result }] = await chrome.scripting.executeScript({
      target: { tabId: tab.id },
      func: () => {
        const sel = window.getSelection()?.toString() || "";
        const body = document.body?.innerText?.slice(0, 8000) || "";
        return { selection: sel, body, title: document.title || "" };
      },
    });
    pageText = [result?.selection, result?.title, result?.body]
      .filter(Boolean)
      .join("\n");
  } catch {
    pageText = tab.title || "";
  }

  if (!dataUrl) captureBlocked = true;

  const match = matchTopics(pageText, FIXTURE_TOPICS);
  const youtubeT = extractYoutubeTimestamp(tab.url);

  const item = toThinItem({
    id: crypto.randomUUID(),
    createdAtEpochMs: now,
    title: tab.title || "Untitled tab",
    tags: match.matchedTopicIds,
    provenance: {
      url: tab.url || null,
      youtubeTimestampSec: youtubeT,
      device: "extension",
    },
    ocrOrPageText: pageText.slice(0, 12000),
    transcriptText: "",
    voiceIdeaText: "",
    thumbnailBase64: dataUrl && !captureBlocked ? dataUrl : null,
    captureBlocked,
    continuityStatus: "local_only",
  });

  const items = await listItems();
  items.unshift(item);
  await chrome.storage.local.set({ items: items.slice(0, 500) });
  return { ok: true, item, match };
}

async function listItems() {
  const { items } = await chrome.storage.local.get({ items: [] });
  return items;
}

function extractYoutubeTimestamp(url) {
  if (!url) return null;
  if (!url.includes("youtube.com") && !url.includes("youtu.be")) return null;
  try {
    const u = new URL(url);
    const t = u.searchParams.get("t") || u.searchParams.get("start");
    if (!t) return null;
    const sec = Number(String(t).replace(/s$/, ""));
    return Number.isFinite(sec) ? sec : null;
  } catch {
    return null;
  }
}
