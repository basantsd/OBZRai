# Phase 1 — Android MVP progress

**Date:** 17 Oct 2026  
**Branch:** `cursor/phase1-android-mvp-3a5a` (draft PR)  
**Base:** Phase 0 closeout (Sharesheet interim approved)  
**Guardrails:** Draft PR only — **never merge to main**. No `.github/workflows`. No store publish. No external paid accounts.

## Assumptions carried forward

| ID | Assumption |
|---|---|
| Q32 | WebDAV = Phase 2 fast-follow after Drive appData |
| Q33 | Thin thumbs ≤ 200 KB JPEG |
| Q36 | Local-first / no-account; counsel PENDING before account/relay |
| S1 | Sharesheet interim; chip/lock **PENDING verification** |
| S2 visual | `matches reference` **PENDING** |

## Slices

| Slice | Status | Delivered |
|---|---|---|
| **1a** Foundation & visual | **DONE** | Home/Library/Today nav, ring+glyph, overlay position persist, dark mode (FR-075), EN+Hindi (FR-076), onboarding ≤3 (FR-072), empty states (FR-073) |
| **1b** Capture & matching | **DONE** | Session + clean-frame save, Sharesheet import, OCR (ML Kit), topics, DRM/black-frame `capture_blocked` (FR-052/055), manual Save/Skip (FR-010) |
| **1c** Audio, PTT, storage | **DONE** | Mic opt-in + PTT recorder (FR-040); transcript placeholder until S6; Room media assets |
| **1d** Hub & quality | **DONE** | Today (FR-035), tags field + search (FR-014/036), provenance fields (FR-038), FR-097 encrypted export/import, FR-106 local_only chip, Trust/Settings (FR-078), marketing stub (FR-094) |
| **1e** Buffer & release | **PARTIAL** | Stop paths via session/service; matrix doc below; signed Play beta **not** produced (no store publish) |

## FR checklist (P0 highlights)

| FR | Status |
|---|---|
| FR-001 session | Implemented |
| FR-002 ring match | Mechanics OK; reference PNG **PENDING** |
| FR-003–005 overlay | Drag/snap/persist + Save/Skip/Settings |
| FR-006–007 frames | Full frame + hide-and-settle |
| FR-008 OCR | ML Kit on-device |
| FR-009 topics | Keyword matcher |
| FR-010 manual | Overlay + Home |
| FR-011–012 audio | Probe honesty; playback capture best-effort |
| FR-013–015 persist/search/URL | Room + search + Sharesheet URL |
| FR-016 stop | Service stop + onStop callback (chip/lock **PENDING device**) |
| FR-017–019 privacy/export | Local default; delete all; encrypted archive |
| FR-035–036–038–040 | Today, tags, provenance fields, PTT |
| FR-052–057 | Blocked label; no bypass; sideload education copy |
| FR-063 team-free | No org UI |
| FR-072–078 | Onboarding, empty, dark, hi, trust |
| FR-094 | `docs/marketing/` stub |
| FR-097 | AES-GCM archive |
| FR-106 | `local_only` continuity |

## Device PENDING

- MediaProjection chip + lock on Android 15 QPR1+ (**do not claim passed**)
- Overlay vs `assets/approved_overlay_reference.png`
- Live Reel/Short OCR precision on 200-item set (S5)
- On-device ASR quality (S6)

## Verify

```bash
./gradlew :app:assembleDebug :app:testDebugUnitTest
```

## Evidence

Context store: `media/phase1/` (build logs + unit summary after verify).
