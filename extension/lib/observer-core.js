/**
 * Bundled copy of shared/observer-core for MV3 module imports (no build step).
 * Keep in sync with ../../shared/observer-core/index.js (S9).
 */

export function normalizeText(input) {
  return String(input || "")
    .toLowerCase()
    .replace(/\s+/g, " ")
    .trim();
}

export function matchTopics(text, topics, sensitivity = "BALANCED") {
  const hay = normalizeText(text);
  const enabled = (topics || []).filter((t) => t.enabled !== false);
  if (!enabled.length) {
    return { matched: false, matchedTopicIds: [], score: 0 };
  }
  const threshold = sensitivity === "CONSERVATIVE" ? 2 : 1;
  const hits = [];
  for (const topic of enabled) {
    const keys = normalizeText(topic.name)
      .split(/[\s,;/|]+/)
      .filter((k) => k.length >= 2);
    let score = 0;
    for (const k of keys) {
      if (hay.includes(k)) score += 1;
    }
    if (score >= threshold) hits.push({ id: topic.id, score });
  }
  const matchedTopicIds = hits.map((h) => h.id);
  const score = hits.reduce((m, h) => Math.max(m, h.score), 0);
  return { matched: matchedTopicIds.length > 0, matchedTopicIds, score };
}

export function analyzeBlackFrame(imageData, sampleStep = 16) {
  if (!imageData || !imageData.data || !imageData.width) {
    return { blocked: true, meanLuma: 0, notes: "empty" };
  }
  const { data, width, height } = imageData;
  let sum = 0;
  let n = 0;
  let nearBlack = 0;
  for (let y = 0; y < height; y += sampleStep) {
    for (let x = 0; x < width; x += sampleStep) {
      const i = (y * width + x) * 4;
      const r = data[i];
      const g = data[i + 1];
      const b = data[i + 2];
      const luma = 0.2126 * r + 0.7152 * g + 0.0722 * b;
      sum += luma;
      n += 1;
      if (luma < 12) nearBlack += 1;
    }
  }
  const mean = n ? sum / n : 0;
  const blackRatio = n ? nearBlack / n : 1;
  const blocked = mean < 18 && blackRatio > 0.92;
  return {
    blocked,
    meanLuma: mean,
    notes: blocked ? "capture_blocked" : "ok",
  };
}

export function toThinItem(item, thumbMaxBytes = 200 * 1024) {
  const thumb =
    item.thumbnailBase64 && estimateBytes(item.thumbnailBase64) <= thumbMaxBytes
      ? item.thumbnailBase64
      : null;
  return {
    id: item.id,
    createdAtEpochMs: item.createdAtEpochMs,
    title: item.title || "",
    tags: item.tags || [],
    provenance: item.provenance || {},
    ocrOrPageText: item.ocrOrPageText || "",
    transcriptText: item.transcriptText || "",
    voiceIdeaText: item.voiceIdeaText || "",
    thumbnailBase64: thumb,
    captureBlocked: !!item.captureBlocked,
    continuityStatus: item.continuityStatus || "local_only",
  };
}

function estimateBytes(b64) {
  const s = String(b64);
  const comma = s.indexOf(",");
  const raw = comma >= 0 ? s.slice(comma + 1) : s;
  return Math.floor((raw.length * 3) / 4);
}

export const FIXTURE_TOPICS = [
  { id: "gst", name: "GST Tally tax", enabled: true },
  { id: "android", name: "Android Kotlin Compose", enabled: true },
];

export const FIXTURE_PAGE =
  "How to file GST return in Tally Prime with e-invoice notes";
