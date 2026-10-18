const listEl = document.getElementById("list");
const emptyEl = document.getElementById("empty");
const searchEl = document.getElementById("search");
const continuityEl = document.getElementById("continuity");
const syncOptIn = document.getElementById("sync-optin");
const syncMsg = document.getElementById("sync-msg");

document.getElementById("btn-save").addEventListener("click", async () => {
  const res = await chrome.runtime.sendMessage({ type: "SAVE_ACTIVE" });
  syncMsg.textContent = res?.ok
    ? res.item?.captureBlocked
      ? "Saved as capture_blocked (no false screenshot Saved)"
      : "Saved"
    : `Save failed: ${res?.error || "unknown"}`;
  await refresh();
});

document.getElementById("btn-refresh").addEventListener("click", refresh);
document.getElementById("btn-sync").addEventListener("click", async () => {
  const res = await chrome.runtime.sendMessage({ type: "SYNC_NOW" });
  syncMsg.textContent = res?.message || JSON.stringify(res);
  await refreshStatus();
});

syncOptIn.addEventListener("change", async () => {
  await chrome.runtime.sendMessage({
    type: "SET_SYNC_OPT_IN",
    optIn: syncOptIn.checked,
  });
  await refreshStatus();
});

searchEl.addEventListener("input", () => render(window.__items || []));

async function refresh() {
  const res = await chrome.runtime.sendMessage({ type: "LIST_ITEMS" });
  window.__items = res?.items || [];
  render(window.__items);
  await refreshStatus();
}

async function refreshStatus() {
  const st = await chrome.runtime.sendMessage({ type: "SYNC_STATUS" });
  syncOptIn.checked = !!st?.optIn;
  continuityEl.textContent = st?.continuity || "local_only";
  continuityEl.classList.toggle("synced", st?.continuity === "synced_ciphertext");
}

function render(items) {
  const q = searchEl.value.trim().toLowerCase();
  const filtered = !q
    ? items
    : items.filter((it) => {
        const hay = [
          it.title,
          it.ocrOrPageText,
          (it.tags || []).join(" "),
          it.voiceIdeaText,
          it.provenance?.url,
        ]
          .join(" ")
          .toLowerCase();
        return hay.includes(q);
      });
  listEl.innerHTML = "";
  emptyEl.classList.toggle("show", filtered.length === 0);
  for (const it of filtered) {
    const li = document.createElement("li");
    const title = document.createElement("div");
    title.className = "title";
    title.textContent = it.title || "Untitled";
    const meta = document.createElement("div");
    meta.className = "meta";
    meta.innerHTML = [
      new Date(it.createdAtEpochMs).toLocaleString(),
      it.provenance?.url ? escapeHtml(it.provenance.url) : "URL unknown",
      it.captureBlocked ? '<span class="blocked">capture_blocked</span>' : null,
      (it.tags || []).length ? `#${it.tags.join(",")}` : null,
      it.continuityStatus,
    ]
      .filter(Boolean)
      .join(" · ");
    li.appendChild(title);
    li.appendChild(meta);
    listEl.appendChild(li);
  }
}

function escapeHtml(s) {
  return String(s)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;");
}

refresh();
