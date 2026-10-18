# OBZRAi — Execution Plan v2.1 r2

**Status:** **AUTHORIZED** — Basant voice APPROVED 17 Oct 2026: stop holding; continue **all phases**. Phase 0 closed under Sharesheet interim.  
**Source of truth:** [`docs/prd/OBZRAi_PRD_v2.1.md`](../prd/OBZRAi_PRD_v2.1.md) — **PRD v2.1 r2** (canonical; supersedes v2.1 / v2.1 r1 check-in and v1.0).  
**Date:** 17 October 2026 (plan revision for r2; execution start same day)  
**Owner locks:** D7 = **Option B DECIDED**; D1–D6 = PRD §9 recommendations; Phase order **0 → 1 → 2**; no GitHub Actions; §26 Unverified does **not** block build; **merged to `main` authorized by Basant (18 Oct 2026)**.

### Assumptions locked at Phase 0 closeout (Basant-approved defaults)

| ID | Assumption | Notes |
|---|---|---|
| **Q32** | WebDAV = **Phase 2 fast-follow after Drive `appDataFolder`** | Not a same-cut Phase 2 exit blocker |
| **Q33** | Thin-sync thumbs **≤ 200 KB JPEG** | Documented cap for FR-103 |
| **Q36** | Phases 1–2 = **local-first / ciphertext / no-account** | **Counsel / DPDP review PENDING** before account or hosted relay |
| **S1** | **Sharesheet interim approved**; chip/lock **PENDING verification** | Do not claim chip/lock passed |
| **S2 visual** | Compose placeholder; **`matches reference` PENDING** | Until `assets/approved_overlay_reference.png` |

---

## Tasks changed vs prior plan (v2.1 → v2.1 r2)

Prior plan: first check-in against PRD v2.1 (pre-r2). This revision aligns to **PRD v2.1 r2**.

| Change | Prior plan | Now (r2) |
|---|---|---|
| **PRD pin** | v2.1 | **v2.1 r2** exact copy in repo |
| **D7 language** | Owner-locked B; PRD still said “pending” in places | PRD marks **D7 DECIDED — Option B**; plan assumes B |
| **Transport order** | Drive `appDataFolder` first; WebDAV “optional later adapter” | **Drive appData then WebDAV** both in Phase 2 path (WebDAV next / FOSS; open Q whether same cut or fast-follow inside Phase 2) |
| **FR-047 / 048 / 054 / 064** | Treated as Phase 4 (full) only; thin work mostly under FR-103 | **Thin-payload slices of FR-047 / 048 / 054 / 064 + FR-103 are Phase 2 exit**; full-res of FR-047 / 064 remains Phase 4 (D1) |
| **FR-104** | Phase 2 bonus | Unchanged — **P2 bonus** (D7-C); not a Phase 2 exit gate |
| **Spikes** | S22 required for D7-B; S24 not listed | **S22 + S24** are Phase 2 gates (S24 = Drive OAuth `drive.appdata` non-sensitive + **brand verification**) |
| **Phase 2 exit AC** | Sync opt-in, thin payload, ciphertext | **Plus:** Android ↔ extension Hub appear within **target p95 ≤ 60 s** on healthy network; ciphertext only on store; **sync off by default** |
| **Risks** | Generic D7/quota notes | Explicit **R31** (brand verification; app verification not mandatory for non-sensitive-only) and **R32** (quota / consent friction; never promise free unlimited sync) |
| **Doc / claim fixes** | N/A | Plan notes r2 claim/price/source tidy-ups (Screenpipe monthly/annual, KR fee unknown, UNVERIFIED tags) — **no product-scope change** beyond D7 markers |
| **Rename baseline** | “Still AI Observer on PR #1” | Rename draft **[#3](https://github.com/basantsd/OBZRai/pull/3)** exists (R1–R5); still no MVP |
| **Phase 0** | Unchanged | **AUTHORIZED** — Sharesheet interim; continue all phases (Basant 17 Oct 2026) |

**Unchanged from prior plan:** Phase 0 S1–S4 gates; Phase 1 slices 1a–1e / 40–55 eng-days / FR-097; no `.github/workflows`; §26 non-blocking; no MVP/extension build until approval.

---

## 0. How to read this plan

| Rule | Detail |
|---|---|
| No invented product facts | Scope, AC, FRs, spikes, estimates from PRD **v2.1 r2** only. |
| Approval gate | Do **not** start Phase 0 engineering, Android MVP, or extension until Basant approves. |
| Phase order after approval | Phase 0 (S1–S4) → Phase 1 Android MVP → Phase 2 browser extension **+ D7-B thin E2E**. |
| CI/CD | **No** `.github/workflows`. Local/worker builds only. |
| §26 Unverified | Do not block spikes or MVP. Confirm when that surface ships. |

### Locked decisions applied here

| ID | Decision (locked) | Plan impact |
|---|---|---|
| **D1** | Optional **full-res** E2E, off by default, **Phase 4** | Full frames / voice audio / hosted relay stay out of Phase 2 |
| **D2** | Cloud AI opt-in + BYO key first | No default cloud upload in Phases 0–2 |
| **D3** | PTT = P0; assistant P1; wake word P2 | Phase 1 includes FR-040 |
| **D4** | Redaction opt-in, off by default | Not a Phase 1 gate |
| **D5** | Today / tags / provenance / PTT in P0; collections P1; **40–55 eng-days** | Phase 1 slices 1a–1e |
| **D6** | Accounting per-app opt-in; banking/password excluded | Phase 1 default exclusions |
| **D7-B** | **DECIDED:** Phase 2 thin E2E; opt-in; Drive `appDataFolder` **then** WebDAV; items + thumbs + transcripts; no full-res; ~3+ eng-weeks (Unverified #40) | Phase 2 exit = extension **+** FR-103 + thin FR-047/048/054/064 + S22 + S24 |

---

## 1. Current baseline

| Asset | State |
|---|---|
| Repo | `basantsd/OBZRai` |
| Scaffold | Draft [#1](https://github.com/basantsd/OBZRai/pull/1); rename [#3](https://github.com/basantsd/OBZRai/pull/3) → OBZRAi strings |
| PRD / plan | Draft [#2](https://github.com/basantsd/OBZRai/pull/2) — this branch; **updating to r2** |
| Overlay PNG | Still missing → Compose placeholder |

---

## 2. Pre-Phase-0 — Scaffold rename (R1–R5)

Unchanged intent. Prefer merging [#3](https://github.com/basantsd/OBZRai/pull/3) into [#1](https://github.com/basantsd/OBZRai/pull/1).

| Task ID | Task | Acceptance |
|---|---|---|
| **R1** | Display strings → **OBZRAi** | app_name, notification, empty library, overlay a11y |
| **R2** | README + `rootProject.name` | OBZRAi; former name in history only |
| **R3** | Keep `com.obzrai.observer` | No package rename |
| **R4** | No `.github/workflows` | Delete if any appear |
| **R5** | Local verify | `assembleDebug` + unit tests on worker only |

---

## 3. Phase 0 — Android feasibility (S1–S4)

**Estimate:** 5–8 eng-days (§19.1). **Hard gates:** S1, S2. S3 fallback OK. S4 third-party audio fail ≠ V1 no-go.  
**Not started until plan approval.**

| Task ID | Spike | Pass | Fail / fallback | FRs |
|---|---|---|---|---|
| **P0-S1** | Projection | Stable full-res frames; chip+lock stop on 15 QPR1+ | Sharesheet-only / stop (owner) | FR-001, 006, 016, 020, 056 |
| **P0-S2** | Overlay | Reference match; no pipe; restricted-settings path | Play-only / Sharesheet | FR-002–005, 057 |
| **P0-S3** | Clean frame | Exclusion or documented OEM limit | Hide-and-settle with gap | FR-007 |
| **P0-S4** | Audio | Controlled app + honest third-party log | Ship visual+OCR+Sharesheet | FR-011, 012 |

---

## 4. Phase 1 — Android MVP (unchanged structure)

**Estimate:** 40–55 eng-days after Phase 0. **Exit:** P0 FRs; S1–S4, S7, S16, S18; FR-097; quality bar §4.5.

| Milestone | Days | Deliverable | Key FRs |
|---|---|---|---|
| **M1a** Foundation & visual | 6–8 | Home/Library, ring, persistence | FR-001–005, 013, 018, 074–076 |
| **M1b** Capture & matching | 10–14 | Session, frames, OCR, topics, DRM label | FR-006–010, 015, 026, 052, 055, 056 |
| **M1c** Audio, PTT, storage | 10–14 | Permitted audio, ASR, PTT ideas | FR-011, 012, 040, 053 |
| **M1d** Hub & quality | 8–12 | Today, tags, search, export, **FR-097**, FR-106 local, marketing stub | FR-014, 019, 035, 036, 038, 063, 072, 073, 078, 094, 097, 106 |
| **M1e** Buffer & release | 6–7 | S7/S18, signed beta, matrix | FR-016, 020, 052, 055, 057 |

**Out of Phase 1:** collections, assistant, imagination, thin or full sync, extension, AccessibilityService, wake word.

---

## 5. Phase 2 — Browser extension + D7-B (r2)

**Depends on:** Phase 1 learnings; **S9 → S10**; then **S22 + S24**.  
**Gates (r2):** S1–S4 + S9 + S10 + **S22 + S24**.

### Milestone M2a — Shared core (S9)

| Task ID | Work | Acceptance | FRs |
|---|---|---|---|
| **P2-S9** | Observer core + fixtures | Same fixture → same match/fields on Android + extension | FR-027, 033 |

### Milestone M2b — Chromium MV3 + side-panel Hub (S10)

| Task ID | Work | Acceptance | FRs |
|---|---|---|---|
| **P2-01** | Active-tab observe | Exact URL; ≤ 2 `captureVisibleTab`/sec | FR-028 |
| **P2-02** | Page text / highlight | No other-tab read | FR-029, 067 |
| **P2-03** | DRM / EME label | `capture_blocked`; no false Saved | FR-052 |
| **P2-04** | Side-panel Hub (option c) | Offline IndexedDB/OPFS; Today/search/browse | FR-058–061, 065, 095 |
| **P2-05** | Extension UX + CWS | Shortcuts, PTT contract, least privilege, single purpose | FR-066, 069–072 |
| **P2-06** | YouTube timestamp | Store `t=` only if known | FR-068 |
| **P2-07** | Export | MD/PDF/archive; FR-097 still works | FR-062, 097 |
| **P2-08** | S19 CWS packet | Privacy tab + listing | S19 |
| **P2-09** | S20 Hub CWV | LCP/INP/CLS + side-panel paint targets | FR-077 |

### Milestone M2c — D7-B thin E2E (**DECIDED**, r2 exit)

| Task ID | Work | Acceptance | FRs / spikes |
|---|---|---|---|
| **P2-S22** | Minimal-sync protocol + first user-owned adapter | Thin payload round-trip; ciphertext only; opt-in | S22, **FR-103** |
| **P2-S24** | Drive `appDataFolder` E2E spike | OAuth **`drive.appdata` only** (non-sensitive); **brand verification** for name/logo (app verification **not** mandatory for non-sensitive-only — **R31**); write/read ciphertext; uninstall deletes folder; quota visible (**R32** / Unverified #36) | S24, R31, R32 |
| **P2-D7-01** | Thin payload schema | Items, tags, collections, provenance, OCR/page text, transcripts, voice-idea **text**, thumbs ≤ documented cap (rec. ≤ 200 KB JPEG). **No** full-res frames, voice audio blobs, imagination originals | **FR-103** |
| **P2-D7-02** | Transport #1 Drive appData | Ciphertext; opt-in; off by default; device-only collections out | §24.5 |
| **P2-D7-03** | Transport #2 WebDAV | FOSS adapter next (same Phase 2 cut **or** fast-follow inside Phase 2 — open Q 32) | §24.5 |
| **P2-D7-04** | Android + extension clients | Thin **FR-047** (encrypted sync for thin set), **FR-048** conflicts (no silent loss), **FR-054** cross-device appear, **FR-064** ciphertext Hub client for thin path | FR-047†, 048†, 054†, 064†, 103 |
| **P2-D7-05** | Continuity status UI | Last sync / local-only / last writer; no green “synced” without round-trip | **FR-106** |
| **P2-D7-06** | Latency AC | Save on Android appears in extension Hub **and vice versa** within **target p95 ≤ 60 s** on healthy network | §19.4 exit |
| **P2-D7-07** | Defaults & honesty | Sync **off by default**; copy: thin ≠ full-res backup; lost key = lost remote; never “free unlimited sync” | R32, §24.6 |
| **P2-D7-08** | Explicitly **not** Phase 2 | Full-res media, voice audio blobs, hosted ciphertext relay, account-required sync | D1 / Phase 4 |

† Phase 2 = **thin-payload** acceptance only. Full-res FR-047 / FR-064 remain Phase 4.

### Milestone M2d — Bonuses (not exit gates)

| Task ID | Work | Priority | FRs |
|---|---|---|---|
| **P2-BONUS-C** | LAN/QR pairing (D7-C) | **P2 bonus** | **FR-104** |
| **P2-PROP** | Strength proposals after Hub schema | PROPOSAL | FR-098, 100–102, … |

### Phase 2 exit checklist (r2)

- [ ] S9 + S10 pass; CWS privacy; 2/sec; no other-tab; DRM labelled.
- [ ] **S22 + S24** pass (protocol + Drive brand-verify path).
- [ ] Thin E2E: FR-103 + thin FR-047/048/054/064; FR-106 accurate.
- [ ] Android ↔ extension Hub: **p95 ≤ 60 s** target on healthy network.
- [ ] Store sees **ciphertext only**; sync **opt-in / off by default**; device-only honored.
- [ ] Drive then WebDAV path documented; no full-res in sync.
- [ ] No GitHub Actions workflows.

---

## 6. Deferred (unchanged gates)

| Item | Earliest |
|---|---|
| Phase 3 Desktop | After S11 |
| Phase 4 full-res E2E + PWA + RN + iOS discovery | D1 / S12 / S14 |
| Wake word / AccessibilityService in MVP | Never / P2 |
| `.github/workflows` | Owner forbid |
| Marketing claims from §26 | Confirm at ship |

---

## 7. Dependency graph

```text
[Basant approves this r2 plan]
        │
        ▼
[R1–R5 / merge rename #3 into #1]
        │
        ▼
[Phase 0: S1–S4] ── hard S1,S2
        │
        ▼
[Phase 1: M1a→M1e + FR-097]
        │
        ▼
[S9] → [M2b extension] → [S22+S24] → [M2c D7-B thin E2E]
        │
        ▼
[Phase 3 / 4 later — not authorized by this plan]
```

---

## 8. r2 doc notes (non-build)

Claim/price/source tidy-ups in PRD r2 (Screenpipe Basic/Business monthly+annual, KR Play fee unknown, UNVERIFIED tag hygiene, §9 heading, FR-087/105 restatements) do **not** add Phase 0–1 engineering tasks. They constrain **honesty in copy and GTM** when those surfaces ship.

**R31 / R32** are Phase 2 engineering risks tied to S24 / FR-103 — tracked in M2c above.

---

## 9. Definition of done — this revision

- [x] Repo `docs/prd/OBZRAi_PRD_v2.1.md` = exact PRD **v2.1 r2**
- [x] Execution plan revised for D7-B, S22+S24, Phase 2 exit AC, FR thin Phase 2 mapping, R31/R32
- [x] Store plan + summary updated; **tasks changed** section present
- [x] No `.github/workflows`; no MVP/extension build
- [ ] **Basant approval** still required before Phase 0

---

## 10. Document control

| Field | Value |
|---|---|
| Plan version | **2.1 r2** |
| PRD version | **2.1 r2** (17 Oct 2026) |
| Supersedes plan | Execution plan v2.1 (first check-in on PR #2) |
| Next action | Basant approve → R1–R5 → Phase 0 S1–S4 |
