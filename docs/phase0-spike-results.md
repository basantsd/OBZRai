# Phase 0 spike results — S1–S4

**Date:** 17 Oct 2026 (closeout same day)  
**Repo:** [basantsd/OBZRai](https://github.com/basantsd/OBZRai)  
**Branch:** `cursor/phase0-spikes-3a5a` · Draft [#4](https://github.com/basantsd/OBZRai/pull/4)  
**PRD:** v2.1 r2 §19.1  
**Owner:** Basant voice APPROVED 17 Oct 2026 — **stop holding; continue all phases.** Sharesheet interim approved for S1.

---

## Summary table (closeout)

| Spike | Status | Fallback / notes | Evidence |
|---|---|---|---|
| **S1 Projection** | **PASS (interim)** · chip/lock **PENDING verification** | **Sharesheet fallback approved as interim** (Basant, 17 Oct 2026). Real MediaProjection pipeline remains in tree; do **not** claim chip/lock passed. | `media/phase0/*`, Sharesheet receiver in Phase 1 |
| **S2 Overlay** | **PASS** (mechanics) · **`matches reference` = PENDING** | Compose placeholder until `assets/approved_overlay_reference.png` | `media/phase0/s2-overlay-geometry-densities.png` |
| **S3 Clean frame** | **PASS** (approved fallback) | Hide-and-settle 50 ms | `CleanFrameCaptureTest` |
| **S4 Audio** | **PASS** (honesty) | Third-party block ≠ V1 no-go | `AudioPlaybackProbeTest` |

**Phase 0 exit:** Closed for continued build under Sharesheet interim + documented S2/S3/S4 outcomes. Full-product UI **authorized to proceed** (Basant).

---

## Assumptions locked at Phase 0 closeout (were open Qs)

| ID | Assumption (safest default) | Status |
|---|---|---|
| **Q32** | WebDAV is **Phase 2 fast-follow after Drive `appDataFolder`** — not a same-cut Phase 2 exit blocker | **ASSUMPTION** |
| **Q33** | Thin-sync thumbnail cap **≤ 200 KB JPEG** | **ASSUMPTION** |
| **Q36** | Phases 1–2 stay **local-first / ciphertext / no-account**. **Counsel / DPDP review PENDING** before any account or hosted relay | **ASSUMPTION** · counsel **PENDING** |

These do **not** reopen Phase 0 gates.

---

## Step A — Rename merge (done)

- Fast-forwarded `cursor/scaffold-android-app-bed7` with PR #3 rename.
- PR #3 superseded/merged; PR #1 = OBZRAi scaffold.
- No `.github/workflows`.

---

## S1 — Projection (closeout)

### Implemented
- Real MediaProjection + ImageReader pipeline + `Callback.onStop`.
- **Sharesheet interim** (`ACTION_SEND` image/text/URL) — owner-approved path while chip/lock stays unverified on device.

### Pass criteria vs status
| Criterion | Status |
|---|---|
| Authorized session + repeated full-res frames | Code ready; live device **PENDING** |
| Portrait bounds / no per-frame prompt | Implemented in code |
| Chip + lock stop on 15 QPR1+ | Callback wired; **PENDING verification — do not claim passed** |
| Interim continuity | **Sharesheet approved** (Basant 17 Oct 2026) |

---

## S2 — Overlay

Mechanics **PASS**. **`matches reference` = PENDING** until owner PNG:

| Item | Spec |
|---|---|
| Path | `assets/approved_overlay_reference.png` |
| Format | PNG, transparent |
| Size | 28×28 dp; prefer 28 / 56 / 84 px (@1x/@2x/@3x) |
| Content | Approved blue hollow ring only |

Restricted-settings path (15+ sideload) documented in prior section.

---

## S3 / S4

Unchanged: S3 hide-and-settle **PASS**; S4 honesty **PASS**.

---

## Local verify

```bash
./gradlew :app:assembleDebug :app:testDebugUnitTest
```

No `.github/workflows`.

---

## Evidence index (`media/phase0/`)

| Path | What |
|---|---|
| `gradle-build-test.log` | assembleDebug + unit tests |
| `unit-test-summary.txt` | 9 PASS |
| `unit-test-results/*.xml` | JUnit XML |
| `s2-overlay-geometry-densities.png` | 28 dp @ 1×/2×/3× |
| `emulator-status.txt` | API 35 emulator hung offline |
| `phase0-closeout.txt` | Closeout stamp |

---

## Go / no-go (updated)

| Gate | Decision |
|---|---|
| S1 | **GO via Sharesheet interim**; chip/lock **PENDING** |
| S2 mechanics | **GO**; reference match **PENDING** |
| S3 / S4 | **GO** |
| Full UI / Phase 1+ | **AUTHORIZED** (Basant voice, 17 Oct 2026) |
