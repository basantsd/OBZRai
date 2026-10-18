import assert from "node:assert/strict";
import {
  FIXTURE_PAGE,
  FIXTURE_TOPICS,
  analyzeBlackFrame,
  matchTopics,
  toThinItem,
} from "./index.js";

const match = matchTopics(FIXTURE_PAGE, FIXTURE_TOPICS);
assert.equal(match.matched, true);
assert.ok(match.matchedTopicIds.includes("gst"));

const black = {
  width: 8,
  height: 8,
  data: new Uint8ClampedArray(8 * 8 * 4),
};
assert.equal(analyzeBlackFrame(black).blocked, true);

const thin = toThinItem({
  id: "1",
  createdAtEpochMs: 1,
  title: "t",
  thumbnailBase64: "a".repeat(300_000),
});
assert.equal(thin.thumbnailBase64, null);

console.log("observer-core fixtures OK");
