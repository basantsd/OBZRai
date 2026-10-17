# OBZRAi — Execution Plan v2.1

**Status:** Planning only — awaiting Basant approval before Phase 0 / MVP / extension implementation.  
**Source of truth:** [`docs/prd/OBZRAi_PRD_v2.1.md`](../prd/OBZRAi_PRD_v2.1.md) (supersedes v1.0 / “AI Observer”).  
**Date:** 17 October 2026  
**Owner locks (Basant):** D7 = **Option B**; D1–D6 = PRD §9 recommendations; Phase order **0 → 1 → 2**; no GitHub Actions; §26 Unverified does **not** block build.

---

## 0. How to read this plan

| Rule | Detail |
|---|---|
| No invented product facts | Scope, AC, FRs, spikes, and estimates come only from PRD v2.1. |
| Approval gate | **Show this plan first.** Do not start full Phase 0 engineering, Android MVP, or extension until Basant approves. |
| Phase order after approval | Phase 0 (S1–S4) → Phase 1 Android MVP → Phase 2 browser extension (+ D7-B). |
| CI/CD | **No** `.github/workflows`. Builds/tests run on the worker VM / local only (owner constraint; Actions minutes exhausted). PRD §15.2 / §20.4 CI mentions are deferred — verify locally. |
| §26 Unverified | Do not block spikes or MVP. Treat as “confirm when touching that surface,” not as a gate. |
| Effort labels | From PRD (eng-days / ~weeks). Not calendar commitments. |

### Locked decisions applied here

| ID | Decision (locked) | Plan impact |
|---|---|---|
| **D1** | Optional full-res E2E sync, off by default, **Phase 4** | Full frames/voice audio stay out of Phase 2 |
| **D2** | Cloud AI opt-in per feature + BYO key first | No default cloud upload in Phases 0–2 |
| **D3** | PTT spoken ideas = **P0**; assistant P1; wake word P2 | Phase 1 includes FR-040; no wake word |
| **D4** | Optional redaction, off by default | Not a Phase 1 gate (S15 later) |
| **D5** | Today / tags / provenance / PTT in P0; collections P1; MVP **40–55 eng-days** | Phase 1 slices 1a–1e |
| **D6** | Accounting per-app opt-in; banking/password excluded | Phase 1 exclusions; accounting opt-in can wait for P1 polish |
| **D7-B** | **DECIDED:** Phase 2 minimal E2E sync, opt-in, Drive `appDataFolder` first; items + thumbs + transcripts; **no** full-res; ~3 eng-weeks (S22) | Phase 2 exit includes FR-103 |

---

## 1. Current baseline

| Asset | State |
|---|---|
| Repo | `basantsd/OBZRai` — `main` has README only |
| Scaffold | Draft [PR #1](https://github.com/basantsd/OBZRai/pull/1) — `cursor/scaffold-android-app-bed7` — Kotlin/Compose shell, package `com.obzrai.observer`, **display name still “AI Observer”** |
| PRD | v2.1 checked into `docs/prd/OBZRAi_PRD_v2.1.md` (this planning PR) |
| Overlay asset | `assets/approved_overlay_reference.png` still missing → Compose placeholder until provided |

---

## 2. Immediate pre-Phase-0 work — Scaffold PR #1 rename

**Goal:** Align scaffold identity with OBZRAi without building MVP features. Small rename PR / commit on the scaffold branch is enough.

| Task ID | Task | Acceptance | FR / notes |
|---|---|---|---|
| **R1** | Rename user-visible product strings from “AI Observer” → **OBZRAi** | `strings.xml` `app_name`, notification title, library empty, overlay content description use OBZRAi; consumer copy may still say “observer” as common noun for the ring | §2 Identity |
| **R2** | README + `settings.gradle.kts` `rootProject.name` | README title **OBZRAi**; root project name `OBZRAi` (or `obzrai`); remove leading “AI Observer” branding | §20.4 / §27 |
| **R3** | Keep package `com.obzrai.observer` | No package rename in this pass (collision avoidance already done); document former name in README history only | §20.4 |
| **R4** | Confirm **no** `.github/workflows` on PR #1 | If any workflow appears, delete it before merge | Owner CI lock |
| **R5** | Local verify only | `./gradlew :app:assembleDebug` and `:app:testDebugUnitTest` on worker/local | No Actions |

**Out of rename scope:** capture pipeline, OCR, ASR, Hub polish, AccessibilityService, sync.

---

## 3. Phase 0 — Android feasibility (S1–S4)

**Estimate:** 5–8 eng-days (PRD §19.1).  
**Gate:** No full-product UI rollout before S1–S4 pass or an approved fallback is recorded.  
**Hard gates:** S1 and S2. S3 fallback allowed. S4 third-party audio failure is **expected** and not a V1 no-go.

### Milestone M0 — Spike pack complete

| Task ID | Spike | Deliverable | Pass (go) | Fail / fallback | Primary FRs |
|---|---|---|---|---|---|
| **P0-S1** | Projection | Authorized session; repeated full-res frames on Reel/Short **and** website | Portrait bounds preserved; no new prompt per frame; correct stop including **chip + lock** on 15 QPR1+ | Unstable projection → stop product or Sharesheet-only (owner decision) | FR-001, FR-006, FR-016, FR-020, FR-056 |
| **P0-S2** | Overlay | 28 dp edge-clipped ring, 10–12 dp glyph, drag/snap | Matches reference on 3 densities; no pipe; host remains tappable; sideload restricted-settings path documented | Overlay unusable after education → Play-only overlay or Sharesheet fallback | FR-002, FR-003, FR-004, FR-005, FR-057 |
| **P0-S3** | Clean frame | Overlay exclusion / hide-and-settle | Exclusion works **or** OEM limit documented | Always capture the ring → ship documented hide-and-settle even if 1-frame gap | FR-007 |
| **P0-S4** | Audio | Controlled test app + Instagram + YouTube on selected devices | Positive on controlled app; third-party results **logged honestly** | Third-party blocked → **do not block V1**; ship screenshot+OCR+Sharesheet | FR-011, FR-012 |

### Phase 0 exit checklist

- [ ] S1 go recorded (or owner fallback written).
- [ ] S2 go recorded (or owner fallback written).
- [ ] S3 pass or documented fallback.
- [ ] S4 matrix logged; copy uses “when permitted,” not universal audio claims.
- [ ] Spike notes in repo docs (no marketing claims from §26).
- [ ] Local builds only; no workflows added.

**Alpha path (optional after S1–S4):** screen/OCR-only alpha to testers in ~10–15 additional days without PTT/Hub polish (PRD §19.3).

---

## 4. Phase 1 — Android MVP

**Estimate:** 40–55 eng-days after Phase 0 (D5 / §19.3).  
**Depends on:** Phase 0 go (or approved fallback).  
**Exit:** All P0 FRs on device matrix (§20.1); S1–S4, S7, S16, S18 done; privacy checklist; no placeholder UI; README limitations honest; FR-097 round-trip; quality bar §4.5.

### Milestone M1a — Foundation & visual (6–8 d)

| Task ID | Work | Acceptance | FRs |
|---|---|---|---|
| **P1-1a-01** | Compose Home + Library shell (from scaffold) | Navigation works; Observe start/stop UI wired to session SM | FR-001, FR-049 |
| **P1-1a-02** | Approved ring overlay (PNG when available; else continue placeholder with gap noted) | Geometry/states match §11; Save/Skip/Settings; drag/snap persist | FR-002–005, FR-018 |
| **P1-1a-03** | Persistence foundation (Room entities → real writes for sessions/items stubs) | Accepted items survive restart | FR-013 |
| **P1-1a-04** | Overlay non-drag alternative + dark mode strings baseline | Non-drag path for Save/Skip; EN strings; Hindi strings started | FR-074, FR-075, FR-076 |

### Milestone M1b — Capture & matching (10–14 d)

| Task ID | Work | Acceptance | FRs |
|---|---|---|---|
| **P1-1b-01** | MediaProjection session + FGS (`mediaProjection`) | Fresh consent per session; FGS + chip never hidden | FR-001, FR-016, FR-020, FR-056 |
| **P1-1b-02** | Full-frame capture + clean-frame path | Stored original uncropped; overlay exclusion or documented settle | FR-006, FR-007 |
| **P1-1b-03** | On-device OCR + topic classifier | Indexed text; ≥80% precision target on 200-item set (target); manual Save/Skip | FR-008, FR-009, FR-010, FR-026 |
| **P1-1b-04** | DRM / FLAG_SECURE labeling | `capture_blocked`; no green check for blank frame; no bypass | FR-052, FR-055 |
| **P1-1b-05** | Source integrity + share import | Exact URL only when known; never fabricate | FR-015 |

### Milestone M1c — Audio, PTT, storage (10–14 d)

| Task ID | Work | Acceptance | FRs |
|---|---|---|---|
| **P1-1c-01** | Playback audio adapter | Permitted path works; Unsupported/Unavailable/Blocked honest | FR-011 |
| **P1-1c-02** | On-device ASR (`whisper.cpp` path per stack) | Timestamped transcript; never invent for blocked audio | FR-012 |
| **P1-1c-03** | PTT spoken idea notes (S16 gate) | Separate mic opt-in; hold-to-talk; audio+transcript linked; visible mic | FR-040, FR-053 |
| **P1-1c-04** | Item detail + media layout | Resource, time, provenance fields, voice idea linkage | FR-038, FR-053 |
| **P1-1c-05** | Banking/password exclusion defaults | Default exclusion list; no workplace-monitor mode | FR-034 (subset), FR-050 (defaults) |

### Milestone M1d — Hub & quality (8–12 d)

| Task ID | Work | Acceptance | FRs |
|---|---|---|---|
| **P1-1d-01** | Today view | Today + earlier day selectable; badges for voice/audio/blocked | FR-035 |
| **P1-1d-02** | Tags + search (FTS) | Add/remove/rename tags; p95 ≤ 500 ms on 1,000-item fixture | FR-014, FR-036 |
| **P1-1d-03** | Provenance narrative | Omits unknowns; never guesses | FR-038 |
| **P1-1d-04** | Export/delete + **encrypted export/import** | Single/all delete; passphrase archive round-trip on fresh install | FR-019, **FR-097** |
| **P1-1d-05** | Continuity status UI (local-only) | Hub shows library-on-this-device / last backup or never | **FR-106** |
| **P1-1d-06** | Privacy copy, trust center in Settings, team-free | No account wall; delete-all; team-free | FR-017, FR-063, FR-078 |
| **P1-1d-07** | Marketing origin stub (home, privacy, download CTAs, changelog) | Public `/privacy` before any store listing | FR-094 (Phase 1 subset per §19.8) |
| **P1-1d-08** | Onboarding ≤ 3 screens + empty states | Skippable; no account wall; every empty view has one primary action | FR-072, FR-073 |
| **P1-1d-09** | Observer core contract (Kotlin-first) | Shared fixtures for topics/scoring/schema ready for S9 | FR-027 |

### Milestone M1e — Buffer & release (6–7 d)

| Task ID | Work | Acceptance | FRs / spikes |
|---|---|---|---|
| **P1-1e-01** | S7 stop/privacy matrix | Chip, notification, lock, revoke, kill → buffers gone | FR-016, FR-020 |
| **P1-1e-02** | S18 protected content | DRM labelled; no false Saved | FR-052, FR-055 |
| **P1-1e-03** | OEM / restricted-settings education | Sideload path documented | FR-057 |
| **P1-1e-04** | Signed beta APK + README honesty | Limitations: audio/DRM/URL; “not a universal recorder” | §20.4–20.5 |
| **P1-1e-05** | S8 distribution packet (docs only if not submitting) | Play vs FOSS decision documented | S8 |
| **P1-1e-06** | Device matrix pass | Android 10/13/14/15/16 + OEM samples per §20.1 | §20.1 |

### Phase 1 P0 FR checklist (must pass)

FR-001–020, 026, 027, 035, 036, 038, 040, 049, 052, 053, 055, 056, 057, 063, **097**, plus Phase-1 subsets of FR-072–076, 078, 094, **106** as listed above.

**Explicitly out of Phase 1:** collections (FR-037), assistant (FR-041), imagination (FR-044), full E2E (FR-047), AccessibilityService, wake word, browser extension, D7-B.

---

## 5. Phase 2 — Browser extension + D7-B

**Depends on:** Phase 1 learnings; spikes **S9** (core parity) then **S10** (extension).  
**Estimate:** not calendar-dated until Phase 1 (PRD §19.4). **D7-B add-on:** ~3+ eng-weeks (PRD §9 / §26 item 40 = relative effort only).  
**Owner lock:** D7-B **in** Phase 2 (opt-in minimal E2E; Drive `appDataFolder` first).

### Milestone M2a — Shared core extraction (S9)

| Task ID | Work | Acceptance | FRs |
|---|---|---|---|
| **P2-S9** | Extract Observer core + fixtures | Same fixture → same topic match and item fields on Android + extension target | FR-027, FR-033 |

### Milestone M2b — Chromium MV3 extension (S10 + Hub)

| Task ID | Work | Acceptance | FRs |
|---|---|---|---|
| **P2-01** | Active-tab observe session | User-started; exact URL `source_url_origin = browser_tab`; ≤ 2 `captureVisibleTab`/sec | FR-028 |
| **P2-02** | Page text / highlight-clip | Visible text + selection; no other-tab read; restricted pages reported | FR-029, FR-067 |
| **P2-03** | DRM / EME labelling | Black frames → `capture_blocked`; no false Saved | FR-052 (web) |
| **P2-04** | Side-panel Hub (option **c**) | Offline IndexedDB/OPFS; Today/search/browse; no account required | FR-058–061, FR-065, FR-095 |
| **P2-05** | Extension UX | ≤ 4 shortcuts; voice note contract; onboarding; CWS least privilege + single purpose | FR-066, FR-069–072, FR-070, FR-071 |
| **P2-06** | YouTube timestamp (when known) | Store `t=` only if known; never download video | FR-068 |
| **P2-07** | Export paths | MD/PDF/archive from Hub; encrypted export still works | FR-062, FR-097 |
| **P2-08** | S19 CWS packet | Privacy tab, listing video, permissions justified | S19 / FR-070–071 |
| **P2-09** | S20 Hub CWV | LCP/INP/CLS targets; side-panel first paint target | FR-077 |

### Milestone M2c — D7-B minimal E2E sync (LOCKED)

| Task ID | Work | Acceptance | FRs / spikes |
|---|---|---|---|
| **P2-D7-01** | **S22** protocol + schema | Thin payload: items, tags, collections, provenance, OCR/page text, transcripts, voice-idea **text**, thumbnails ≤ documented cap (e.g. ≤ 200 KB JPEG). **No** full-res frames, **no** voice audio blobs, **no** imagination originals | **FR-103** |
| **P2-D7-02** | Transport #1: Google Drive `appDataFolder` | Ciphertext only; `drive.appdata`; opt-in; off by default; device-only collections excluded | §24.5 |
| **P2-D7-03** | Android sync client | Opt-in settings; encrypt before upload; conflict rule = no silent data loss (§16.5.3) | FR-103, FR-048 (thin) |
| **P2-D7-04** | Extension sync client | Same protocol; Hub shows continuity status | FR-103, **FR-106** |
| **P2-D7-05** | Continuity UX copy | “Thin sync ≠ full-res backup”; lost key = lost remote copy; quota honesty (§26 #36) | §24.6 |
| **P2-D7-06** | (Optional later adapter) WebDAV | Second adapter = M effort if needed | §24.5 |
| **P2-D7-07** | **Not in D7-B** | Hosted ciphertext relay, full-res FR-047, account-required sync → **Phase 4 / D1** | D1 |

### Milestone M2d — Phase 2 bonuses (not gates)

| Task ID | Work | Priority | FRs |
|---|---|---|---|
| **P2-BONUS-C** | D7-C LAN/QR pairing | P2 bonus | FR-104 |
| **P2-PROP** | Strength proposals only after Hub schema | PROPOSAL | FR-098, FR-100–102, etc. |

### Phase 2 exit checklist

- [ ] S9 + S10 pass.
- [ ] CWS privacy / single-purpose / 2-per-sec / no other-tab access.
- [ ] Side-panel Hub offline-capable.
- [ ] **D7-B:** opt-in thin E2E Android ↔ extension via Drive appDataFolder; FR-103 AC met; FR-106 accurate.
- [ ] Full-res media still local-only (D1 unchanged).
- [ ] No GitHub Actions workflows introduced.

---

## 6. Explicitly deferred (do not start this turn / before gates)

| Item | Earliest | Notes |
|---|---|---|
| Phase 3 Desktop | After S11 | macOS → Windows → Linux |
| Phase 4 full E2E + PWA + RN + iOS discovery | D1 / S12 / S14 | Full-res FR-047, FR-064 |
| Wake word | P2 | FR-042 |
| AccessibilityService | Never in MVP | §2 / FR guardrail |
| GitHub Actions / `.github/workflows` | Owner forbid | Local Gradle / manual only |
| Marketing claims from §26 | N/A | Confirm when that surface ships |

---

## 7. Dependency graph (approval → ship)

```text
[Basant approves this plan]
        │
        ▼
[R1–R5 rename on PR #1] ── parallel OK with planning docs
        │
        ▼
[Phase 0: S1–S4] ── hard: S1,S2 ── soft: S3 fallback, S4 honesty
        │
        ▼
[Phase 1: M1a→M1e Android MVP + FR-097]
        │
        ▼
[S9 core] → [Phase 2 extension M2b] → [M2c D7-B / S22]
        │
        ▼
[Phase 3 / 4 later — out of this plan’s build authorization]
```

---

## 8. Definition of done pointers

- **Android V1 DoD:** PRD §20.5 (checkbox list).  
- **Quality bar:** PRD §4.5.  
- **Example acceptance scenarios:** PRD §20.3 (Gherkin).  
- **This planning turn DoD:** PRD in repo draft PR; this plan in `docs/plans/` + Context store copy; PR URL + 1-page summary for Basant; **no** MVP/extension implementation; **no** workflows.

---

## 9. Open questions that do *not* block Phase 0

Tracked in PRD §29 / §26. Phase 0 may start after plan approval even while these remain open (owner: §26 does not block build). Examples: IP India clearance, Instagram/YouTube capture-policy measurement (done *in* S4), Play target-API deadline re-verify at submission, Drive quota wording honesty before promising “free sync.”

---

## 10. Document control

| Field | Value |
|---|---|
| Plan version | 2.1 |
| PRD version | 2.1 (17 Oct 2026) |
| Supersedes | Informal v1.0 “AI Observer” planning / Researchy placeholders |
| Next action | Basant approval → execute R1–R5 → Phase 0 S1–S4 |
