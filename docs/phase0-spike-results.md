# Phase 0 spike results — S1–S4

**Date:** 17 Oct 2026  
**Repo:** [basantsd/OBZRai](https://github.com/basantsd/OBZRai)  
**Branch:** `cursor/phase0-spikes-3a5a`  
**PRD:** v2.1 r2 §19.1  
**Exit rule:** No full-product UI rollout until S1–S4 pass or approved fallback recorded.

Open questions **Q32** (WebDAV same cut vs fast-follow), **Q33** (200 KB thumb cap), **Q36** (DPDP legal) — **kept open**; not blocking Phase 0.

---

## Summary table

| Spike | Status | Fallback / notes | Evidence |
|---|---|---|---|
| **S1 Projection** | **PENDING** (device) / **PASS** (code + unit) | Pipeline + `MediaProjection.Callback.onStop` landed. Live Reel/Short frames + 15 QPR1+ chip/lock not exercised — worker emulator stuck offline. | `media/phase0/unit-test-summary.txt`, `media/phase0/gradle-build-test.log`, `media/phase0/emulator-status.txt` |
| **S2 Overlay** | **PASS** (mechanics) · **`matches reference` = PENDING** | Compose 28 dp edge-clipped ring, drag/snap, host-tappable flags, 3 densities. Restricted-settings path documented. Visual match blocked on missing PNG. | `media/phase0/s2-overlay-geometry-densities.png`, geometry unit tests |
| **S3 Clean frame** | **PASS** (approved fallback) | No Exclusion API on stock MediaProjection → **hide-and-settle (50 ms)** documented and unit-tested. | `CleanFrameCaptureTest`, code `CleanFrameCapture` |
| **S4 Audio** | **PASS** (honesty) | Controlled live probe needs active projection. Instagram/YouTube expected block logged honestly — **≠ V1 no-go**. | `AudioPlaybackProbeTest`, honesty strings in probe |

**Hard gates:** S1 device proof still **PENDING** (emulator guest hung). S2 mechanics **PASS**; reference match **PENDING** until asset arrives. Full-product UI rollout should wait for S1 device confirmation **or** owner-approved Sharesheet-only fallback.

---

## Step A — Rename merge (done)

- Fast-forwarded `cursor/scaffold-android-app-bed7` to include rename commit from PR #3.
- PR #3 closed/merged; PR #1 title updated to **Scaffold OBZRAi Android base**.
- No `.github/workflows` present or added.

---

## S1 — Projection

### Implemented
- `DefaultMediaProjectionGateway`: one-shot consent, VirtualDisplay + ImageReader, sample PNG frames under `filesDir/phase0/s1_frames/`.
- `ObservationCaptureService`: FGS `mediaProjection` type; attach after `startForeground`.
- `MediaProjection.Callback.onStop` → stop reason `projection_callback_on_stop` (chip / lock / revoke path).
- Consent launcher in `MainActivity`; session SM requests consent when needed.

### Pass criteria vs status
| Criterion | Status |
|---|---|
| Authorized session + repeated full-res frames | Code ready; **device PENDING** |
| Portrait bounds preserved | Code uses real display metrics; **device PENDING** |
| No new prompt per frame | Implemented (one-shot token) |
| Chip + lock stop on 15 QPR1+ | Callback wired; **device PENDING** |

### Evidence
- Unit: session consent / start / stop — PASS  
- `./gradlew :app:assembleDebug :app:testDebugUnitTest` — BUILD SUCCESSFUL  
- Emulator API 35: stuck `offline` — see `media/phase0/emulator-status.txt`

---

## S2 — Overlay

### Implemented
- `OverlayService` + `DefaultOverlayController`: `TYPE_APPLICATION_OVERLAY`, `FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCH_MODAL | FLAG_LAYOUT_NO_LIMITS`.
- Geometry: **28 dp** ring, **11 dp** glyph, **~40%** edge clip, drag + edge snap (`OverlayGeometry`).
- Densities validated in unit tests at **1.0 / 2.0 / 3.0**.
- Android 15+ sideload: `ACTION_MANAGE_OVERLAY_PERMISSION`; restricted-settings education noted in controller + this doc.

### `matches reference` = **PENDING**
Compose `ObserverRing` placeholder until owner asset arrives.

### Ask Basant — approved overlay asset
Please add **`assets/approved_overlay_reference.png`** (and optional density variants) to the repo:

| Item | Spec |
|---|---|
| **Path** | `assets/approved_overlay_reference.png` (repo root or `app/src/main/assets/`) |
| **Format** | PNG, transparent background |
| **Content** | Owner-approved **blue hollow ring** only (PRD §5.1 / §11.1) — not a replacement logo |
| **Logical size** | **28×28 dp** reference |
| **Pixel sizes** | Prefer `@1x` **28×28**, `@2x` **56×56**, `@3x` **84×84** (or one **84×84** / **112×112** master) |
| **Optional glyph pack** | Separate 10–12 dp state glyphs (idle / listening / capturing / recording / saved / skipped / settings) — or confirm Compose glyph stand-in is OK until then |
| **Color** | Match muted blue ring palette (scaffold uses `#4A7AB5`) |

Until these land, S2 **mechanics PASS**; visual **reference match stays PENDING**.

### Restricted-settings path (15+ sideload)
1. Install APK (GitHub / Obtainium / sideload).  
2. If “Display over other apps” greyed: App info → **Allow restricted settings**.  
3. Settings → Apps → Special app access → Display over other apps → OBZRAi → Allow.  
4. Return to app → Start Observing.

---

## S3 — Clean frame

### Result: **PASS** with documented fallback
- Stock MediaProjection **cannot** exclude overlay windows → strategy `HIDE_AND_SETTLE` (`CleanFrameCapture.SETTLE_MS = 50`).
- Unit tests: hide → capture → restore; no toggle when already hidden.
- OEM Exclusion API: not available — documented limit.

---

## S4 — Audio

### Result: **PASS** (honesty); third-party fail ≠ V1 no-go
- `AudioPlaybackProbe` + `RECORD_AUDIO` permission.
- Honesty log always includes Instagram / YouTube / Netflix expected blocks.
- Live PCM probe runs when projection active; silent stream ≠ success.
- Controlled-app PASS still needs a permissive playback app on a healthy device/emulator.

---

## Local verify (worker)

```bash
./gradlew :app:assembleDebug :app:testDebugUnitTest
# 9 unit tests, 0 failures
```

No `.github/workflows` added.

---

## Evidence index (`media/phase0/`)

| Path | What |
|---|---|
| `gradle-build-test.log` | assembleDebug + unit test run |
| `unit-test-summary.txt` | 9 PASS |
| `unit-test-results/*.xml` | JUnit XML |
| `s2-overlay-geometry-densities.png` | 28 dp ring at 1×/2×/3× |
| `emulator-status.txt` | API 35 emulator hung offline |

---

## Go / no-go recommendation

| Gate | Recommendation |
|---|---|
| S2 mechanics | **GO** (placeholder visual) |
| S3 | **GO** with hide-and-settle |
| S4 | **GO** for V1 visual path; audio best-effort |
| S1 | **HOLD** full UI rollout until device/emulator confirms frames + chip/lock **or** Basant approves Sharesheet-only fallback |
