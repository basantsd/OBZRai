# Phase 1e — stop / DRM / device matrix (draft)

**Not a store release.** Draft documentation only.

## Stop paths (FR-016 / S7)

| Trigger | Expected | Code path | Device verify |
|---|---|---|---|
| User Stop Observing | FGS stops; overlay hides; buffers released | `ObservationSessionController.stop` | PENDING |
| Status-bar chip (15 QPR1+) | `MediaProjection.Callback.onStop` | `DefaultMediaProjectionGateway` | **PENDING verification** |
| Device lock (15 QPR1+) | Same onStop | same | **PENDING** |
| Permission revoke | Session ends | onStop / SecurityException | PENDING |
| Process kill | No stale FGS after relaunch | OS | PENDING |

## DRM / FLAG_SECURE (FR-052 / FR-055 / S18)

| Case | Expected | Status |
|---|---|---|
| Black/blank frame | `capture_blocked`; no green Saved for screenshot | Unit-tested (`BlackFrameDetectorTest`) |
| Bypass APIs | Forbidden — none shipped | PASS (code review) |
| Idea note without frame | Allowed with blocked label | Implemented via ItemSaver |

## Sideload overlay (FR-057)

Documented in Settings Trust copy + Phase 0 results.

## Signed beta

**Not produced** this turn (guardrail: no store publish). Local `assembleDebug` only.
