# OBZRAi — Product Requirements Document

**Version:** 2.1  
**Date:** 17 October 2026  
**Status:** Professional-grade PRD for owner review. Android feasibility spikes S1–S4 remain the first engineering gate. D1–D6 carry explicit recommendations in §9; **D7 is a new pending decision** (minimal E2E sync alongside Phase 2 vs keep full sync in Phase 4). Owner sign-off still required before those items leave “recommended.” **v2.1, 17 Oct 2026:** fills web-grade Hub/marketing-site/share, GTM checklists, strength-feature PHASE tags, and a new **§24 Cross-device continuity**. Does not duplicate §§21–23.  
**Owner:** Basant Singh (India)  
**Product name:** **OBZRAi** (formerly “AI Observer”)  
**File:** this document is self-contained. It supersedes `/workspace/prd/AI_Observer_PRD_v1.2.md` as the working PRD. v1.2 and v2.0 are not modified.

**Platforms:** One product around one shared **Observer core**. Android mobile app first (current Cursor scaffold: Kotlin + Jetpack Compose). Then Chromium Manifest V3 browser extension, desktop (macOS / Windows / Linux), React Native, with optional end-to-end encrypted cross-device sync. iOS is a later discovery, not an Android MVP promise. Non-Android clients start only after Android spikes S1–S4 pass or an approved fallback is recorded.

**Distribution:** Open-source repository; GitHub Releases / Obtainium / IzzyOnDroid / F-Droid considered; Google Play publication after policy review and a MediaProjection demonstration video.

**Product philosophy:** Minimal UI, maximum utility, user-controlled observation, privacy-first, local-first and on-device by default, professional-grade quality.

> **Product promise:** *Watch anything. Remember what matters.* OBZRAi is a small floating companion that collects useful information from **any content the user is already watching or reading on screen** — websites, YouTube, reels/shorts, articles, accounting software (Tally, Excel, ERP), AI chats, design/3D tools, movies — on whichever device they use. The user talks to it like an assistant and asks it to save things. Each saved item stores: screenshot/resource, time, the user’s spoken idea (audio + transcript), an optional AI-generated image of the idea, and source/provenance. Everything lands in a bookmarks-style **Research Hub** so a full day of research sits in one place. It is not a screen recorder, not a downloader, and not an always-on monitor.

**How this document treats facts.** Every external claim carries a source URL and a date (or “date not shown”). Items that could not be confirmed are labelled **Unverified** in place and collected in §26. Live web search and X search were used on 17 October 2026. Training data was not used as a source of facts.

---

## Document control

| Field | Value |
|---|---|
| Product | OBZRAi |
| Former name | AI Observer |
| PRD version | **2.1, 17 Oct 2026** |
| Previous | v2.0 (v2.0 addendum, 17 October 2026); v1.2, 17 October 2026 (`AI_Observer_PRD_v1.2.md`) |
| Author role | Senior product manager and platform-research analyst |
| Audience | Owner (Basant Singh), engineering, design, legal/privacy review |
| Companion research | `/workspace/AI_Observer_Marketing_Research.md` (dated sources; not modified) |
| Design authority | Owner-approved overlay PNG referenced by v1.2 as `assets/approved_overlay_reference.png` |
| Implementation status | Android-only Kotlin/Compose scaffold in Cursor. React Native, browser extension and desktop are follow-up phases. |

### Revision history

| Version | Date | Change |
|---|---|---|
| 1.0 | 17 October 2026 | Baseline: Android-first observer for Reels/Shorts; approved overlay; spikes S1–S8. |
| 1.1 | 17 October 2026 | Multi-platform, any-screen scope. Android MVP gates unchanged. |
| 1.2 | 17 October 2026 | Professional-grade expansion: Research Hub, assistant, AI agents, sync, DRM, FRs 035–054, spikes S14–S18, pending D1–D6. |
| **2.0** | **17 October 2026** | Rename to OBZRAi. Live-validated platform constraints (correcting v1.2). Competitive landscape, pricing, legal (DPDP Rules 2025, Play, ToS), naming/trademark, D1–D6 recommendations, tightened FRs/NFRs/metrics, phased roadmap with exit criteria. See §25. |
| **2.0 addendum** | **17 October 2026** | Web-grade product experience, go-to-market strategy, proposed features for strength and defensibility. FR-058–FR-093. New §§21–23; former §§21–27 renumbered 24–30. |
| **2.1** | **17 October 2026** | Fill gaps in §§21–23 (marketing site, E2E collection share with key-in-fragment, GTM step-by-step checklists, ASO, OSS contributors, missing strength proposals with PHASE). New **§24 Cross-device continuity** and pending **D7**. FR-094–FR-106. Former §§24–30 become 25–31. See §25. |

### Owner direction (kept from v1.1 / v1.2)

> “ये सब चीज़ों के लिए भी यूज़ होगा, तो मल्टी प्लेटफॉर्म के लिए बने ये”  
> *(It will be used for all of these things too, so it should be built multi-platform.)*

> “कोई स्क्रीन ऑब्ज़र्व करनी होगी, जैसे वेबसाइट वगैरह, कोई यूट्यूब वीडियो वगैरह, या फिर कोई कहीं पे आर्टिकल वगैरह, तो उनके लिए भी यूज़ होगा।”  
> *(Whenever some screen has to be observed — a website, a YouTube video, or an article — it will be used for those too.)*

> “इसके लिए पीआरडी प्रॉपर रेडी करो, प्रोफेशनल लेवल की प्रोडक्ट बनाना है”  
> *(Get the PRD properly ready; we have to build a professional-level product.)*

Addendum direction (17 October 2026): add new ideas, make it a professional **web-grade** product, check how it can be run in the market, and add features/ideas that make it stronger. See §§21–23. **v2.1** fills remaining web/GTM/proposal gaps and adds how a save on one device shows up on another (§24).

---

## Table of contents

1. Executive summary  
2. Approved baseline and locked decisions  
3. Problem, users and jobs to be done  
4. Success criteria, quality bar and metrics  
5. Scope, priorities and exclusions  
6. Platform API and constraint validation  
7. Competitive landscape  
8. Pricing and business model  
9. Recommendations on pending decisions D1–D7  
10. End-to-end user flows  
11. Visual and interaction requirements  
12. Unified Library / Research Hub  
13. Functional requirements  
14. Observation and extraction pipeline  
15. Technical architecture  
16. Data model and local storage  
17. Privacy, security, compliance and legal  
18. Non-functional requirements  
19. Technical discovery, spikes and roadmap  
20. Testing, open-source release and definition of done  
21. Web-grade product experience  
22. Go-to-market strategy  
23. Proposed features for strength and defensibility  
24. Cross-device continuity  
25. Changes from v1.2  
26. Unverified claims  
27. Naming and trademark risk  
28. Risk register  
29. Open questions  
30. Glossary  
31. Sources  

---

## 1. Executive summary

OBZRAi is **one piece of software that observes any screen content the user chooses**, during an explicit, visible, user-started session, and files what they keep into a searchable Research Hub. The user can tap Save/Skip or **talk to it** (“save this — idea: reuse this invoice layout”). Each capture stores the full frame (when the OS allows), time, spoken idea, optional imagination image, and honest provenance.

**Android ships first** because the current scaffold is Android-only and because MediaProjection + overlay is the hardest, most policy-sensitive surface. Browser extension, desktop, React Native and sync follow after spikes S1–S4.

**What is different from “another clipper”:** live overlay during an OS-authorized session; on-device OCR and speech-to-text; full-frame originals that are never auto-cropped; topic matching; spoken ideas bound to the moment; a local library that works without an account. Share-sheet apps need a public link. OS tools (Pixel Screenshots, Circle to Search, Microsoft Recall) do not persist a personal cross-app library with the user’s spoken idea. Continuous screen-memory products (Rewind/Limitless, Screenpipe, Recall) record in the background; OBZRAi records only inside a session the user starts and can stop.

**Strategic constraints (validated 17 October 2026):**

- Android 14+ requires **fresh MediaProjection consent per session**, a `mediaProjection` foreground service, and a user choice of **single app vs entire screen** (single-app picker from Android 14 QPR2, not “Android 15 only” as v1.2 implied). Android 15 QPR1+ shows a **status-bar chip** and **stops projection on lock**. Playback audio is allowed only when the source app’s capture policy is `ALLOW_CAPTURE_BY_ALL`. `FLAG_SECURE` and DRM yield black frames; Play forbids bypass.
- Chrome MV3 `captureVisibleTab` is capped at **2 calls/second** (Chrome 92+). DRM/EME frames capture black. `tabCapture` needs a user gesture and an offscreen document for the stream.
- macOS Sequoia+ nags monthly (or on idle) if the app bypasses `SCContentSharingPicker`. Windows Graphics Capture draws a **yellow border** unless `IsBorderRequired` is unset with extra consent. Microsoft Recall is a Copilot+ opt-in OS feature, not a competitor to ship against on Android.
- India **DPDP Rules 2025** were gazetted **13 November 2025**; remaining processing obligations phase in through **~13 May 2027**. Play developer verification is already in force in Brazil, Indonesia, Singapore and Thailand (30 September 2026) and is planned globally in 2027.
- The name **“AI Observer” collides** with Observer AI (Roy3838). **OBZRAi** has no exact Play or X match found on 17 October 2026; close names exist (Obz AI, Obsera). **IP India search was not completed** (CAPTCHA-gated).

**Recommended business model:** open-core / FOSS **local-first free forever** for capture, library, OCR and on-device transcription; **BYO API key** for cloud AI; a later modest **Pro** subscription for hosted E2E sync and hosted AI, with **INR and USD** price lists. Do not charge for the local observer. See §8.

**Recommended D1–D6:** optional E2E sync off by default (D1); cloud AI opt-in per feature + BYO key (D2); push-to-talk in P0, assistant P1, wake word P2 (D3); optional redaction off by default (D4); Today/tags/provenance/voice notes in P0, collections P1, re-estimate ~40–55 eng-days (D5); accounting per-app opt-in, banking/password excluded (D6). Rationale in §9.

**Pending D7 (v2.1):** whether to pull a **minimal** E2E sync (items + thumbnails + transcripts, no full-res media) into Phase 2 when the extension ships, so a save on the phone appears in the browser Hub. Default recommendation remains D1: full-res sync stays Phase 4, off by default. D7-B is the owner’s call if phone↔browser continuity is required at extension launch. Full options, effort, cost, DPDP and a recommendation are in §9 D7 and §24.

**v2.0 addendum:** web-grade Hub (side panel in Phase 2, PWA in Phase 4, team-free V1), GTM, and strength proposals are in §§21–23. **v2.1** fills remaining gaps in those sections (marketing site, E2E collection share with key-in-fragment, GTM step-by-step checklists, missing strength proposals with PHASE) and adds **§24 Cross-device continuity**. New FRs FR-058–FR-106.

---

## 2. Approved baseline and locked decisions

These decisions are locked from the design discussion unless the owner explicitly changes them. Identity is updated from “AI Observer” to **OBZRAi**; the overlay geometry is unchanged.

| Area | Decision |
|---|---|
| Identity | **OBZRAi**, using the **blue hollow-ring observer logo** from the owner-approved reference. Consumer copy may say “Observer” as a common noun (“the observer ring”) after legal clearance of the brand. |
| Platform strategy | **Multi-platform**: Android app, React Native app, browser extension and desktop software, all built on one shared **Observer core**. Android ships first; other platforms follow after Android spikes S1–S4. |
| Content scope | **Any screen content**: short-form video (Reels/Shorts/TikTok), long-form video (YouTube and others), websites and web apps, articles/blogs/documentation, AI chat assistants, accounting and business software (Tally, Excel, ERP), design and 3D tools, movies/streaming (within DRM limits), documents, slides, images. Reels are one use case, not the product boundary. |
| Unified library | One bookmark-style **Research Hub** for everything saved on every device: collections/folders, tags, Today/timeline, search, source links, export. |
| Capture record | Every saved item records the **resource** (screenshot, clip or page text), **when** the user saw it, the user’s **spoken idea/instruction** (audio + transcript, if given), an optional **AI imagination image**, and **source/provenance**, shown as a short narrative. |
| Conversational assistant | The user can talk to or type to OBZRAi (“save this”, “remember this idea”, “imagine this”, “what did I see yesterday?”). Push-to-talk first; wake word only as an opt-in later option. |
| AI processing | On-device by default; cloud AI agents and image generation are **opt-in per feature**. Recommended confirmation: D2. |
| Default overlay | **Upper-left edge, just under the host app’s “Reels” label** in the reference (Android mobile); draggable anywhere, with left/right edge snap. Other platforms keep the same ring identity and states, adapted to their surface. |
| Form | Approximately **28 dp ring reference**, partly hidden outside the display edge; a **smaller 10–12 dp status glyph** tightly overlaps the logo’s lower edge. Final sizes validated on physical devices. |
| Background treatment | One coherent, lightly translucent floating element; **no vertical dark pipe**, tall rectangular backing, independent status block, or stacked double-ring redesign. |
| Overlay states | Idle; Listening (subtle waveform); Capturing (focus corners); Recording (muted red dot); Saved (standalone soft-green check); Skipped (minimal skip glyph); Settings (gear when opening menu). |
| Tap actions | Compact **Save / Skip / Settings** menu near the logo; tiny icons with readable labels; dismiss quickly. |
| Progress | Capture, recording, matching and saving proceed in the user-authorized session **without pausing video, modal progress windows, or blocking screens**. Only tiny status motion on the fixed logo. |
| Screenshot | Save the **entire captured display frame** at native orientation and available resolution. **Never automatically crop the stored original** to a creator face, central text, or landscape preview. |
| Audio/transcript | Save permitted source-app playback audio and create a timestamped transcript **when the OS and source app allow access**. Otherwise save visual information and an explicit audio-unavailable state. |
| Main app | Compact **Home + Library** navigation. Search within Library; Topics, Capture options and Privacy are secondary screens in Settings. |
| Data | **Local-first** on-device storage; account and cloud **not required** to use the product. User can stop observation and delete all captured data. Optional account + E2E sync in a follow-up phase (D1). Phase 1 continuity is encrypted export/import (FR-097). Pulling a thin E2E payload into Phase 2 is pending **D7**. See §24. |
| Source link | Keep the exact URL when shared or genuinely available; **do not fabricate** URLs from guessed metadata. |
| Rejected visual styles | Multicolored/neon-heavy UI, triangular replacement logo, extra dark connector block, oversize status buttons, unrelated product/app mockups. |
| AccessibilityService | **Not used in MVP.** A later narrow use needs separate disclosure, Play declaration and an architectural decision. Never market OBZRAi as an accessibility tool unless it is one. |
| DRM / FLAG_SECURE | **Never bypass.** Black/blank frames are labelled `capture_blocked`. |

**The owner-approved reference is the single source of truth for the overlay visual identity.** See `assets/approved_overlay_reference.png` (same file referenced by v1.2).

*Design reference note: the approved concept image contains an inconsistent screen caption for its third phone. Actual implementation must use the correct glyph for each state: muted red dot for Recording; soft green tick for Saved. The reference fixes style and placement, not incorrect text labels.*

---

## 3. Problem, users and jobs to be done

### 3.1 Problem

People learn from Instagram Reels, YouTube Shorts, full-length YouTube videos, websites, articles, documentation, AI chats, business software and design tools, on phones and on laptops. They currently take multiple screenshots, keep dozens of tabs open, or use each platform’s bookmarking feature. Screenshots lose spoken context. Links and saved posts scatter across apps, browsers and devices. Native Save is a bookmark, not a backup: Instagram watch history is documented as **30 days**, and saved items disappear if the creator deletes the Reel ([Crown Media, 9 Oct 2026](https://crownmediacreative.com/how-to-find-saved-posts-on-instagram/); [Lilach Bullock, 3 Oct 2026](https://www.lilachbullock.com/how-to-find-your-saved-reels-on-instagram/)).

Demand for “I saved it and cannot find it” is **qualitative plus adjacent commercial proof**, not a surveyed TAM. ReciMe (recipes from Reels/TikTok) has **1M+** Play installs ([Play listing, updated 1 Oct 2026](https://play.google.com/store/apps/details?id=com.recime.app)). Mozilla shut Pocket on **8 July 2025** after “helping millions” ([Mozilla blog, 22 May 2025](https://blog.mozilla.org/en/mozilla/building-whats-next/); [Mozilla Support, updated 12 Nov 2025](https://support.mozilla.org/en-US/kb/future-of-pocket)). Meta/IPSOS (4,000+ respondents, 23 centres) reported **97% of surveyed users in India watch video on Meta platforms daily** ([Meta newsroom, 3 Jun 2026](https://about.fb.com/news/2026/06/reels-is-shaping-indias-video-first-future-across-gen-z-women-bharat/)). YouTube Shorts were stated at **over 200 billion daily views** ([YouTube Blog / Cannes, 18 Jun 2025](https://blog.youtube/news-and-events/neal-mohan-cannes-2025/)); from **31 Mar 2025** YouTube counts a Shorts view on every start/replay with no minimum watch time, so that figure is not comparable to older “engaged views” ([The Keyword compilation](https://www.thekeyword.co/stats/youtube-shorts), 2025–2026).

**Do not claim** “X million Indians lose saved Reels.” Claim: Reels are a default discovery screen in India (Meta’s own survey); native Save is a bookmark; people already pay ReciMe to extract one vertical.

### 3.2 Target users

- **Technical learners (primary ICP for launch):** track GitHub repositories, AI models, CLI tools, developer workflows, courses and documentation references discovered on Reels/Shorts and on the desktop.
- **Researchers and creators:** collect product ideas, quotes, tutorials and explanations from different apps.
- **General learners:** recipes, finance education, career tips, languages or any custom topic.
- **Desktop/browser knowledge workers:** capture tools, links, quotes and explanations from websites, long YouTube videos, articles and documentation.
- **Multi-device users:** watch short videos on the phone and read/watch long-form on a computer; want one library.
- **Accountants, finance and business operators:** save report layouts, procedures and how-to steps from Tally, Excel, ERP, with strong protection for financial data.
- **Designers and 3D creators:** save references, tool settings and ideas, often with a spoken idea or an AI “imagination” sketch.
- **AI power users:** keep useful answers, prompts and ideas from AI chat assistants alongside everything else.

Launch ICP recommendation (from marketing research, 17 Oct 2026): **India + global Android developers and technical learners** who already live on GitHub / F-Droid / Obtainium. Recipe mass-market is ReciMe’s; Hinglish general memory is BhaiFinder’s.

### 3.3 Core jobs to be done

1. “While watching a Reel, I want relevant information saved for me without repeatedly taking screenshots.”
2. “I want the entire visible frame, because the useful name or link might appear at any corner.”
3. “I want the complete available explanation, not a screenshot without voice context.”
4. “I want an optional one-tap Save or Skip even when content is unrelated to my configured topics.”
5. “I want one library that I can search by topic, spoken words, visible text, creator and source when known.”
6. “While reading a website or article, I want the useful part and its exact link saved without copying text and bookmarks by hand.”
7. “While watching a long YouTube video, I want to save the moment that matters, with its frame, what was said around it and the video timestamp when available.”
8. “I want the same Observer, topics and library behavior whether I am on my phone, in my browser or on my desktop.”
9. “When something strikes me, I want to say my idea out loud and have it saved together with what I was looking at.”
10. “At the end of the day I want to open one place and see everything I researched today, organized like bookmarks.”
11. “What I save in the browser should be on my phone, and the other way round.”
12. “I want to ask the Observer what I saw yesterday, or ask it to imagine my idea as an image.”

### 3.4 Not the product

OBZRAi is **not** a hidden surveillance app, universal DRM bypass, unrestricted downloader, covert screen recorder, social-media scraper, web crawler, browsing-history tracker, keylogger, replacement for Instagram/YouTube bookmarks, always-on device monitor without informed consent, cloud-first service, always-listening voice recorder, employee/workplace monitoring tool, or a way to copy movies or DRM-protected content.

Every platform observes only inside an explicit, visible, user-started session or user action.

### 3.5 Supported content types

The same Observe → Match/Save → Organize → Search loop applies to every type. Per-type extraction is in §14.7. Platform availability is in §5.4.

| Type | What is saved | Primary surfaces | Special rules |
|---|---|---|---|
| Short-form video (Reels, Shorts, TikTok) | Full frame + permitted audio/transcript | Android first; also browser/desktop | Audio often blocked by source policy |
| Long-form video (YouTube and others) | **Moments**, not whole videos: frame + bounded audio window + URL/timestamp when genuinely available | Browser/desktop strongest for exact URL | Never claim full-video archive |
| Websites and web apps | Visible viewport, readable text, exact URL when the platform exposes it | Browser extension; Android via projection | No crawling of other pages |
| Articles, blogs, documentation | Visible/selected text + frame + title/URL | Browser extension | Full-text save is an explicit user action |
| Other on-screen (PDFs, slides, code, images) | Full frame + OCR | All | No URL unless genuinely available |
| Accounting / business (Tally, Excel, ERP) | Frame + OCR + spoken note | Desktop; Android | **Per-app opt-in**, manual save default (D6) |
| AI chat assistants | Visible prompt/answer as frame + text + URL when available | Browser, desktop, mobile app | Private human chats stay sensitive |
| Design and 3D tools | Canvas/viewport + panels; spoken idea; optional imagination | Desktop | Low sampling; mostly manual save |
| Movies / streaming | Time + source + idea; frame only if capturable | All | DRM → `capture_blocked`; never bypass |

---

## 4. Success criteria, quality bar and metrics

All numbers in this section are **targets**, not established results.

### 4.1 North-star measure

**Useful saves per active observation hour:** items the user retains and later opens, shares or searches after the first save.

**Target (beta, labelled as target):** ≥ 3 useful saves per active observation hour among weekly-active observers, measured from local events (save, later open/search/share within 7 days). Do not publish this number externally until measured.

### 4.2 Input metrics (targets)

| Metric | Target | Measurement |
|---|---|---|
| Session start completion | ≥ 70% of users who begin first-run complete OS capture consent and start a session | Funnel on-device / opt-in diagnostics |
| Manual Save used in first session | ≥ 50% of first sessions include at least one Save | Local events |
| Today view opened within 24 h of first save | ≥ 40% | Local events |
| Spoken idea attached | ≥ 20% of saves in weeks 2–4 among mic-opted-in users | Local events |
| 7-day return (opened Library) | ≥ 25% of users who saved ≥ 3 items | Local events |

### 4.3 Release quality targets (Android MVP)

| Metric | Initial acceptance target | Measurement |
|---|---|---|
| Full-frame screenshot fidelity | 100% of saved original frames preserve the actual captured bounds/orientation; no product-introduced cropping | Pixel/bounds comparison against projection frame on supported test devices |
| Overlay interaction | Tap menu opens in under 200 ms on reference test devices | UI instrumentation; p95 |
| Non-interruption | No custom blocking progress dialog, forced app switch or pause during automatic processing | Manual UX matrix and UI tests |
| Classification | ≥ 80% precision on a labeled 200-item multi-topic test set; recall separately reported | Offline benchmark, pre-release target |
| Search | Query shows matching title/OCR/transcript within 500 ms for a 1,000-item local test library | Benchmarked Room FTS query, p95 |
| Data custody | Zero external uploads by default; all test items deletable via app UI | Network inspection and deletion tests |
| Crash-free sessions | ≥ 99% during public beta, after sufficient observations | Opt-in anonymous diagnostics or local logs |
| User comprehension | ≥ 90% of test participants correctly understand when screen capture is active | 10-user moderated test |

Battery, thermal and STT accuracy targets must be established during feasibility work on at least one entry-level and one midrange Android phone. Do not claim performance before profiling.

React Native, browser extension and desktop clients reuse the same metric *definitions*; numeric targets are set during each platform’s own spike and are not claimed in advance.

### 4.4 Guardrails (targets)

| Guardrail | Target |
|---|---|
| Default network | 0 bytes of capture content uploaded unless the user enabled sync or a cloud-AI feature |
| Play / store rejection | Treat a MediaProjection+overlay rejection as a distribution incident; FOSS channels remain the fallback |
| False “Saved” on DRM | 0 false green-check on `capture_blocked` items (S18) |
| Silent data loss on sync | 0 in conflict-test suite (S14) |
| AccessibilityService | Not shipped in MVP |

### 4.5 Product quality bar

Every release on every platform must meet this bar before it is called done:

| Area | Bar |
|---|---|
| Reliability | All P0 acceptance criteria (§13) pass on the supported test matrix; crash-free target from §4.3 met in beta; no data loss on crash, update, sync conflict or low storage. |
| Performance | §4.3 targets met and measured, not assumed; no perceptible slowdown of the host app, page or video. |
| Design fidelity | Approved overlay identity and minimal UI implemented exactly; no placeholder screens, lorem ipsum, debug UI or unfinished states in release builds. |
| Privacy and security | Privacy review, threat model and permission audit completed per platform; encryption at rest and in transit for synced data; no raw content in logs. |
| Accessibility | Screen-reader labels, reduced motion, contrast and scalable text verified. |
| Honesty | Copy says “when permitted”; DRM/audio limits are shown in onboarding and on blocked items. |

---

## 5. Scope, priorities and exclusions

### 5.1 P0 — Android MVP must ship

- Explicit observation session with OS MediaProjection consent; never auto-start on boot.
- Approved floating ring (geometry, states, Save/Skip/Settings).
- Full-frame capture; overlay exclusion or documented hide-and-settle.
- On-device OCR; topic classifier; manual Save/Skip.
- Playback audio when permitted; honest unavailable/blocked states; timestamped transcript when audio exists.
- Local persistence; searchable library (OCR, transcript, title, topic, tags, voice-idea text).
- Source integrity (no fabricated URLs).
- Safe stop on user stop, chip tap, lock (Android 15 QPR1+), revoke, kill.
- Privacy: no upload by default; permissions explained.
- Low distraction: no blocking dialogs.
- Export/delete; session indicators (OS chip + FGS notification).
- Content-type agnostic observation (`content_type` recorded).
- Shared Observer core contract (even if still Kotlin-only).
- **Today view**, **user tags**, **provenance narrative**, **spoken idea notes** (push-to-talk).
- Offline-first capture/library/search/export.
- Protected/DRM screens labelled `capture_blocked`.
- Capture record completeness (resource, time, source, voice idea when given).
- Banking/payment/password apps excluded by default.

### 5.2 P1 — after first stable Android release

- Rolling audio buffer (bounded, in-session only).
- Dedup grouping; semantic search (on-device embeddings).
- Browser extension observation; web/article text; long-video moments.
- Desktop observation (gated on S1–S4 + desktop spike).
- Site/app exclusions (user-editable); accounting per-app opt-in.
- Collections/folders; research export (archive, Markdown/HTML, CSV).
- Conversational assistant (voice + text intents); “find what I saw.”
- Imagination images; AI agents (summarise, tag, cluster, Q&A) on-device default, cloud opt-in.
- Optional account; E2E encrypted sync; conflict handling; cross-device availability.
- Optional redaction for opted-in business apps.

### 5.3 P2 — optional research

- iOS companion (ReplayKit / later ScreenCaptureKit paths).
- React Native client parity.
- Wake word (opt-in, on-device only).
- Firefox extension; full-page capture; Linux as a first-class desktop SKU if S11 passes.

### 5.4 Platform phasing

| Phase (this PRD) | What ships | Gate |
|---|---|---|
| Phase 0 | Android feasibility S1–S4 | Go/no-go (see §19) |
| Phase 1 | Android MVP (P0) | P0 AC + quality bar |
| Phase 2 | Chromium MV3 browser extension | S1–S4 + S9 + S10 |
| Phase 3 | Desktop (macOS, then Windows; Linux if S11 passes) | S1–S4 + S9 + S11 |
| Phase 4 | Optional E2E sync, React Native, iOS discovery | S12, S14; D1 confirmed |

Order of Phases 2–4 may change with owner priority. Phase 5 in v1.2 terms (core extraction / S9) is a **prerequisite** of Phases 2–4, not a user-facing release.

### 5.5 Exclusions for V1 Android

- Mandatory sign-in or cloud backend.
- AccessibilityService.
- FLAG_SECURE / DRM bypass; movie downloaders; Instagram/YouTube scrapers.
- Always-on capture; boot-start observation; wake word.
- Workplace/employee monitoring mode.
- iOS app; paid Play billing (unless Pro is explicitly scheduled).
- Fabricated source URLs or invented transcripts.

---

## 6. Platform API and constraint validation

v1.2 §8 was directionally right and incomplete. This section corrects it with live sources as of **17 October 2026**.

**Corrections to v1.2**

| v1.2 statement / gap | v2.0 correction | Source |
|---|---|---|
| Single-app vs entire-screen framed as a generic “may offer” without version | **App screen sharing / single-app picker is Android 14** (API 34). Pixel shipped it in **Android 14 QPR2** (Mar 2024). It is not an Android 15-only feature. | [Android media projection docs](https://developer.android.google.cn/media/grow/media-projection?authuser=0) (page live at research); [9to5Google, 15 Nov 2023](https://9to5google.com/2023/11/15/android-14-qpr2-single-app-recording/); [Forasoft, 1 Aug 2026](https://www.forasoft.com/blog/article/screen-sharing-android-1644) |
| Status-bar capture indicator underspecified | **Android 15 QPR1+**: large status-bar **chip** for projection/cast/record; tap to stop; **projection stops when the device is locked**. Apps must implement `MediaProjection.Callback.onStop()`. | [Android media projection — status bar chip](https://developer.android.com/about/versions/12/12L/features/media-projection-large-screens); [Android Authority, 11 Oct 2024](https://www.androidauthority.com/android-15-qpr1-stop-screen-shares-3489652/); [9to5Google, 12 Sep 2024](https://9to5google.com/2024/09/12/android-15-screen-recording-cast-chip/) |
| Chrome capture rate limit not stated | `chrome.tabs.MAX_CAPTURE_VISIBLE_TAB_CALLS_PER_SECOND` = **2** (Chrome 92+) | [Chrome `tabs` API](https://developer.chrome.com/docs/extensions/mv2/reference/tabs) (docs last seen 28 Sep 2026) |
| macOS “Screen Recording permission” only | Sequoia (macOS 15)+ **recurring prompts** if the app bypasses the system picker. Use **`SCContentSharingPicker`**. Audio via ScreenCaptureKit `capturesAudio`. | [9to5Mac, 14 Aug 2024](https://9to5mac.com/2024/08/14/macos-sequoia-screen-recording-prompt-monthly/); [Apple `SCContentSharingPicker`](https://developer.apple.com/documentation/screencapturekit/sccontentsharingpicker) (docs 7 Oct 2026) |
| Windows “border/indicator on supported versions” | Graphics Capture draws a **yellow border**. `IsBorderRequired` (Windows 10 2104 / build 20348+) can hide it only after `GraphicsCaptureAccessKind.Borderless` consent and `graphicsCaptureWithoutBorder`. DRM / `WDA_MONITOR` / protected media path → black. | [Microsoft Learn, Screen capture](https://learn.microsoft.com/en-us/windows/apps/develop/media-authoring-processing/screen-capture) (27 Aug 2026); [IsBorderRequired](https://learn.microsoft.com/en-us/uwp/api/windows.graphics.capture.graphicscapturesession.isborderrequired?view=winrt-28000) (5 Aug 2026) |
| RN/iOS: “no system-wide overlay” only | iOS **Broadcast Upload Extension ~50 MB memory hard cap** (`EXC_RESOURCE` / jetsam). No Android-style `SYSTEM_ALERT_WINDOW`. RN has **no first-class screen-capture API**. | [Apple Forums thread 651367](https://developer.apple.com/forums/thread/651367); [Twilio ReplayKit README](https://github.com/twilio/video-quickstart-ios/blob/master/ReplayKitExample/README.md); [Forasoft iOS screen sharing, 10 Oct 2026](https://www.forasoft.com/blog/article/how-to-implement-screen-sharing-in-ios-1193) |
| Play target SDK not stated | New apps/updates: **target API 36 (Android 16) as of 31 Aug 2026** (extension to 1 Nov 2026 cited by Forasoft). Re-verify at submission. | [Forasoft, 1 Aug 2026](https://www.forasoft.com/blog/article/screen-sharing-android-1644) |
| Developer verification absent | Protections began **30 Sep 2026** in **Brazil, Indonesia, Singapore, Thailand** on certified Android 7+ via seven participating stores; **global expansion planned 2027**. Advanced sideload flow: Developer options + reboot + **24-hour wait**. | [Android developer verification](https://developer.android.com/developer-verification) (30 Sep 2026); [Android Authority, 1 Oct 2026](https://www.androidauthority.com/android-sideloading-developer-verification-first-wave-rollout-3717921/); [Ars Technica, 19 Mar 2026](https://arstechnica.com/gadgets/2026/03/google-details-new-24-hour-process-to-sideload-unverified-android-apps/) |

### 6.1 Android — MediaProjection, FGS, overlay, audio, FLAG_SECURE

**Consent and token.** Apps targeting Android 14+ must request user consent **before each media projection session**. A session is a single `createVirtualDisplay()` call. A `MediaProjection` token must be used only once. Reusing a cached intent throws `SecurityException` on Android 14+ ([Android media projection docs](https://developer.android.google.cn/media/grow/media-projection?authuser=0); [Android 14 behavior changes](https://developer.android.com/about/versions/14/behavior-changes-14)). Forasoft (1 Aug 2026) reports Android 16 additionally blocks caching the token across app restarts. **Product rule:** never silently reopen projection; always run the OS picker.

**Single-app vs entire screen.** Android 14 introduces **app screen sharing**: the consent UI lets the user share a **single app window** or the **entire display**. Single-app capture excludes status bar, navigation bar and notifications. Apps can force entire-display via `MediaProjectionConfig.createConfigForDefaultDisplay()`, which is the wrong default for a privacy-first observer. **Product rule:** prefer **single-app** as the guided default (matches Play review and user trust); allow entire-screen when the user chooses it in the OS dialog. Handle pause when the user switches away from a single-app share (task-switcher strings exist in Android 15; treat as capability to detect in S1).

**Foreground service.** Targeting API 34+: declare `android:foregroundServiceType="mediaProjection"`, `FOREGROUND_SERVICE` and `FOREGROUND_SERVICE_MEDIA_PROJECTION`. Call `createScreenCaptureIntent()` **before** starting the FGS; then `startForeground()`; then `getMediaProjection()` ([Android FGS types](https://developer.android.com/develop/background-work/services/fgs/service-types); [Play Console Help — FGS declaration](https://support.google.com/googleplay/android-developer/answer/16965181)).

**Play Console.** For each FGS type, Play requires a use-case selection and **a video demonstrating the steps the user takes to trigger the feature**. Listed use case: “Media or content projection and streaming or recording with MediaProjection API.” The service must be user-initiated, user-perceptible, stoppable, and only as long as needed ([Play Console Help](https://support.google.com/googleplay/android-developer/answer/16965181); [Device and Network Abuse](https://support.google.com/googleplay/android-developer/answer/16559646)). **Product rule:** demo video shows a **tutorial website or YouTube**, OS chip visible, Stop working; **not** Instagram scraping.

**Status-bar chip (Android 15 QPR1+).** Large chip alerts that projection is in progress; tap stops sharing; projection **auto-stops on lock**. Implement `onStop()` and release VirtualDisplay, audio, buffers ([Android docs — status bar chip](https://developer.android.com/about/versions/12/12L/features/media-projection-large-screens); [Mirrativ, 17 Dec 2024](https://tech.mirrativ.stream/entry/2024/12/17/103000)).

**AudioPlaybackCapture (API 29+).** Capture of other apps’ playback requires **all** of: usage `USAGE_MEDIA` / `USAGE_GAME` / `USAGE_UNKNOWN`; capture policy **`ALLOW_CAPTURE_BY_ALL`** (most restrictive of manifest `allowAudioPlaybackCapture`, `AudioManager.setAllowedCapturePolicy`, and per-player `AudioAttributes` wins); same user profile. `ALLOW_CAPTURE_BY_SYSTEM` and `ALLOW_CAPTURE_BY_NONE` block third-party capture. Apps targeting < Q must explicitly opt in ([Android AV capture](https://developer.android.com/media/platform/av-capture); [AudioPlaybackCaptureConfiguration](https://developer.android.google.cn/reference/android/media/AudioPlaybackCaptureConfiguration), 3 Aug 2026). Instagram, Netflix and many browsers **opt out**; S4 must log this honestly. **Never** treat a silent stream as a successful recording.

**FLAG_SECURE.** Content is excluded from MediaProjection. Play **forbids bypassing** other apps’ `FLAG_SECURE` ([Play Device and Network Abuse](https://support.google.com/googleplay/android-developer/answer/16559646)). Samsung documents Circle to Search failing in capture-restricted apps ([Samsung US support, 5 Oct 2026](https://www.samsung.com/us/support/answer/ANS10000753/)).

**SYSTEM_ALERT_WINDOW.** Overlay requires Settings → Display over other apps. Android 12+ tapjacking limits apply. On **Android 15+**, Display over other apps is a **restricted setting for sideloaded APKs**; the user must enable it per app ([Android Authority, 12 Sep 2024](https://www.androidauthority.com/android-15-sideloaded-apps-restrictions-3481098/); [9to5Google, 12 Sep 2024](https://9to5google.com/2024/09/12/android-15-sideloaded-apps-restrictions/)). FOSS/GitHub installs will hit this. In-app instructions are P0.

**Developer verification 2026–27.** From **30 September 2026**, apps installed from participating stores (Google Play, HONOR, OPPO, Galaxy Store, Palm Store, V-Appstore, GetApps) on certified Android 7+ in **Brazil, Indonesia, Singapore, Thailand** must be registered to a verified developer. Global expansion is planned for **2027**. Unregistered apps can still be sideloaded via ADB or the advanced flow (Developer options + reboot + 24-hour wait) ([developer.android.com/developer-verification](https://developer.android.com/developer-verification), 30 Sep 2026; [The Verge, 2 Oct 2026](https://www.theverge.com/tech/1003920/androids-developer-verification-rules-just-took-effect-for-the-first-time)). **India is not in the first wave**; 2027 is the planning horizon for Indian sideload UX. Register the developer identity before Play and before relying on GitHub APKs on certified devices in 2027.

**Min SDK proposal:** Android 10 / API 29 (playback-audio path). **Test matrix:** Android 10, 13, 14, 15, 16 and recent OEM skins. **Target SDK:** current Play requirement at publication (API 36 cited as of 31 Aug 2026 — re-verify).

**Policy:** no AccessibilityService in MVP.

### 6.2 Chrome / Chromium Manifest V3

| Desire | Reality | Product behavior |
|---|---|---|
| Observe every website | Restricted pages (chrome://, Web Store, some built-ins) cannot run content scripts; host permissions are user-granted | Minimal permissions; prefer `activeTab` + user gesture; show “Cannot observe this page” |
| Screenshot the tab | `chrome.tabs.captureVisibleTab` captures the **visible viewport**; quota **2 calls/second** (Chrome 92+, `MAX_CAPTURE_VISIBLE_TAB_CALLS_PER_SECOND = 2`) | Save visible viewport as the full frame; sample at ≤ 2 fps; full-page capture is a later explicit action |
| Tab audio | `chrome.tabCapture` only after user invocation (action click); MV3 service workers have no DOM — obtain `getMediaStreamId()` in the worker and consume in an **offscreen document** (Chrome 116+) | Audio only on user action inside a session; offscreen document for the stream |
| Desktop / window | `chrome.desktopCapture` / `getDisplayMedia` shows the OS picker | Use for desktop-in-browser only with explicit user choice |
| Side panel | `sidePanel` API is the natural Library surface in MV3 | Home/Library in the side panel; toolbar icon shows overlay states |
| DRM / EME | Protected video frames commonly capture **black** | Label `capture_blocked`; keep title/URL |
| Exact URL | Active tab URL/title with permission | `source_url_origin = browser_tab`; never read other tabs or history |

Sources: [Chrome tabs API](https://developer.chrome.com/docs/extensions/reference/api/tabs) / [MV2 tabs reference](https://developer.chrome.com/docs/extensions/mv2/reference/tabs) (28 Sep 2026); [tabCapture](https://developer.chrome.com/docs/extensions/reference/api/tabCapture) (11 Sep 2026); [Offscreen documents](https://developer.chrome.com/blog/Offscreen-Documents-in-Manifest-v3) (25 Jan 2023).

### 6.3 macOS — ScreenCaptureKit

- Permission: **Screen & System Audio Recording** (TCC). App must be quit and relaunched after grant ([Screenify Studio, 23 Apr 2026](https://www.screenify.studio/blog/2026-04-23-macos-screen-recording-permissions)).
- **macOS 15 Sequoia:** apps that bypass the system private window picker and call older APIs (`CGDisplayStream`, `CGWindowListCreateImage`) or generic `SCShareableContent` without the picker get a prompt: allow **for one month** (moved from weekly in beta). **15.1** reduced nags for regularly used apps; Apple told developers to migrate to **ScreenCaptureKit + `SCContentSharingPicker`** ([9to5Mac, 14 Aug 2024](https://9to5mac.com/2024/08/14/macos-sequoia-screen-recording-prompt-monthly/); [9to5Mac, 7 Oct 2024](https://9to5mac.com/2024/10/07/macos-sequoia-screen-recording-popups/); [TidBITS, 19 Aug 2024](https://tidbits.com/2024/08/19/apple-reduces-excessive-sequoia-permission-requests-shifts-to-monthly/)). **Product rule:** always present `SCContentSharingPicker`; never hide the OS indicator.
- Audio: `SCStreamConfiguration.capturesAudio = true` captures system audio under the Screen Recording permission; microphone is a separate TCC ([Qiita, 6 Oct 2026](https://qiita.com/TakeshiKawai/items/5d5fcb08e7a46608f4fa)).
- DRM / FairPlay / HDCP: protected video typically black; same `capture_blocked` labeling.

### 6.4 Windows — Graphics Capture and Recall

- **Windows.Graphics.Capture:** system picker for display or window; system draws a **yellow notification border** around the captured item ([Microsoft Learn, 27 Aug 2026](https://learn.microsoft.com/en-us/windows/apps/develop/media-authoring-processing/screen-capture)).
- **`IsBorderRequired`:** available from Windows 10 version 2104 (build 10.0.20348.0). Setting it false requires `GraphicsCaptureAccess.RequestAccessAsync(Borderless)` and the `graphicsCaptureWithoutBorder` capability. If the user denies, the border stays. Other apps capturing the same surface can force the border on ([Microsoft Learn API](https://learn.microsoft.com/en-us/uwp/api/windows.graphics.capture.graphicscapturesession.isborderrequired?view=winrt-28000), 5 Aug 2026). OBS collaborators report the borderless path is practical on Windows 11 and awkward for Win32 on Windows 10 ([OBS discussion, 12 Sep 2023](https://github.com/obsproject/obs-studio/discussions/9590)). **Product rule:** keep the yellow border by default (honesty); optional borderless only after explicit extra consent on Windows 11+.
- **Protected content:** PlayReady / Widevine L1 / hardware protected media path composites in a TEE; capture is black. `SetWindowDisplayAffinity(WDA_MONITOR)` also renders captures black; `WDA_EXCLUDEFROMCAPTURE` omits the window ([Capt. Meelo, 13 Sep 2026](https://captmeelo.com/research/2026/09/13/the-drm-flag-that-isnt-drm.html)). Never bypass.
- **Microsoft Recall:** Copilot+ PCs only (40+ TOPS NPU, 16 GB RAM, 256 GB storage, Hello ESS, BitLocker/Device Encryption). **Opt-in per user; off by default; local encrypted snapshots; Hello-gated.** GA on Copilot+ **25 April 2025**. Still present on Windows 11 26H2 for Copilot+ hardware, still opt-in ([Microsoft Support — Privacy and control](https://support.microsoft.com/en-us/windows/privacy/privacy-and-control-over-your-recall-experience); [Microsoft Support — Retrace your steps](https://support.microsoft.com/en-us/windows/ai/ai-features/retrace-your-steps-with-recall); [Techdows, 30 Sep 2026](https://techdows.com/does-windows-11-26h2-have-recall.html)). **Positioning:** Recall is continuous OS memory on a narrow PC SKU. OBZRAi is session-based, user-started, multi-platform, with spoken ideas. Do not imitate always-on snapshots.

### 6.5 DRM across platforms

| Protection | Typical capture result | Product |
|---|---|---|
| Android `FLAG_SECURE` | Black / empty | `capture_blocked`; no retry loop |
| Widevine L1 (Netflix, Prime, many Android TV/mobile apps) | Black video plane | Same |
| PlayReady SL3000 / Windows protected media path | Black | Same |
| FairPlay + HDCP (Apple TV / Safari / some Mac players) | Black | Same |
| Browser EME / encrypted media | `captureVisibleTab` / tabCapture often black | Keep URL/title; label blocked |
| Source-app `ALLOW_CAPTURE_BY_SYSTEM` / `NONE` | Video may capture; **audio silent** | `audio_unavailable` / `blocked_by_source` |

Onboarding copy (required): **“Some apps and protected videos cannot be captured; OBZRAi will tell you when that happens.”**

### 6.6 React Native and iOS

- React Native has **no first-class screen-capture or overlay API**. MediaProjection, overlay, playback-audio, OCR/ASR remain **native modules** (or Expo modules wrapping them). Bridging must not add capture latency or weaken Android lifecycle rules.
- **iOS** does not allow an Android-equivalent system overlay (`SYSTEM_ALERT_WINDOW` has no equivalent). In-app `RPScreenRecorder` records only the app’s own UI. System-wide capture is a user-started **Broadcast Upload Extension** (ReplayKit) until newer ScreenCaptureKit paths on later iOS; Forasoft (10 Oct 2026) describes ScreenCaptureKit as the recommended path on **iOS 27+** and ReplayKit BUE as still working and deprecated in that generation — **treat iOS 27+ ScreenCaptureKit as Unverified for this PRD’s purposes until spike S25**, because the Forasoft page is secondary.
- Broadcast Upload Extension: **~50 MB memory hard limit**; exceeding it kills the process (`EXC_RESOURCE` / jetsam) ([Apple Developer Forums](https://developer.apple.com/forums/thread/108854); [Twilio example](https://github.com/twilio/video-quickstart-ios/blob/master/ReplayKitExample/README.md)). OCR/ASR cannot live in the extension. Frames must be downsampled and handed to the main app via App Group.
- iOS capture UX is Control Center / system picker, not a 28 dp edge ring over other apps. **Do not promise the Android overlay on iOS.**

### 6.7 Linux (desktop, Phase 3)

xdg-desktop-portal **ScreenCast** + PipeWire. User picks a display/window in the portal UI. Availability of system audio depends on the portal and PipeWire session. Wayland does not allow silent full-screen grab. ([xdg-desktop-portal ScreenCast](https://flatpak.github.io/xdg-desktop-portal/docs/doc-org.freedesktop.portal.ScreenCast.html))

### 6.8 Platform constraints matrix

Legend: **Verified** = tied to a named source below. **Partial** = documented but OEM/app variance remains. **Unverified** = could not confirm on 17 Oct 2026.

| Constraint | Platform | Product implication | Status | Source | Date |
|---|---|---|---|---|---|
| Per-session MediaProjection consent; one-shot token | Android 14+ | Fresh OS picker every session; no token cache | Verified | [Android media projection](https://developer.android.google.cn/media/grow/media-projection?authuser=0); [A14 behavior](https://developer.android.com/about/versions/14/behavior-changes-14) | Docs live 17 Oct 2026 |
| Single-app vs entire-screen picker | Android 14; Pixel 14 QPR2 | Guide single-app default; save selected region only | Verified | [Android docs](https://developer.android.google.cn/media/grow/media-projection?authuser=0); [9to5Google](https://9to5google.com/2023/11/15/android-14-qpr2-single-app-recording/) | 15 Nov 2023 / docs live |
| FGS type `mediaProjection` + permission | Android 14+ target | Manifest + `startForeground` type; else `SecurityException` | Verified | [FGS types](https://developer.android.com/develop/background-work/services/fgs/service-types) | Docs live |
| Play FGS declaration + **demo video** | Google Play | Console form; video of user-triggered capture | Verified | [Play Help](https://support.google.com/googleplay/android-developer/answer/16965181) | Docs live |
| Status-bar chip; stop on lock | Android 15 QPR1+ | `onStop()` must release all resources | Verified | [Android docs chip](https://developer.android.com/about/versions/12/12L/features/media-projection-large-screens); [AA 11 Oct 2024](https://www.androidauthority.com/android-15-qpr1-stop-screen-shares-3489652/) | 11 Oct 2024 / docs |
| AudioPlaybackCapture needs `ALLOW_CAPTURE_BY_ALL` + MEDIA/GAME/UNKNOWN | Android 10+ | Many apps silent; never invent transcript | Verified | [AV capture](https://developer.android.com/media/platform/av-capture) | Docs live |
| FLAG_SECURE / Play no-bypass | Android + Play | Black frame; `capture_blocked` | Verified | [Play 16559646](https://support.google.com/googleplay/android-developer/answer/16559646) | Docs live |
| Overlay restricted on sideload | Android 15+ | In-app instructions for restricted settings | Verified | [AA 12 Sep 2024](https://www.androidauthority.com/android-15-sideloaded-apps-restrictions-3481098/) | 12 Sep 2024 |
| Developer verification first wave | BR, ID, SG, TH from 30 Sep 2026; global 2027 | Register developer; India sideload UX changes in 2027 | Verified | [developer-verification](https://developer.android.com/developer-verification) | 30 Sep 2026 |
| Play target API 36 | Play | Target Android 16 at submission | Partial | [Forasoft 1 Aug 2026](https://www.forasoft.com/blog/article/screen-sharing-android-1644) | 1 Aug 2026 |
| `captureVisibleTab` 2/sec | Chrome 92+ | Sampling ≤ 2 fps | Verified | [Chrome tabs API](https://developer.chrome.com/docs/extensions/mv2/reference/tabs) | 28 Sep 2026 |
| tabCapture + offscreen document | Chrome MV3 | User gesture; offscreen for MediaStream | Verified | [tabCapture](https://developer.chrome.com/docs/extensions/reference/api/tabCapture) | 11 Sep 2026 |
| DRM/EME black frames | Chrome, Android, desktop | Honest label | Verified | Platform docs + Samsung 5 Oct 2026 | Mixed |
| Sequoia recurring screen-recording prompt | macOS 15+ | Use `SCContentSharingPicker` | Verified | [9to5Mac 14 Aug 2024](https://9to5mac.com/2024/08/14/macos-sequoia-screen-recording-prompt-monthly/) | 14 Aug 2024 |
| ScreenCaptureKit audio | macOS 12.3+ / Sequoia path | `capturesAudio`; Screen Recording TCC | Verified | Apple docs / [Qiita 6 Oct 2026](https://qiita.com/TakeshiKawai/items/5d5fcb08e7a46608f4fa) | 6 Oct 2026 |
| WGC yellow border | Windows 10 1803+ | Keep visible by default | Verified | [Microsoft Learn](https://learn.microsoft.com/en-us/windows/apps/develop/media-authoring-processing/screen-capture) | 27 Aug 2026 |
| `IsBorderRequired` | Win10 2104+ / practical on Win11 | Extra consent to hide | Verified | [MS Learn API](https://learn.microsoft.com/en-us/uwp/api/windows.graphics.capture.graphicscapturesession.isborderrequired?view=winrt-28000) | 5 Aug 2026 |
| Recall opt-in Copilot+ only | Windows 11 Copilot+ | Not an Android competitor; privacy case study | Verified | [Microsoft Support](https://support.microsoft.com/en-us/windows/privacy/privacy-and-control-over-your-recall-experience) | Live Oct 2026 |
| RN no first-class capture | React Native | Native modules required | Verified | [RN native modules](https://reactnative.dev/docs/native-platform) | Docs live |
| iOS BUE ~50 MB | iOS ReplayKit extension | Downsample; no overlay; P2 | Verified | [Apple Forums](https://developer.apple.com/forums/thread/108854); Twilio README | Date not shown / repo live |
| iOS 27 ScreenCaptureKit as BUE replacement | iOS 27+ | Do not schedule on this | Unverified | [Forasoft 10 Oct 2026](https://www.forasoft.com/blog/article/how-to-implement-screen-sharing-in-ios-1193) secondary | 10 Oct 2026 |
| Linux portal ScreenCast | Wayland | Portal picker; no silent grab | Verified | [xdg-desktop-portal](https://flatpak.github.io/xdg-desktop-portal/docs/doc-org.freedesktop.portal.ScreenCast.html) | Docs live |
| Instagram automated collection prohibited | Meta ToS | No scraper/downloader; OS capture of user’s display only | Verified | [Instagram Terms](https://help.instagram.com/581066165581870/) | Live Oct 2026 |

---

## 7. Competitive landscape

Research date: **17 October 2026**. Live web + X. Star counts and prices move; treat as point-in-time.

### 7.1 Map

| Cluster | Products | Job | Capture model | Privacy posture |
|---|---|---|---|---|
| A. Continuous screen memory | Rewind→Limitless (dead into Meta); Screenpipe; Microsoft Recall | “What was on my screen earlier?” | Always-on / periodic snapshots | Local-first claims; high privacy scare |
| B. Screen-watching agents | Observer AI (Roy3838) | Agents watch screen and act | Local agents + optional cloud | FOSS local models; **name collision** |
| C. OS screenshot/search | Pixel Screenshots; Circle to Search / Lens | Search what’s on screen now | OS screenshot or live view | Pixel Screenshots moved partly to cloud (Jun 2026) |
| D. Link/share-sheet extractors | ReciMe; BhaiFinder; Recall.it; Glasp | Save a public URL into structured notes | Share sheet / paste link | Cloud + account |
| E. Read-later / clippers | Pocket (dead); Readwise Reader; Raindrop.io; mymind; Notion Web Clipper | Save articles and bookmarks | Extension / share | Mixed |
| F. Native Save | Instagram / YouTube Save | Bookmark in-silo | Platform | Not a backup |
| G. Downloaders | ReelSave and similar | Get the MP4 | URL fetch | ToS/copyright risk |

**OBZRAi sits in a gap:** session-based overlay capture + on-device OCR/STT + spoken idea + Research Hub + no account required. It is closer to a **personal research inbox** than to Rewind or ReciMe.

### 7.2 Competitor sheets

**Observer AI (Roy3838)** — GitHub [Roy3838/Observer](https://github.com/Roy3838/Observer); site [observer-ai.com](https://app.observer-ai.com/). Local LLM micro-agents watch screen/camera/audio. Plus **$5/mo**. GitHub listing ~**1.7k stars / 142 forks** at marketing-research time (date not shown on the GitHub UI scrape). **Highest naming and “AI is watching my screen” collision.** Different job (agents vs library).

**Pixel Screenshots** — Pixel-only screenshot library with OCR and NL search ([Google Pixel Help](https://support.google.com/pixelphone/answer/15312581)). June 2026: **no longer on-device only** — cloud / Private AI Compute ([9to5Google, 17 Jun 2026](https://9to5google.com/2026/06/17/pixel-screenshots-cloud-ai/); [Android Authority, 18 Jun 2026](https://www.androidauthority.com/pixel-screenshots-cloud-processing-3678819/)). Backlash: “Google just ruined my favorite Pixel feature” ([Android Authority, 24 Jun 2026](https://www.androidauthority.com/google-made-me-turn-off-pixel-screenshots-3680197/)). Closest **on-device screenshot memory**; not a live overlay; Pixel-only.

**Circle to Search / Google Lens** — Search what’s on screen; does not keep a personal library by default. Can fail in FLAG_SECURE / DRM apps ([Samsung US, 5 Oct 2026](https://www.samsung.com/us/support/answer/ANS10000753/)).

**ReciMe** — Share Reel/TikTok/web → recipe. Play **1M+**, ~4.7★ / ~108k ratings ([Play, updated 1 Oct 2026](https://play.google.com/store/apps/details?id=com.recime.app)). iOS 4.8 / ~313k ratings. Free tier cited as 5 imports/week in 2026 roundups; Plus/Premium App Store **$9.99–$59.99**, US annual **$39.99** in one 2026 writeup (verify at App Store at launch). Proves “save Reels for later” converts. Cloud, account, one vertical.

**BhaiFinder** (BharatAI, Bareilly) — [bhaifinder.com](https://bhaifinder.com/). India Hinglish AI memory via share sheet. Free **20 saves/mo**; Pro **₹99/mo**; Pro Max **₹199/mo** ([Stork, 30 Sep 2026](https://www.stork.ai/en/bhaifinder)). Play **100+** downloads (early). Same pain line; not overlay; cloud.

**Recall.it** — [recall.it](https://www.recall.it/about). Save YT/articles/PDFs/podcasts → AI cards. Free 10 AI summaries/mo; Plus **$10/mo** yearly; Max **$38/mo** yearly ([recall.it/pricing](https://www.recall.it/pricing)). Claims **700,000+** users (about page, Oct 2026). Links, not live overlay.

**Readwise Reader** — Full **$9.99/mo** yearly / **$12.99** monthly; Lite **$5.59/mo** yearly ([Marqly, checked 6 Oct 2026](https://www.marqly.com/blog/readwise-reader-review-2026)). Play **100k+** ([Play, 7 Oct 2026](https://play.google.com/store/apps/details?id=com.readermobile)). Power readers; YouTube via share/extension.

**mymind** — Guest 100 cards; Bookmarker **$4.99/mo**; Student **$7.99 / $79/yr**; Mastermind **$12.99 / $129/yr** ([mymind.com/pricing](https://mymind.com/pricing), live). Play 100k+, ~3.5★ ([androidrank, 9 Oct 2026](https://androidrank.org/application/mymind/com.mymind.access)). Privacy positioning; not video-native.

**Glasp** — Free; Pro **$12.50/mo** yearly ($150/yr); Unlimited **$30/mo** yearly after 1 May 2026 hike ([Glasp blog, 27 Mar 2026](https://blog.glasp.co/price-update-2026-05-01/)). YouTube transcripts in-browser.

**Raindrop.io** — Unlimited free bookmarks; Pro **~$3/mo or $28/yr** (US web, [ContextBolt, 10 Sep 2026](https://contextbolt.com/blog/raindrop-pricing/); App Store ~$2.99 / $27.99 in other 2026 roundups). Visual bookmark manager. Not overlay, not transcripts of Reels.

**Notion Web Clipper** — Clipper free; Notion Plus **$10/member/mo** yearly, Business **$20** ([notion.com/pricing](https://www.notion.com/pricing) cited via [Marqly](https://www.marqly.com/compare/raindrop-vs-notion-web-clipper), Oct 2026). Saves into a workspace, not a session observer.

**Pocket** — Shut **8 Jul 2025**; export closed **12 Nov 2025** ([Mozilla, 22 May 2025](https://blog.mozilla.org/en/mozilla/building-whats-next/); [Support, 12 Nov 2025](https://support.mozilla.org/en-US/kb/future-of-pocket)). MacRumors paraphrased “over 10 million users” (22 May 2025). Vacuum in save-later; replacements are articles, not Reels.

**Rewind → Limitless** — Meta acquired Limitless **5 Dec 2025**; Rewind capture disabled **19 Dec 2025** ([TechCrunch, 5 Dec 2025](https://techcrunch.com/2025/12/05/meta-acquires-ai-device-startup-limitless/); [9to5Mac, 5 Dec 2025](https://9to5mac.com/2025/12/05/rewind-limitless-meta-acquisition/)). Category existed; **exited**. `rewind.ai` is now unrelated.

**Screenpipe** — Desktop continuous screen+audio memory, local. GitHub **~22k stars**, **~2.2k forks**, updated 17 Oct 2026 ([github.com/screenpipe/screenpipe](https://github.com/screenpipe/screenpipe)). License moved from MIT to **Screenpipe Commercial License** (source-available; personal/non-commercial free; commercial use paid) ([Screenpipe blog, 9 Jun 2026](https://screenpipe.com/blog/screenpipe-license-update)). App pricing aggregators reading screenpipe.com on **1 Oct 2026**: Free; **Basic $21/mo** ($250/yr); **Business $42/mo** ($500/seat/yr); Enterprise on request ([Position Is Everything, 16 Oct 2026](https://www.positioniseverything.net/tools/screenpipe/pricing/)). Older writeups still say **$25 / $50**. **Unverified at screenpipe.com/pricing itself** (aggregator). Closest *desktop always-on* analogue; opposite of OBZRAi’s session model.

**Microsoft Recall** — See §6.4. Privacy case study: always-on screen memory is toxic unless opt-in, local, filterable, hardware-gated.

**Xeoma “AI Observer”** — On-prem CCTV VLM ([felenasoft.com](https://felenasoft.com/xeoma/en/articles/ai-observer/), page seen 4 Oct 2026). Different category; leftover collision for the old name.

**OBSERVER Ai Weather** — Play weather app, updated 27 Jul 2025.

### 7.3 Positioning map (qualitative)

```text
                    always-on memory
                           ▲
           Screenpipe      │      Microsoft Recall
           Limitless†      │
                           │
   local-first ◄───────────┼───────────► cloud / account
           Observer AI     │      ReciMe, BhaiFinder
           Pixel (was)     │      Recall.it, Readwise
                           │
                    session save
                           │
                      ★ OBZRAi
                    (overlay + Hub
                     + spoken idea)
                           ▼
                    bookmark / clip
              Raindrop, Notion, Pocket†
```

† Product exited.

### 7.4 Differentiation (what nobody else does)

Live **translucent overlay** on whatever is on screen, **user-started MediaProjection** (or OS equivalent), **on-device OCR + STT**, **topic match**, **full-frame + transcript + spoken idea + provenance** in a **local searchable Research Hub**, **no account**. Adjacent products each drop one of: overlay, on-device, video, no-account, cross-app, speech, honesty about DRM.

**Messaging (recommendation):** “You tap Save. The ring only runs when the OS capture indicator is on.” Avoid “AI is watching you.” Lead India GTM with technical learners, not recipe mass-market.

**X search 17 Oct 2026:** query `OBZRAi OR obzrai` returned **no posts**. Demand sentiment for the *problem* is loud (“saved reels graveyard”); the *brand* is unused on X.

---

## 8. Pricing and business model

### 8.1 Options

| Model | How it works | Fits OBZRAi? | Risks |
|---|---|---|---|
| **A. FOSS free forever** | Apache-2.0 app; donations | Matches ICP and local-first | Sync/AI cost unpaid; Play review still needed if listed |
| **B. Freemium** | Free local core; paywall library size / AI / sync | Familiar | Play billing on India until 2027 is 15/30%; paywalling local capture would betray the promise |
| **C. One-time paid** | Paid APK / desktop license | Simple | FOSS channel conflict; no sync revenue; refunds |
| **D. Subscription** | Monthly/yearly Pro | Covers hosted sync + AI | Must not gate core capture |
| **E. BYO API key** | User pastes OpenAI/Anthropic/xAI/Gemini key | Cloud AI without us holding margin | Support burden; key leakage UX |
| **F. Open-core** | Source open; hosted sync/AI proprietary or dual-license | Screenpipe’s 2026 move is a cautionary analogue | Community trust if core is later relicensed |
| **G. Play billing only** | IAP for Pro | Required for Play digital goods | Fees (below); FOSS users skip Play |

### 8.2 Comparable price points (dated)

| Product | Price | Source | Date |
|---|---|---|---|
| Observer AI Plus | $5/mo | observer-ai.com | Live Oct 2026 |
| BhaiFinder Pro / Pro Max | ₹99 / ₹199 per month | [Stork](https://www.stork.ai/en/bhaifinder) | 30 Sep 2026 |
| Raindrop Pro | $3/mo or $28/yr | [ContextBolt](https://contextbolt.com/blog/raindrop-pricing/) | 10 Sep 2026 |
| Readwise Reader | $9.99/mo yearly | [Marqly](https://www.marqly.com/blog/readwise-reader-review-2026) | 6 Oct 2026 |
| ReciMe Premium (one writeup) | ~$39.99/yr US | Marketing research compilation | 2026 |
| Recall.it Plus / Max | $10 / $38 per month yearly | recall.it/pricing | Live Oct 2026 |
| mymind Bookmarker / Mastermind | $4.99 / $12.99 per month | mymind.com/pricing | Live Oct 2026 |
| Glasp Pro | $12.50/mo yearly | [Glasp blog](https://blog.glasp.co/price-update-2026-05-01/) | 27 Mar 2026 |
| Screenpipe Basic / Business | $21 / $42 per month (annual) | [PIE aggregator](https://www.positioniseverything.net/tools/screenpipe/pricing/) citing screenpipe.com | 1 Oct 2026 |
| Notion Plus | $10/member/mo yearly | notion.com/pricing via Marqly | Oct 2026 |

### 8.3 Google Play fees (India vs global)

**Current (until Rest-of-World cutover):** Google’s long-standing structure is **15% on the first $1M** of developer earnings per year and **30% above**, with 99% of developers said to qualify for 15% or less ([Play India alternative billing Help](https://support.google.com/googleplay/android-developer/answer/13306652)). India already allows **alternative billing alongside Play**; if the user pays through the alternative system, the Play service fee is **reduced by 4%**.

**New split (US/UK/EEA from 30 Jun 2026; Australia 30 Sep 2026; JP/KR 31 Dec 2026; Rest of World including India 30 Sep 2027):** service fee separated from billing fee; first $1M / subscriptions cited at **10%** service in launch markets plus **5%** Play billing fee in US/UK/EEA; alternative billing or web links avoid the billing fee ([The Verge, 24 Jun 2026](https://www.theverge.com/policy/956296/google-play-app-store-alternative-billing-fee-antitrust); [Android Authority, 24 Jun 2026](https://www.androidauthority.com/google-play-billing-changes-3681025/); [ecorpit, 19 Jul 2026](https://ecorpit.com/google-play-service-fee-split-2026-app-economics/)). Exact Rest-of-World billing-fee percentage **not announced** as of this research.

**Implication for an Indian owner:** for Indian users on Play, budget **15% (or 11% with alternative billing)** until **30 September 2027**, then re-model. Selling to US/UK/EEA users is already on the new card as of 30 Jun 2026.

### 8.4 Recommended model

**Open-core + FOSS local-first + optional Pro + BYO key.**

| Tier | Price (targets, not launched) | Includes |
|---|---|---|
| **Free / FOSS** | ₹0 / $0 forever | Session observe, overlay, full-frame saves, on-device OCR, on-device ASR (user-downloaded model), Today/Library/tags, export, no account |
| **BYO key** | ₹0 platform fee | User’s own cloud LLM / image API for assistant, summarise, imagination. Keys stored in OS keystore. Disclosure of what is sent. |
| **Pro (hosted)** | **India: ₹149/mo or ₹1,499/yr** · **Global: $4.99/mo or $39/yr** | Optional account, **hosted E2E ciphertext relay** (full-res in Phase 4 / D1; thin payload in Phase 2 only if D7-B uses a relay), optional hosted AI quota, priority support. Local app remains fully usable if Pro lapses (library stays on device). **User-owned storage** for D7-B (Drive appData / WebDAV) stays on the Free tier — the user pays Google/their NAS, not OBZRAi. |

Rationale: ReciMe/BhaiFinder prove willingness to pay for *rescue of saves*, but the ICP is FOSS-sensitive. Charging for local capture would collide with Pixel-cloud backlash and with Graphene/F-Droid reviewers. Hosted sync is the first real COGS. INR list should sit near BhaiFinder Pro (₹99–199) without undercutting into dump-price. USD list should sit near Raindrop/Readwise Lite, far below Screenpipe’s always-on desktop pricing.

**Not recommended for V1:** paid one-time APK as the only model; paywalling Save; ads; selling user content.

**Play vs FOSS tracks:** same UX. Pro IAP on Play; license key or Stripe/UPI on desktop and sideload. Alternative billing in India when Pro ships. Launch packaging, lifetime-as-P2, and CWS payment realities are in **§22.4**. Do not paywall Save.

---

## 9. Recommendations on pending decisions D1–D7

These were “pending Basant’s confirmation” in v1.2. v2.0 records a **clear recommendation** for each. Owner may override.

### D1 — Account and E2E sync

| | |
|---|---|
| **v1.0/v1.1** | On-device storage; account and cloud not required; no mandatory sign-in; multi-device sync was P2. |
| **v1.2 proposal** | Local-first default; optional account + E2E sync in a follow-up phase. |
| **v2.0 recommendation** | **Adopt v1.2: optional, off by default, E2E encrypted, Phase 4.** Android MVP ships with **no account**. Local export/import (encrypted archive) is the V1 multi-device path. **v2.1 keeps this as the default for full-resolution media.** See D7 for a pending option to pull a *minimal* payload earlier. Continuity per phase is in §24. |
| **Rationale** | Owner wants browser ↔ mobile. That needs a sync service. Making it default would reverse the privacy promise and the Pixel-cloud lesson. E2E keeps the server as a ciphertext relay. |
| **Risks** | Key-recovery UX is hard (lose all devices → lose ciphertext). Hosting cost. DPDP “data fiduciary” duties once an account exists. Mitigation: recovery-key printout; self-hostable relay as a later option; device-only flag for sensitive collections. |

### D2 — Cloud AI opt-in

| | |
|---|---|
| **Earlier** | All processing on-device; generative summary only when capable and labeled. |
| **v1.2** | Agents on-device where possible; cloud + image APIs opt-in per feature. |
| **v2.0 recommendation** | **Adopt, and add BYO API key as the first cloud path.** Hosted AI is Pro-only later. Default provider for imagination is **off**. Prefer providers that contractually do not train on user content. If the project uses an in-house Grok/xAI path later, it is still opt-in and disclosed. |
| **Rationale** | Cloud AI is the only practical path for imagination and strong Q&A on midrange phones. Sending screenshots of Tally or private chats by default is unacceptable under DPDP and under the product promise. |
| **Risks** | Cost, provider lock-in, accidental send of redacted/sensitive items. Mitigation: per-feature switch; never send `redacted` or `device_only` items without a per-request confirm; usage meter. |

### D3 — Spoken notes / mic / wake word

| | |
|---|---|
| **Earlier** | Microphone only for P1 PTT voice commands. |
| **v1.2** | Spoken idea notes in Android MVP; full assistant P1; wake word P2 opt-in on-device. |
| **v2.0 recommendation** | **PTT spoken idea notes = P0 (FR-040).** Conversational assistant = **P1**. Wake word = **P2, off, on-device only, visible listening glyph, one-tap off.** No always-listening in marketing. |
| **Rationale** | Binding the idea to the moment is the product’s second half. PTT is the only mic mode that matches “no always-on monitor.” Wake word is a privacy-perception landmine (Recall, Rewind, Observer AI). |
| **Risks** | Mic permission reduces Play conversion; room audio may capture others. Mitigation: separate contextual consent; OS mic indicator never hidden; onboarding warning; delete voice independently of the screenshot. |

### D4 — Optional redaction

| | |
|---|---|
| **Earlier** | Never automatically crop/alter the stored original. |
| **v1.2** | When enabled for an app/category, mask sensitive regions; bounds preserved; label `redacted`. |
| **v2.0 recommendation** | **Ship as opt-in, off by default, including for opted-in accounting apps.** Manual save remains the business-app default (D6). Do not retain the unredacted frame when redaction is on. Detection quality is a spike (S15), not a guarantee. |
| **Rationale** | Preserves the unaltered-original rule unless the user asks. Masking PAN/GSTIN/account numbers is the only responsible way to support Tally/Excel. |
| **Risks** | False negatives (user thinks a number is gone). Mitigation: “best-effort” label; preview before save when redaction is on; device-only default for redacted items. |

### D5 — MVP library scope

| | |
|---|---|
| **Earlier** | Android MVP library: search, topic chips, detail. |
| **v1.2** | Add Today view, user tags, provenance narrative, spoken idea notes; collections P1. |
| **v2.0 recommendation** | **Confirm P0: Today, tags, provenance, PTT voice notes. Collections P1.** **Re-estimate Android MVP at 40–55 engineering days** (one experienced Android engineer + QA/design support), replacing v1.2’s 30–45 days. Early screen/OCR-only alpha can still follow S1–S4 (~10–15 days) without Hub polish. |
| **Rationale** | Without Today + provenance + idea notes, the product is a screenshot folder. Collections can wait. The extra days are Hub UI, PTT, DRM labeling, and policy work (Play video, overlay restricted-settings copy). |
| **Risks** | Scope creep past 55 days. Mitigation: collections, assistant, imagination, sync stay out of Phase 1. |

### D6 — Accounting software per-app opt-in

| | |
|---|---|
| **Earlier** | Password managers and banking excluded. |
| **v1.2** | Tally/Excel/ERP are an explicit use case after per-app opt-in; banking/payment/password stay excluded. |
| **v2.0 recommendation** | **Adopt.** Default exclusion list includes banking, UPI/wallets, payment, password managers, and (where identifiable) authenticator/OTP apps. Accounting/ERP/spreadsheet packages are **off until the user enables each package**. Manual save default. Device-only default. Workplace-policy reminder in onboarding. |
| **Rationale** | Owner use case is real; financial data is DPDP-sensitive; OBZRAi must never look like employee monitoring. |
| **Risks** | Package-name lists go stale; Excel used for personal finance. Mitigation: user-editable list; keyword pause; redaction opt-in (D4). |

### D7 — Pull minimal E2E sync earlier, alongside Phase 2 (pending Basant)

**Question for the owner.** D1 keeps **full** E2E sync (including full-resolution frames and voice audio) optional, off by default, in **Phase 4**. When the Chromium extension ships in Phase 2, a save on the phone is still a separate library unless the user carries an encrypted export. D7 asks: pull a **minimal** E2E continuity path forward so “what I observed in the browser shows up on mobile, and vice versa” (JTBD 11 / FR-054) is true at extension launch.

v2.1 **does not change D1.** Full-res media, conflict UX, recovery keys, and a hosted ciphertext relay remain Phase 4. D7 is only about a thinner payload and an earlier date.

| Option | Scope | Effort (labelled) | Cost | Privacy / DPDP | Risks |
|---|---|---|---|---|---|
| **A — Keep Phase 4** | No new sync in Phase 2. Continuity = encrypted export/import (FR-097) in Phase 1; side-panel Hub is local to the browser (option **c** in §21.7). Full E2E (FR-047, FR-064) in Phase 4, off by default. | **S** extra in Phase 2 (zero sync work). Full sync remains **L** in Phase 4. | ₹0 / $0 until Phase 4 relay. | Matches the local-first promise. No account, no fiduciary duties until Phase 4. | Phone and browser libraries diverge. Users who live on both devices will treat the product as two apps. |
| **B — Minimal sync in Phase 2** | Opt-in. E2E. Payload: item metadata, tags, collections, provenance, OCR/page text, transcripts, voice-idea text, **thumbnails** (bounded, e.g. ≤ 200 KB JPEG). **No** full-res frames, **no** voice audio blobs, **no** imagination originals. Transport, in order of preference: (1) **user-owned storage** — Google Drive `appDataFolder` ([Drive API, last updated 3 Sep 2026](https://developers.google.com/workspace/drive/api/guides/appdata)), WebDAV, iCloud (Apple later), (2) Syncthing-style P2P, (3) a small **ciphertext relay** as a later Pro SKU. | **L** (~3+ eng-weeks: protocol + Android client + extension client + one storage adapter). A second adapter is **M**. | User-owned storage: user’s quota (Drive appData counts against the user’s Google storage — community confirmation 19 Apr 2026, [r/developersIndia](https://www.reddit.com/r/developersIndia/comments/1spzhw5/google_has_free_peruser_storage_built_in_and/); official docs do not publish a separate free bucket). Ciphertext relay: hosting + support; price inside Pro ₹149 / $4.99 (§8). | Ciphertext only on any third-party store. Drive `drive.appdata` is a non-sensitive scope; folder is hidden, unshareable, deleted on uninstall. If OBZRAi operates a relay, DPDP fiduciary duties begin (account, notices, deletion). Device-only collections stay out. | Protocol bugs; quota exhaustion if thumbs grow; users confuse “synced library” with “full-res backup”; Google/Apple ToS on appData; key-recovery still hard. |
| **C — Device pairing, no server** | Same-LAN or QR/WebRTC pairing. Each device serves its local store (extension native-messaging host or desktop localhost; Android nearby). No cloud copy. | **M–L** (pairing UX + NAT traversal + auth). Native messaging is a documented Chrome API ([native messaging, last updated 16 Sep 2026](https://developer.chrome.com/docs/extensions/develop/concepts/native-messaging)). | ₹0 hosting. Support cost for “devices cannot see each other.” | Strongest privacy: bytes stay on the LAN or a user-initiated P2P channel. No fiduciary. | Devices must be co-located or reachable; flaky NAT; CWS justification for `nativeMessaging`; does not help a phone in India and a laptop on another network. |

**Precedents (live, 17 Oct 2026):**

| Precedent | What it proves | Source | Date |
|---|---|---|---|
| **Obsidian Sync** | Local-first app; optional paid E2E AES-256 sync. Standard **$4/user/mo billed annually** (1 vault, 1 GB, 5 MB, 1 month history); Plus **$8** (10 vaults, 10 GB, 200 MB, 12 months). Selective sync of images/audio/video. Lost encryption password is unrecoverable. | [obsidian.md/sync](https://obsidian.md/sync); [obsidian.md/pricing](https://obsidian.md/pricing) (aggregators verified 16 Oct 2026, [SourceTier](https://sourcetier.com/pricing/obsidian)). @kepano: Dropbox/Drive/GitHub are not E2E ([X, 20 Aug 2026](https://x.com/kepano/status/2090281274569380312)); Sync encrypts before leave-device ([X, 27 Apr 2026](https://x.com/kepano/status/2048818352953708994)). Cure53 Oct 2024 and Trail of Bits Dec 2025 audits: cited by [WorkifyLab, 31 Aug 2026](https://workifylab.com/obsidian-sync-options-2026/) — **Unverified** as primary PDFs not opened in this pass. | Live / 16 Oct 2026; X 20 Aug 2026 |
| **Standard Notes** | Free plan includes E2E and **unlimited device sync**; Productivity **$90/yr**; Professional **$120/yr** + 100 GB. | [standardnotes.com/plans](https://standardnotes.com/plans) (page seen 17 Oct 2026; aggregators checked 20 Sep 2026). | Live Oct 2026 |
| **Bitwarden** | Zero-knowledge; free sync unlimited devices; Premium **$1.65/mo billed annually ($19.80/yr)**; Teams **$4/user/mo**. Send: default **7-day** lifespan, deletion max **31 days**, sender can deactivate/delete any time. | [bitwarden.com/pricing](https://bitwarden.com/pricing) (seen 17 Oct 2026); [Send lifespan](https://bitwarden.com/help/send-lifespan/) (help PDF dated 16 Sep 2026). Premium price restated in Bitwarden’s enhanced-premium post ([21 Jan 2026](https://bitwarden.com/blog/bitwarden-launches-enhanced-premium-plan/)). | 17 Oct 2026 / 16 Sep 2026 |
| **Syncthing** | P2P TLS, no cloud copy. Untrusted-device mode encrypts file data with **XChaCha20-Poly1305** and names with **AES-SIV**; docs still mark the mode **beta / testing only**. | [docs.syncthing.net/users/untrusted.html](https://docs.syncthing.net/users/untrusted.html); [spec](https://docs.syncthing.net/specs/untrusted.html) (docs dated 7 Oct 2026). | 7 Oct 2026 |
| **Excalidraw share links** | AES-GCM; encryption key in the **URL fragment** (`#key=…` / `#room=id,key`); server stores ciphertext. Fragment is client-side (RFC 3986); HTTP does not send `#` to the server. | [Excalidraw blog, 21 Mar 2020](https://plus.excalidraw.com/blog/end-to-end-encryption); collaboration docs (seen 2026, [Mintlify mirror](https://www.mintlify.com/excalidraw/excalidraw/concepts/collaboration)). RFC 3986 generic URI syntax: [rfc-editor.org/rfc/rfc3986](https://www.rfc-editor.org/rfc/rfc3986). | 21 Mar 2020 / RFC 2005 / docs 2026 |
| **Proton Drive** | E2E share links with optional password, expiry, and revoke (“stop sharing”). Search-under-E2E is an ongoing hard problem. | [proton.me/support/password-protect-files-proton-drive](https://proton.me/ca/support/password-protect-files-proton-drive); mobile share guide updated **16 Oct 2026** ([proton.me/support/proton-drive-mobile-share-files-folders](https://proton.me/support/proton-drive-mobile-share-files-folders)). @ProtonDrive on search-under-E2E ([X, 15 Oct 2026](https://x.com/ProtonDrive/status/2110734481804099638)). | 16 Oct 2026 / 15 Oct 2026 |
| **Ente** | E2E photos; **10 GB free forever**; paid from **$2.49/mo** Starter (50 GB). Official help confirms 10 GB free ([ente.io/help](https://ente.io/help/photos/faq/storage-and-plans)). Paid SKUs from aggregators citing ente.com on **15 Oct 2026** ([MacMyths](https://macmyths.com/apps/ente-photos/pricing/), [The Geeks Club, 17 Oct 2026](https://www.thegeeksclub.com/platforms/ente-photos/pricing/)) — **Unverified** at ente.com checkout (SPA not fetched). | Help page live; aggregators 15–17 Oct 2026 | 17 Oct 2026 |
| **Google Drive appDataFolder** | Hidden per-app folder; `drive.appdata` scope; only the creating app can read; cannot share via Drive UI; deleted when the user uninstalls the app. Counts against the user’s quota. | [developers.google.com workspace Drive appdata](https://developers.google.com/workspace/drive/api/guides/appdata), last updated **3 Sep 2026**. | 3 Sep 2026 |

**Recommendation.**

1. **Keep option A as the default** (D1 unchanged): full-resolution E2E sync stays Phase 4, off by default, Pro-priced.
2. **Offer option B as a Phase 2 fast-follow** if Basant wants phone↔browser continuity on the day the extension ships. Payload stays thin (FR-103). **User-owned storage first** (Drive appDataFolder and/or WebDAV); a hosted ciphertext relay waits for Pro / Phase 4. Off by default; per-collection device-only still wins.
3. **Ship option C as a Phase 2 bonus** for co-located pairing (QR + same-LAN), useful for a first-run “link this browser to this phone” without creating an account (FR-104). It does not replace B for travel/remote.

If B is accepted, Phase 2 exit grows by the L estimate above; S1–S4 and the Android overlay freeze are untouched. Record the choice in the Phase 2 kickoff; until then engineering assumes **A**.

---

## 10. End-to-end user flows

Identity strings below say **OBZRAi**. Overlay behavior is unchanged from v1.2.

### Flow A — First-time setup

1. Install (Play, Obtainium, F-Droid, or APK). If sideloaded on Android 15+, show how to enable **Display over other apps** under restricted settings.
2. Onboarding: what observation is; OS indicator will show; local-first; DRM/audio limits in one sentence; not a downloader.
3. Grant overlay (Settings). Grant notifications if required.
4. User taps **Start observing**. OS MediaProjection dialog: user picks **single app or entire screen**. FGS notification appears; on Android 15 QPR1+ the **status-bar chip** appears.
5. Home shows Observe **Active**. Ring appears at the approved default.

### Flow B — Automatic relevant-content save

1. Session active. Sampler sees a frame whose OCR/transcript matches a topic above threshold (with hysteresis).
2. App selects the best **complete** frame; may record permitted playback audio.
3. Overlay: Capturing → Recording (if audio) → Saved (green check). Host video is not paused. No modal.
4. Item appears in Library and Today with provenance.

### Flow C — Save unrelated content manually

User taps ring → **Save**. Item is stored even if topics do not match. Optional PTT idea after or during save.

### Flow D — Skip and undo

**Skip** rejects the current candidate only (not a global blacklist). Undo from the last-saved snack in Home/Library (short TTL).

### Flow E — Search and reopen

Library search over title, OCR, transcript, tags, voice-idea text, sources. Open item → original frame (fitCenter, zoom/pan), provenance narrative, transcript, Open source (only if URL known).

### Flow F — Session stop and interruptions

Stop from Home, notification, **status-bar chip**, or lock (15 QPR1+). `onStop()` releases projection, audio, temp buffers. No covert resume. User must start a new session (new OS consent on Android 14+).

### Flow G — Save from a website or article (browser extension, Phase 2)

User starts Observe or taps Save on the toolbar. Extension captures visible tab (respecting 2/sec), visible text, exact URL (`browser_tab`). Restricted pages show “Cannot observe this page.” DRM video → `capture_blocked` + URL.

### Flow H — Save a moment from a long video (YouTube)

User taps Save (or auto-match). Bounded moment: full frame + permitted audio window. URL/timestamp only if the page exposes them. Item does not claim to contain the whole video.

### Flow I — Save with a spoken idea (P0)

User holds PTT (separate mic opt-in). Speaks the idea. Release stops recording. Capture + `VoiceNote` (audio + transcript) linked. Mic glyph visible; OS mic indicator remains.

### Flow J — Ask the Observer (P1)

Text or PTT: “what did I see yesterday about Tally GST?” Assistant searches the library, cites items, says when nothing matches. Destructive intents confirm.

### Flow K — Review today’s research

Library → **Today**: time-ordered items, source, device, badges for voice / imagination / audio. Date picker for any earlier day. Export the day (P1).

---

## 11. Visual and interaction requirements

### 11.1 Overlay geometry (final approved, not a redesign)

| Element | Specification |
|---|---|
| Main brand mark | Hollow circular blue ring with dark translucent interior; thin, soft highlight; visually identical to approved reference. |
| Default placement | Left edge, just below host-app “Reels” heading, avoiding status bar and top gesture region. |
| Ring size | **28 dp reference**, adjusted across density/font scales while keeping visually small. |
| Edge exposure | Approximately 65–80% visible in collapsed state; never completely inaccessible. |
| Status glyph | ~10–12 dp, over the ring’s **lower/right perimeter** with a small shared overlap; not a second independent control. |
| Joint silhouette | Ring + attached glyph form **one small cluster**. No thick vertical background connectors. |
| Translucency | Dark base with a softened backdrop; ~20–40% visible backing opacity as a **design starting point**. |
| Colors | Muted blue base, pale blue-gray listening, off-white focus/skip/settings, desaturated red recording, soft green saved. No neon. |
| Animation | 180–250 ms easing; waveform ≤ 2 Hz; honor reduced-motion. |
| Touch | Visually tiny; accessible hit area where practical; must not obstruct important host controls. |

**Visual identity freeze:** do not substitute triangular, robot, letterform, doubled concentric-ring, or extruded capsule icons.

### 11.2 State table

| State | Main ring | Lower overlapping mini glyph | Motion/feedback |
|---|---|---|---|
| Idle/Ready | Approved blue ring | None | Static |
| Observing/Listening | Same ring | Tiny waveform | Quiet waveform oscillation |
| Screenshot captured | Same ring | Open focus-corner glyph | 200–400 ms subtle fade |
| Audio recording | Same ring | Small desaturated red dot | Slow pulse |
| Saved | Same ring | **Standalone green check**, no surrounding green circle | Brief scale/fade; back to previous state |
| Skipped | Same ring | Minimal skip/forward glyph | Brief low-key acknowledgment |
| Error/Audio unavailable | Same ring | Muted dash or warning dot | No false green check |
| Paused | Dimmed blue ring | Tiny pause glyph | Static |
| Capture blocked (DRM) | Same ring | Warning dot | No green check for a screenshot |

The waveform glyph means **an active permitted audio/listening component**. It must never imply audio is captured when unavailable. Candidate state and audio state are separate fields.

### 11.3 Tap, drag and menu

- **Tap:** tiny anchored glass menu: **Save**, **Skip**, **Settings** (icon + text).
- **Drag:** move freely; on release snap to nearest left/right edge; persist `(edge, yFraction)`.
- **Tap outside / idle:** close menu without eating host touches.
- **Session ON/OFF:** prominent toggle in Home, Settings and FGS notification. Distinct from Skip.
- **Position recovery:** clamp after rotation/insets.
- **Touch-through:** outside observer/menu bounds, touches go to the host app.

### 11.4 Capture feedback: silent by design

Only the status glyph changes. No “Capturing…” card, no cropping modal, no auto-open of OBZRAi, no forced video pause. **OS capture chip, FGS notification and mic indicator are never hidden or spoofed.**

### 11.5 Main app information architecture

**Primary bottom navigation: Home · Library**; settings in the app bar.

**Home:** ring logo + “OBZRAi”; Observe Mode status (Active / Paused / Permission required); short privacy/session copy; counters (`Saved items`, `Today`, optional `Topics`); 3–5 recent rows; links to Library, Topics, Today’s research.

**Library:** search; filters `All`, `Instagram`, `YouTube`, `Web`, `Articles`, `Other`, `Audio available`; later platform filter; topic chips. Rows: uncropped-ratio thumbnail, title, topic, time, audio/transcript marker. Sort newest / title / relevance. Empty state with no account prompt.

**Item detail:** original screenshot `fitCenter` (letterboxing OK; **stored file unchanged**); zoom/pan; editable title/tags; source with honesty states; tabs **Transcript · Notes/Summary · Details**; provenance narrative; Share, Open source, Export, Delete.

**Topics:** custom topics, aliases, negative keywords, enable/disable; sensitivity Conservative (default) / Balanced / Broad.

**Settings / privacy:** capture and overlay status; audio capability; STT model download; overlay reset; auto-save threshold; retention/storage meter; export; Delete all; excluded apps/sites; sensitive-app opt-in; voice & assistant; AI features; account & sync (when shipped).

### 11.6 Platform surface adaptations

| Platform | Observer surface | Notes |
|---|---|---|
| Android app | Floating overlay ring (§11.1–11.3) | Reference implementation |
| React Native | Same native Android overlay via native module; iOS uses only OS-permitted surfaces | No JS substitute logo |
| Browser extension | Toolbar icon + optional shadow-DOM in-page ring; Library in **side panel** | Ring must not block page controls; per-site disable |
| Desktop | Borderless always-on-top ring + tray/menu-bar | Exclude own window from captures where the OS allows; else hide-and-settle (S3) |
| iOS (P2) | System broadcast picker / share extension; **no** system overlay | Do not fake the Android ring over other apps |

---

## 12. Unified Library / Research Hub

The Library is the user’s **Research Hub**. Primary nav stays Home · Library. Three views: **All · Today/Timeline · Collections**.

**Collections (P1):** user folders; an item can belong to several; smart collections from filters; collection-level sync/export.

**Tags (P0):** Topics (matching) and free-form user tags are distinct. AI-suggested tags need acceptance.

**Today (P0):** everything saved today, newest first, grouped by time block and source. Calendar for earlier days. Badges for voice / imagination / audio / blocked.

**Search (P0 keyword, P1 NL):** titles, OCR, page text, transcripts, voice-idea transcripts, tags, collections, sources. Filters: date, device, platform, content type, source, collection, has-voice-idea, has-audio.

**Provenance (P0):** app, window/page title, exact URL, video timestamp, each only if known. “Open source” only when genuinely known.

**Export (P0 single/all delete+basic; P1 rich):** portable archive (media + `metadata.json`), Markdown/HTML, CSV. Redacted items export redacted. Copyright caution.

**Account:** without an account the Hub is that device’s library. With optional account it is the user’s encrypted base across devices. **Web-grade Hub** (side panel, PWA, boards, browse, read-only E2E share, marketing site) is specified in **§21**. **Cross-device continuity** (export in Phase 1, optional thin E2E in Phase 2 via D7, full E2E in Phase 4) is specified in **§24**. Strength proposals (digest, collection summaries, learning-path, snippets, MCP, templates, etc.) are in **§23**.

---

## 13. Functional requirements

Priorities: **P0** Android MVP, **P1** after stable Android / with later platforms, **P2** research. Acceptance is testable.

| ID | Pri | Requirement | Acceptance |
|---|---|---|---|
| FR-001 | P0 | Explicit observation session | No capture until the user confirms the OS permission and starts the session. No boot auto-start. |
| FR-002 | P0 | Approved floating ring | Overlay matches owner-approved blue ring shape/geometry; no replacement logos. |
| FR-003 | P0 | Draggable + edge snap | Drag to either side; position persists after process/UI recreation where allowed. |
| FR-004 | P0 | Mini status glyph | All state glyphs stay attached to the ring, smaller than the logo. |
| FR-005 | P0 | Three quick actions | Tap opens Save/Skip/Settings with visible compact labels; no full-screen takeover. |
| FR-006 | P0 | Full-frame capture | Stored image preserves frame bounds and original projection orientation. No product-introduced crop. |
| FR-007 | P0 | Overlay exclusion | On supported devices, briefly hide overlay before selecting a clean frame; else declare/test fallback. |
| FR-008 | P0 | On-device OCR | Captured frame text is indexed; empty/unreadable frames do not fabricate text. |
| FR-009 | P0 | Topic classifier | User interests influence auto-save; unrelated content suppressed by default. ≥80% precision target on the 200-item set (§4.3). |
| FR-010 | P0 | Manual override | Save works for unrelated content; Skip rejects the current candidate without global blacklisting. |
| FR-011 | P0 | Playback audio, if permitted | Records only OS-permitted playback; shows Unsupported/Unavailable/Blocked otherwise. Silent PCM ≠ success. |
| FR-012 | P0 | Transcript | Timestamped transcript for available audio; marks partial/incomplete; never invents text for blocked audio. |
| FR-013 | P0 | Local persistence | Accepted items survive app restart; temp rejected items deleted on expiry. |
| FR-014 | P0 | Searchable library | Keyword search matches OCR, transcript, title, topic, tags, voice-idea text; Unicode handled. p95 ≤ 500 ms on 1,000-item fixture. |
| FR-015 | P0 | Source integrity | User-shared exact URL retained; unknown URLs stay empty, never inferred as fact. |
| FR-016 | P0 | Safe stop | FGS stops and releases projection/audio/buffers on user stop, chip tap, lock (15 QPR1+), revoke, or kill. |
| FR-017 | P0 | Privacy default | No data uploaded by default; permissions and capture scope explained in-app. |
| FR-018 | P0 | Low distraction | Automatic save shows only the tiny glyph; no blocking dialog; no interruption to scrolling. |
| FR-019 | P0 | Export/delete | Single item and whole-library deletion works; optional exports include content and metadata. |
| FR-020 | P0 | Session indicators | FGS notification present; OS chip never hidden; app exposes active/paused. |
| FR-021 | P1 | Rolling audio | Bounded user-enabled buffer, current authorized session only. |
| FR-022 | P1 | Voice command (legacy) | Superseded in scope by FR-041; PTT remains the mic path. |
| FR-023 | P1 | Dedup grouping | Visually identical or same-URL saves grouped; no silent data loss. |
| FR-024 | P1 | Semantic search | On-device embeddings improve recall within privacy/performance limits. |
| FR-025 | P2 | iOS companion | Separate spec after feasibility; no Android overlay promised. |
| FR-026 | P0 | Content-type agnostic | Saves full frame + OCR + topics for videos, websites, articles and other screens; `content_type` or `unknown`. |
| FR-027 | P0 | Shared Observer core | Topic matching, scoring, item schema, search and privacy rules live in a platform-independent core with shared fixtures. |
| FR-028 | P1 | Browser extension observation | Active-tab only during a user-started session or explicit action; exact URL with `source_url_origin = browser_tab`; ≤ 2 `captureVisibleTab` calls/sec. |
| FR-029 | P1 | Web/article text | Visible page text and user selection stored; no crawling of other pages/tabs; restricted pages reported. |
| FR-030 | P1 | Long-video moments | Bounded moment; URL/timestamp only when genuinely available; never claim full-video archive. |
| FR-031 | P1 | Desktop observation | OS consent, OS indicators visible, stop on Stop/lock/sleep/revoke. |
| FR-032 | P2 | React Native client | Shared mobile UI parity without regressing Android P0 or the overlay. |
| FR-033 | P1 | Cross-platform consistency | Same input fixture → same topic match and item fields (core parity test). |
| FR-034 | P1 | Site/app exclusions | User exclusions never captured; banking/password pre-excluded where identifiable. |
| FR-035 | P0 | Today view | All items saved today in time order with source and device; any earlier day selectable. |
| FR-036 | P0 | User tags | Add/remove/rename; searchable/filterable; AI-suggested tags need acceptance. |
| FR-037 | P1 | Collections and folders | Create, nest, rename, delete; item in multiple collections; smart collections. |
| FR-038 | P0 | Provenance narrative | Renders time seen, source (each field only if known) and the user’s idea; unknown omitted, never guessed. |
| FR-039 | P1 | Research export | Item/collection/day/library to archive, Markdown/HTML, CSV; redacted stays redacted. |
| FR-040 | P0 | Spoken idea note | PTT records only while held, after separate mic opt-in; audio + timestamped transcript linked; visible mic state. |
| FR-041 | P1 | Conversational assistant | Voice and text intents (§14.8); ambiguous intents ask; destructive intents confirm. |
| FR-042 | P2 | Wake word | Opt-in, on-device, visible listening, easy off; no audio leaves the device before activation. |
| FR-043 | P1 | Find what I saw | NL questions return cited items or an explicit empty result. |
| FR-044 | P1 | Imagination image | On request, via configured opt-in provider; labeled AI-generated; stores prompt/provider/time. |
| FR-045 | P1 | AI agents | Summarise, auto-tag, cluster, Q&A on-device by default; cloud per-feature opt-in; outputs editable and labeled. |
| FR-046 | P1 | Optional account | Fully usable without; sign-in/out, device list, device removal; account deletion deletes server-side data. |
| FR-047 | P1 | Encrypted sync | E2E for items, collections, tags, voice notes, images; per-device and per-collection opt-out. |
| FR-048 | P1 | Conflict handling | No silent data loss; visible choice when needed (§16.5.3). |
| FR-049 | P0 | Offline-first | Capture, library, search, export work offline; with sync on, changes queue. |
| FR-050 | P1 | Sensitive business screens | Accounting apps only after per-app opt-in; manual save default; banking/payment/password excluded by default. |
| FR-051 | P1 | Redaction | When enabled, mask detected sensitive values before save; bounds preserved; label `redacted`; unredacted frame not kept. |
| FR-052 | P0 | Protected/DRM screens | Blank/black/blocked detected and labelled `capture_blocked`; no bypass; idea note and time still saveable; **no green check for a screenshot**. |
| FR-053 | P0 | Capture record completeness | Resource, time seen, source/provenance and (when given) voice idea and imagination as linked records. |
| FR-054 | P1 | Cross-device availability | Item saved on any opted-in client appears in the Hub on the others after sync. Full-res: Phase 4 (D1). Thin payload: Phase 2 only if D7-B. Until then: FR-097 export. See §24. |
| FR-055 | P0 | No FLAG_SECURE bypass | App does not use private APIs, accessibility tricks or overlays to capture secure surfaces. Play policy test. |
| FR-056 | P0 | Single-app projection handling | If the user shares one app, only that app’s frames are treated as the capture region; switching away does not silently grab the new foreground app without OS/user involvement. |
| FR-057 | P0 | Sideload overlay education | First-run detects missing `SYSTEM_ALERT_WINDOW` / restricted setting and shows OEM-accurate steps. |
| FR-058 | P1 | Local-first Hub web surface | IndexedDB/OPFS library works offline; side panel in Phase 2, standalone PWA in Phase 4; no account required. See §21.1. |
| FR-059 | P1 | Today / timeline on web | FR-035 content on the Hub; keyboard accessible. |
| FR-060 | P1 | Boards (web collections) | Same entity as FR-037; no shared boards in V1. |
| FR-061 | P1 | Hub search on web | FR-014 parity; p95 ≤ 500 ms on 1,000-item fixture. |
| FR-062 | P1 | Export MD / PDF / archive; Notion & Obsidian paths | Obsidian vault folder valid; Notion only with user token; PDF of a day works. |
| FR-063 | P0 | Team-free V1 | No org, seats, live collab, or ACL UI. |
| FR-064 | P1 | E2E Hub client | Ciphertext only on relay; matches FR-047. Phase 4. |
| FR-065 | P1 | Side panel Hub | Chrome 114+; `open()` only on user gesture with tabId or windowId. |
| FR-066 | P1 | Extension keyboard shortcuts | ≤ 4 suggested keys; each includes Ctrl or Alt. |
| FR-067 | P1 | Highlight / clip | Selection + exact URL on explicit action; no other-tab read. |
| FR-068 | P1 | YouTube timestamp capture | Store `t=` / `?t=` only if known; never download the video file. |
| FR-069 | P1 | Extension voice note | FR-040 contract in the side panel. |
| FR-070 | P1 | CWS least privilege + justifications | Privacy tab filled; no unused permissions; prefer activeTab. |
| FR-071 | P1 | CWS single-purpose listing | One-sentence purpose; no extra unrelated surfaces. |
| FR-072 | P1 | Onboarding | ≤ 3 screens; skippable; no account wall. |
| FR-073 | P1 | Empty states | Every empty view has one primary action. |
| FR-074 | P1 | WCAG 2.2 AA | Overlay has a non-drag alternative; Hub meets AA additions 2.4.11, 2.5.7, 2.5.8, 3.2.6, 3.3.7, 3.3.8. |
| FR-075 | P1 | Dark mode | Follow OS + user override; approved ring identity preserved. |
| FR-076 | P1 | i18n English + Hindi | UI in both; Hinglish accepted in voice notes and search. |
| FR-077 | P1 | Performance budgets | Hub CWV good targets (LCP ≤ 2.5 s, INP ≤ 200 ms, CLS ≤ 0.1 at p75); captureVisibleTab ≤ 2/sec; side-panel first-paint target < 300 ms. |
| FR-078 | P1 | Trust center / privacy dashboard | In-app + public page; session status; delete-all works. |
| FR-079 | P1 | **PROPOSAL** Daily AI research digest | Cited recap of today’s items; on-device default; no fake content on empty days. §23. |
| FR-080 | P1 | **PROPOSAL** Semantic search across visual + voice | Extends FR-024; embeddings on-device unless opted out. |
| FR-081 | P1 | **PROPOSAL** Auto-citations and source-link recovery | Footnotes only for known URLs; OCR URLs labelled and confirmable; never fabricate. |
| FR-082 | P1 | **PROPOSAL** Research sessions / projects | Named project label on items; not a second database; not teams. |
| FR-083 | P2 | **PROPOSAL** Spaced-repetition review | Opt-in per item; no streak dark patterns. |
| FR-084 | P1 | **PROPOSAL** Readwise export | User token; highlights with source URL when known. |
| FR-085 | P1 | **PROPOSAL** Local LLM on-device Q&A | Answers cite item ids or empty; model download explicit. |
| FR-086 | P1 | **PROPOSAL** Local MCP server (stdio) | Read-only default; no network listener in V1. |
| FR-087 | P2 | **PROPOSAL** Shareable read-only boards | Explicit publish; E2E key in URL fragment; revoke; default 7-day expiry; still team-free. FR-105. |
| FR-088 | P1 | **PROPOSAL** Templates by persona | Developer / student / accountant / designer; skippable. |
| FR-089 | P1 | **PROPOSAL** Duplicate detection | Extends FR-023; visible group, no silent delete. |
| FR-090 | P2 | **PROPOSAL** OCR of code into snippets | Copyable fenced Markdown; not an in-app executor. |
| FR-091 | P1 | **PROPOSAL** YouTube timestamp deep links | Open source jumps to stored seconds; pairs with FR-068. |
| FR-092 | P1 | **PROPOSAL** Hindi / Hinglish voice notes | Transcript language tagged; S16 gate. |
| FR-093 | P2 | **PROPOSAL** Integrations catalog | Optional modules; CWS single purpose preserved; FOSS flavor omits proprietary SDKs. |
| FR-094 | P1 | Marketing site | Public origin with home, privacy/trust, download CTAs per platform, docs, changelog, open-source, waitlist. See §21.6. |
| FR-095 | P1 | Web Hub browse | All / list / grid / filters on the Hub; same entities as Android Library. Phase 2 side panel. |
| FR-096 | P1 | Web / marketing onboarding | Marketing site → platform CTA → first save; ≤ 3 in-product screens; skippable; no account wall. Extends FR-072. |
| FR-097 | P0 | Encrypted export / import (continuity) | Phase 1 multi-device path: encrypted archive of items + metadata; passphrase; round-trip Android ↔ later clients. |
| FR-098 | P1 | **PROPOSAL** AI summaries per collection | Cited recap of one board; on-device default; empty collection produces no fake content. Phase 2–4. |
| FR-099 | P1 | **PROPOSAL** Google Drive export | User-authorized folder; export Markdown/archive; Drive is an export target, not a plaintext Hub. Phase 2–4. |
| FR-100 | P1 | **PROPOSAL** Ask my library | Productizes FR-043/085: NL questions over the local Hub with citations. Phase 2 after S17. |
| FR-101 | P1 | **PROPOSAL** YouTube learning-path mode | Playlist/course progress, auto-chapter notes from saved timestamps. Phase 2 with FR-068. |
| FR-102 | P1 | **PROPOSAL** Accounting snippets library | Tally / Excel formulas, GST/TDS snippets; redaction on by default for this library. Phase 2 after D6. |
| FR-103 | P1 | Minimal E2E sync payload (D7-B) | If D7-B is accepted: items + thumbs + transcripts only; opt-in; ciphertext; no full-res. Phase 2 fast-follow. |
| FR-104 | P2 | LAN / QR device pairing (D7-C) | Co-located pairing without a server; native messaging or localhost bridge. Phase 2 bonus. |
| FR-105 | P2 | Share-link revoke and expiry | Read-only collection links: fragment key, default 7-day expiry, one-tap revoke. Pairs with FR-087. |
| FR-106 | P1 | Continuity status UI | Hub shows which device last wrote, sync/export state, and “this item is local-only.” Phase 1 export; Phase 2+ sync. |

---

## 14. Observation and extraction pipeline

### 14.1 Modules

```text
[User starts Observe + platform grants capture]
 (Android MediaProjection | browser active-tab | desktop OS capture)
                       |
            [Platform session manager]
                       |
         +-------------+-------------+
         |                           |
[Frame sampler]             [Playback audio adapter]
 (+ page text on web)          (only if permitted)
[Text OCR / frame quality]            |
         |                    [VAD / transcription]
         +-------------+-------------+
                       |
   - - - - - - - - - - + - - - - shared Observer core - - - -
              [Topic match engine]
                       |
        [Candidate: Save / Ignore / Skip]
                       |
               [Full-frame selection]
                       |
   - - - - - - - - - - + - - - - platform adapters - - - - - -
     [Local assets + platform DB + search index]
                       |
               [Library / Detail / Export]
```

### 14.2 Frame sampling

1. Low-frequency preview (starting experiment: one frame every 1–3 seconds; **browser ≤ 2/sec hard cap**). Adapt on motion/battery.
2. Cheap perceptual change score before expensive OCR.
3. OCR the full projection frame; ROIs **only for analysis speed**, never to crop the saved screenshot.
4. Estimate text clarity; keep a small capped rolling set from the authorized session.
5. On strong match, select the most informative **complete** source frame; skip transition/blank frames.
6. Release unselected frames quickly.

### 14.3 Audio processing

- Preferred: `AudioPlaybackCaptureConfiguration` + `AudioRecord` inside an authorized session, where the source allows `ALLOW_CAPTURE_BY_ALL`.
- Verify real PCM amplitude; silent stream ≠ success.
- Encrypted temp buffers → VAD → `whisper.cpp` on-device.
- User-configurable max saved duration; report truncation.
- Timestamps relative to the observation candidate.
- Microphone fallback is **not** automatic; separate opt-in; may pick up room noise.
- Spoken idea notes are a **separate microphone path** (PTT). Never used as a silent substitute for blocked source audio without telling the user.

### 14.4 Topic ranking (deterministic MVP)

Weights are tunable engineering parameters, not a trained-model claim:

```text
score = 0.50 * OCR_topic_match
      + 0.35 * transcript_topic_match
      + 0.10 * alias_or_entity_bonus
      + 0.05 * contextual_repeat_evidence
      - conflict_or_negative_keyword_penalty

if user_manual_save: Save immediately
else if score >= configured_threshold && repeated_evidence: Save candidate
else: Ignore without retaining durable assets
```

Renormalize when audio is unavailable. Hysteresis/debounce to avoid duplicate saves. Provide `Delete` / `Not relevant` feedback.

### 14.5 Item boundary detection

Visual change, OCR clusters, silence/VAD, visible metadata, timeouts. Do not rely on private platform APIs. MVP optimizes for a **high-quality complete screenshot + useful transcript segment**.

### 14.6 URL and platform capture (preference order)

1. Exact URL shared via Android Sharesheet (`ACTION_SEND`) or equivalent.
2. Exact URL of the active tab (`browser_tab`), including a video timestamp only when the page exposes it.
3. Exact URL from a supported deep-link/intent with explicit user action.
4. On-screen URL via OCR, sanitized, labelled `extracted_from_screen`, never treated as authoritative if ambiguous.
5. No URL: `source_url = null`; Open original disabled.

Do not scrape the clipboard invisibly, fabricate deep links, use private APIs, or use AccessibilityService by default. The desktop app does not read other browsers’ history files.

### 14.7 Content-type handling

`content_type` is best-effort (`short_video`, `long_video`, `web_page`, `article`, `document`, `business_app`, `ai_chat`, `design_tool`, `movie`, `other`, `unknown`) and must never block saving.

- **Short-form:** §14.2–14.5.
- **Long-form:** moments only; group by URL/signature under `source_group_id`; on-screen captions are OCR, not an authoritative transcript.
- **Web:** usually no audio; lower sampling on static pages; re-evaluate on scroll/navigation; visible viewport only.
- **Articles:** visible/selected text by default; full readable-text save is explicit.
- **Other:** full frame + OCR; exclude sensitive contexts.
- **Accounting:** opted-in apps only; **manual save default**; optional redaction of amounts, account numbers, tax IDs, names; never read file contents.
- **AI chats:** visible prompt/answer; long answers as separate moments grouped by source.
- **Design/3D:** low sampling, mostly manual; voice + imagination are the enrichments.
- **Movies:** detect blank/uniform frames; `capture_blocked`; idea + time still saveable.

### 14.8 Conversational assistant

The assistant acts only on the library and the current authorized session.

| Intent | Example | Behavior |
|---|---|---|
| Save this | “Save this.” | Save current capture with time and source. |
| Save with idea | “Save this — idea: …” | Save capture + voice note. |
| Idea only | “Note an idea: …” | Voice/text idea with time and current source context, even without a screenshot. |
| Skip | “Skip this.” | Flow D. |
| Observe / stop | “Start observing” / “Stop” | Start only via OS consent; stop immediate. |
| Imagine this | “Imagine this as a mobile app screen.” | Opt-in image generation. |
| Find | “Find what I saw yesterday about Tally GST.” | Search; cite items. |
| Organize | “Add this to my Invoice collection” | Apply collection/tag; confirm. |
| Summarise / ask | “Summarise today’s research.” | Agents layer; cite items. |
| Delete | “Delete the last save.” | Explicit confirmation. |

Rules: PTT is the only P0/P1 voice mode; on-device transcription default; never invent library content; no send/share/post outside the app; voice notes deletable separately.

### 14.9 Imagination

Triggered only by explicit request. Input: idea text/transcript and, if allowed, the capture as reference. Output: `GeneratedImage`, always labelled **AI-generated**, with prompt, provider, model, time. Off by default until D2 provider/cost is set. Failed/refused generations shown honestly.

---

## 15. Technical architecture

### 15.1 Android stack (current scaffold)

| Layer | Recommendation | Reason |
|---|---|---|
| Mobile app | **Kotlin + Jetpack Compose + Material 3** | Native capture, overlay, animations |
| State | ViewModel + StateFlow + Coroutines | Cancellation and session lifecycle |
| DI | Hilt *or* Koin (one, not both) | Testability |
| Background | FGS type `mediaProjection` | Required |
| Overlay | `WindowManager` `TYPE_APPLICATION_OVERLAY`, isolated hit region | Drag/snap ring |
| OCR | ML Kit Text Recognition; optional Tesseract | On-device; disclose ML Kit if FOSS purity is required |
| ASR | `whisper.cpp`, optional downloaded tiny/base quantized | Offline STT |
| VAD | Lightweight VAD | Segment processing |
| Persistence | Room + SQLite + FTS4; app-private files | Local search |
| Images | ImageReader; original PNG/WebP + preview | No crop of original |
| Settings | DataStore | Overlay position, topics |
| Jobs | WorkManager for **postprocessing only** | Never to bypass projection |
| Secrets | Android Keystore; SAF exports | No blanket storage permission |
| Testing | JUnit, Robolectric, Compose UI, instrumentation, physical devices | Cross-version |

### 15.2 Android modules

```text
app/
  core/ui/  core/model/  core/storage/  core/privacy/
  feature/home/  feature/library/  feature/topics/  feature/settings/
  feature/overlay/  feature/capture/  feature/audio/  feature/ocr/
  feature/matcher/  feature/share/  feature/diagnostics/
benchmark/  licenses/  .github/workflows/
```

### 15.3 State machines (Observer core)

**Session:** `STOPPED → NEEDS_PERMISSION → STARTING → ACTIVE → PAUSED/STOPPING → STOPPED`.

**Candidate:** `NONE → DETECTED → EVALUATING → MATCHED → CAPTURING → PROCESSING → SAVED`, with `SKIPPED`, `EXPIRED`, `FAILED`, `CAPTURE_BLOCKED`.

**Audio status:** `NOT_REQUESTED`, `AVAILABLE`, `CAPTURING`, `BLOCKED_BY_SOURCE`, `NO_SIGNAL`, `ERROR`.

**Transcript status:** `PENDING`, `COMPLETE`, `PARTIAL`, `UNAVAILABLE`, `FAILED`.

Do not equate `Candidate SAVED` with `Audio COMPLETE`.

### 15.4 Shared Observer core

Topics/aliases, scoring, hysteresis, state machines, item schema, export format, search ranking, privacy rules, Hub model, assistant intents, sync merge rules, shared fixtures. **Sharing technology is open:** Kotlin Multiplatform vs Rust/C++ vs specified contract + per-platform implementations. Extract the core **before** the first non-Android client (S9).

### 15.5 Other clients

**React Native:** RN screens for Home/Library/Settings; native capture/overlay modules; native DB.

**Browser extension:** MV3 Chromium first; visible-tab + content script; offscreen document for tab audio; toolbar + side panel + optional in-page ring; WASM OCR/ASR subject to S10; IndexedDB; encrypted sync later.

**Desktop:** Tauri *or* Electron *or* native — open decision. ScreenCaptureKit / WGC / xdg-portal; always-on-top ring + tray; SQLite FTS.

### 15.6 Repository layout (target)

```text
core/  android/  react-native/  browser-extension/  desktop/
benchmark/  sync/  agents/  server/  docs/
```

Monorepo vs separate repos is open. Current scaffold stays Android-only until S1–S4 pass.

### 15.7 AI agents layer

| Agent | Job | Default |
|---|---|---|
| Summariser | Item/collection/day summaries | On-device; cloud opt-in |
| Tagger | Suggest tags/titles | On-device; need acceptance |
| Clusterer | Group related items | On-device embeddings |
| Librarian (Q&A) | Find what I saw | Retrieval on-device; generation on-device or cloud; always cites |
| Imagination | Idea images | Provider API, opt-in, off |
| Intent parser | Voice/text → intents | On-device rules first |

Provider-agnostic adapters. Sensitive/redacted items never sent without per-request allow. Outputs labeled, editable, deletable. Usage meter before any paid provider.

---

## 16. Data model and local storage

### 16.1 Core entities (minimum fields)

| Entity | Fields |
|---|---|
| `ObservationSession` | id, start/end, platform, client_version, consent_version, capture_region, stop_reason, device_api, audio_capability |
| `InterestTopic` | id, name, aliases, enabled, negative_keywords, sensitivity |
| `CaptureCandidate` | id, session_id, first_seen_at, score, extracted_text_hash, status, expiry |
| `Capture` / `SavedItem` | id, account_id_nullable, device_id, created_at, seen_at_start, seen_at_end_nullable, title, platform, content_type, source_id, source_group_id_nullable, topic_ids, tag_ids, collection_ids, capture_state (`complete`, `partial`, `capture_blocked`, `redacted`, `idea_only`), audio_state, transcript_state, duration_nullable, user_notes, sync_version, updated_at, deleted_at_nullable |
| `Source` | id, source_app_nullable, source_window_or_page_title_nullable, source_url_nullable, source_url_origin, media_timestamp_ms_nullable, site_or_publisher_nullable, verified |
| `VoiceNote` | id, capture_id, kind (`idea`, `instruction`, `command`), audio_asset_id, transcript_text, transcript_segments, language, recorded_at, duration_ms, parsed_intent_nullable |
| `GeneratedImage` | id, capture_id, voice_note_id_nullable, prompt, provider, model, generated_at, asset_id, used_capture_as_reference, ai_generated_label (always true) |
| `Collection` | id, name, parent_id_nullable, smart_filter_nullable, sort_order, sync_enabled, created_at, updated_at |
| `Tag` | id, name, origin (`user`, `ai_suggested_accepted`), created_at |
| `Account` | id, auth_provider, created_at, encryption_key_metadata (no plaintext key), deleted_at_nullable |
| `Device` | id, account_id_nullable, platform, name, last_sync_at, revoked_at_nullable |
| `SyncChange` | id, entity_type, entity_id, device_id, sync_version, operation, encrypted_payload_ref, created_at |
| `AgentRun` | id, agent, target_ids, provider, on_device, started_at, status, output_ref |
| `MediaAsset` | id, saved_item_id, type, local_uri, width, height, orientation, sha256, bytes, captured_at |
| `TranscriptSegment` | id, saved_item_id, start_ms, end_ms, text, language, confidence_nullable |
| `DetectedEntity` | id, saved_item_id, category, value, extraction_method, confidence_nullable |
| `ConsentEvent` | id, platform, capability, disclosed_version, accepted_at, revoked_at_nullable |
| `PageText` | id, saved_item_id, text_origin (`dom_visible`, `user_selection`, `ocr`), text, captured_at |
| `Exclusion` | id, platform, kind (`app`, `window`, `domain`), value, created_at |

`source_url_origin`: `user_share`, `browser_tab`, `deep_link`, `extracted_from_screen`, `none`. `MediaAsset.type`: `screenshot`, `clip`, `playback_audio`, `voice_note_audio`, `generated_image`, `preview`.

### 16.2 Storage layout

```text
files/observer/
  items/<uuid>/
    screenshot_original.png   # complete projected frame, never cropped
    screenshot_preview.webp
    playback_audio.m4a
    transcript.json
    page_text.json
    source.json
    voice/<voice_note_uuid>.m4a
    voice/<voice_note_uuid>.json
    generated/<image_uuid>.png
    metadata.json
  temp/<session_uuid>/
  models/
```

App-private storage, cap on temp bytes, transactional DB+files, SAF export, user-visible storage meter.

### 16.3 Deduplication

Exact URL if available; else perceptual hash + OCR signature + short-time proximity. Do not merge on similar faces/layouts. Multiple moments from one video stay separate items under one `source_group_id`. Cross-device duplicates are *suggested*, never merged silently.

### 16.4 Provenance narrative

Template-based, facts only. Example:

> “You were checking this in **Chrome — YouTube** at **3:42 PM on 17 Oct** (on your laptop). **This idea came to you:** ‘use this layout for the invoice dashboard.’ **This is where it came from:** youtube.com/watch?v=… at 12:31 (exact URL from browser tab).”

Unknown fields omitted. AI phrasing optional and must not add facts.

### 16.5 Cross-device sync (Phase 4, D1)

- Optional account; product fully usable without.
- Local-first; change-log replication; large media lazy.
- **E2E encrypted**; server stores ciphertext + minimal routing metadata.
- Per-device and per-collection opt-out; sensitive categories device-only.
- Conflicts: captures append-only; field-level merge; last-writer-wins only for simple scalars with history; delete vs edit → delete wins, short trash, user told; conflicting notes keep both versions.
- Offline queue; sync status visible.
- Key recovery documented honestly (lose all devices + recovery key → data gone).
- Transport options (managed relay, self-host, user-owned storage) remain an engineering decision (open question 16).

---

## 17. Privacy, security, compliance and legal

This section is product policy, not legal advice. Counsel should review before Play listing, account launch, or India public launch with accounts.

### 17.1 Product privacy principles

1. Capture begins only after disclosure **and** OS grant. Stop any time. No boot auto-start, no invisible restart.
2. Preview frames are transient. Keep accepted captures; delete irrelevant temps.
3. Respect `FLAG_SECURE`, `HIDE_OVERLAY_WINDOWS`, DRM, OEM restrictions. Never bypass.
4. No stealth audio. Mic requires separate consent. OS indicators stay visible.
5. Never invent URLs or transcripts.
6. Exclude password, OTP, banking, private chats by default.
7. No external transfer by default. Sync, export and cloud AI are explicit opt-in with destination named.
8. Delete item / Delete all wipe DB, media, transcripts, voice (subject to filesystem reality). Confirm.
9. Apache-2.0 proposed for the app; audit every model/library.
10. Play: explain each permission; no fake accessibility claims.
11. Extension: active tab only; no other-tab/history/cookie/password reads.
12. Desktop: never hide OS recording indicators.
13. Same rules on every client.
14. Not an employee-monitoring tool. Onboarding says so.

### 17.2 Sensitive financial and business screens

Default exclusion: banking, payment, UPI/wallet, password-manager, authenticator apps/sites where identifiable. Accounting/business software observed only after **per-app opt-in**, **manual save default**. Redaction opt-in (D4). Device-only option. Workplace/client-data reminder.

### 17.3 Voice consent

PTT only while held (wake word later, opt-in). OS mic indicator never hidden. Voice notes are the user’s speech; onboarding warns that room audio may include others. Playback audio and voice notes stored separately. No voice off-device unless a cloud speech feature is enabled.

### 17.4 Third-party screens, movies, DRM

Personal note-taking and recall, not redistribution. DRM is a hard platform limit. No bulk movie copy features. Export carries a rights caution. Other people’s chats/emails are the user’s responsibility; pause and exclusions are one tap away.

### 17.5 AI providers

On-device default. Each cloud feature names the provider and lists what is sent. Redacted/device-only never sent without per-request permission. Outputs labeled. Review training/retention terms before integration.

### 17.6 India DPDP Act 2023 and DPDP Rules 2025

**Act:** Digital Personal Data Protection Act, 2023 (22 of 2023).

**Rules:** Digital Personal Data Protection Rules, 2025, Gazette **G.S.R. 846(E), 13 November 2025**; corrigendum G.S.R. 892(E), 10 December 2025 ([CADP official text](https://cadp.in/resources/official-texts/dpdp-rules-2025/), page 15 Oct 2026; [MeitY documents listing, published 14.11.2025](https://www.meity.gov.in/documents/act-and-policies/digital-personal-data-protection-rules-2025-gDOxUjMtQWa)). Some secondary pages say “14 November 2025”; the Gazette date on the notification itself is **13 November 2025**.

**Commencement (from Gazette G.S.R. 843(E), 13 Nov 2025, via [Medianama PDF](https://www.medianama.com/wp-content/uploads/2025/11/Date-of-applicability-of-Rules.pdf), 14 Nov 2025):**

| When | What |
|---|---|
| 13 Nov 2025 (publication) | Definitions; Data Protection Board establishment; selected Act sections |
| One year (~**13 Nov 2026**) | Consent Manager registration (Rule 4) and specified Act clauses |
| Eighteen months (~**13 May 2027**) | Remaining processing, notice, rights, and most fiduciary duties (sections 3–17 etc.) |

**Product impact.** Local-only Android MVP with no account still processes personal data (screenshots can contain personal data of the user and of third parties visible on screen). Treat the user as Data Principal and OBZRAi (once an entity offers the app in India, especially with accounts) as a **Data Fiduciary** for any personal data it determines the purpose/means of. Required, as the 18-month duties approach: notice (purpose, data items, how to withdraw), consent for processing beyond obvious local use, rights to access/correct/erase (already aligned with Delete all), reasonable security safeguards, breach notice to the Board and to users. Children’s data: verifiable guardian consent — **do not target children**; Play age rating and a 18+ use statement for observation features.

**This PRD is not a DPDP compliance programme.** A lawyer should map notice text, consent UX, and whether a purely local on-device app without an account is still a fiduciary in MeitY/Board guidance as it appears in 2026–27.

### 17.7 GDPR

If the product is offered to people in the EEA/UK (Play global listing, website, account), GDPR/UK GDPR may apply (Art. 3 targeting). Lawful basis: **consent** for screen/mic capture; **contract** for an optional account. DPIA recommended before always-on-adjacent features (there are none in MVP). DPA with any sync/AI processor. No special-category data by design; screenshots may incidentally include it — minimization and exclusions are the control. **Unverified:** whether a specific DPO is required at the owner’s scale.

### 17.8 Google Play User Data and Device policies

- Declare Data safety (collection, sharing, security practices) honestly: local storage; no sharing by default; optional sync/AI if shipped.
- FGS `mediaProjection` declaration + **demo video** (§6.1).
- No FLAG_SECURE bypass ([Device and Network Abuse](https://support.google.com/googleplay/android-developer/answer/16559646)).
- No AccessibilityService without being an accessibility app ([Play AccessibilityService](https://support.google.com/googleplay/android-developer/answer/10964491)).
- Prominent in-app disclosure before capture.

### 17.9 Meta / Instagram and YouTube terms

**Instagram Terms 4.2:** “You can’t attempt to create accounts or **access or collect information in unauthorized ways**,” including **automated** collection with or without login; don’t circumvent technical measures ([Instagram Terms](https://help.instagram.com/581066165581870/), live 17 Oct 2026). Help Center restricts scraping.

**Product interpretation (not a legal opinion):** user-initiated **OS screen capture of the user’s own display**, with OS indicators on, is different from logging into Instagram APIs or bulk-downloading. Still: **do not** scrape Instagram; **do not** ship a Reel downloader; **do not** advertise “download any Reel.” Expect `FLAG_SECURE` and audio opt-out. Demo videos for Play should use a tutorial site, not Instagram.

YouTube ToS similarly restrict unauthorized download and automated access. Saving a **moment screenshot + permitted audio** for personal notes is the intended use; archiving videos is not.

### 17.10 Copyright

Saved frames, audio, article text and AI-chat output may be copyrighted. Position: **personal note-taking and recall**. Export/share is user-initiated with a caution. No “download movie” feature.

---

## 18. Non-functional requirements

| Domain | Requirement |
|---|---|
| Responsiveness | No deliberate interruption of host scrolling; overlay menu p95 < 200 ms on reference devices. |
| Battery | Adaptive sampling; **no numeric battery claim until S1–S4 + profiling** on one entry-level and one midrange phone. |
| Storage | One original frame per item; compressed thumbnail; audio duration cap; visible meter; low-storage warning. |
| Stability | Survive task/background within OS rules; release everything on `onStop()`. Crash-free sessions ≥ 99% target in beta. |
| Offline-first | OCR, match, search, media, ASR, Hub, voice notes, export without network once models are installed. Sync/cloud AI degrade. |
| Accessibility | Content descriptions for ring and actions; reduced motion; contrast; scalable text. Overlay hit targets as large as the visual spec allows. |
| Localization | English first; architecture supports Hindi and other scripts subject to OCR/ASR models. |
| Privacy audit | No analytics/ad SDK in MVP; do not log raw screenshots, audio or transcripts. |
| Availability | Library works when projection is denied or stopped. |
| Robustness | Rotation, cutouts, gesture nav, app switch, low storage, no audio, DRM. |
| Cross-platform consistency | Core parity tests. |
| Browser performance | No noticeable slowdown of page/video; pause sampling on hidden tabs; ≤ 2 captures/sec. |
| Desktop resources | Adaptive sampling; targets set in S11, not promised now. |
| Sync | No data loss; idempotent; latency targets set in S14. |
| Encryption | E2E for sync; local DB encrypted at rest where the platform supports it; keys in OS keystores. |
| Assistant | PTT listening state appears immediately on press; latency targets set in S16. |
| AI cost | Visible usage, per-feature off, limits before paid providers. |
| Quality bar | §4.5 on every release. |
| Security | Threat model before Play; no plaintext keys; TLS for any network; certificate pinning optional in P1. |

---

## 19. Technical discovery, spikes and roadmap

Durations are **estimates** for one experienced Android engineer with QA/design support. They are not calendar commitments.

### 19.1 Phase 0 — Android feasibility (estimate 5–8 engineering days)

**No full-product UI rollout before S1–S4 pass or an approved fallback is recorded.**

| Spike | Objective | Pass (go) | Fail (no-go / fallback) |
|---|---|---|---|
| **S1 Projection** | Authorized session, repeated full-res frames on a Reel/Short and on a website | Portrait bounds preserved; no new prompt per frame; correct stop including **chip and lock** on 15 QPR1+ | Cannot obtain a stable projection on reference devices → stop product or restrict to Sharesheet-only (owner decision) |
| **S2 Overlay** | 28 dp edge-clipped ring, 10–12 dp glyph, drag/snap | Matches reference on 3 densities; no pipe; host remains tappable; restricted-settings path documented | Overlay unusable on sideload 15+ after education → Play-only overlay or Sharesheet fallback |
| **S3 Clean frame** | Overlay present in projection? | Exclusion or hide-and-settle works, or OEM limit documented | Always capture the ring → ship with documented hide-and-settle even if a 1-frame gap exists |
| **S4 Audio** | Controlled test app + Instagram + YouTube on selected devices | Positive on controlled app; third-party results **logged honestly** | Third-party blocked → **do not block V1**; ship screenshot+OCR+Sharesheet for those apps |

**Go/no-go rule:** S1 and S2 are hard gates. S3 fallback is allowed. S4 failure on Instagram/YouTube is **expected** and is not a V1 no-go.

### 19.2 Later spikes (do not start non-Android clients before S1–S4)

| Spike | Objective | Pass |
|---|---|---|
| S5 OCR + topics | Names on annotated clips | ≥80% precision target; false positives reviewed |
| S6 ASR | 30–60 s permitted audio offline | Works on reference phone within thermal/latency budget (budget set in the spike, not now) |
| S7 Stop/privacy | Chip, notification, lock, revoke, kill | No stale indicators; buffers gone |
| S8 Distribution | Play permissions, Data safety, FGS video, licenses | Publishability decision documented (Play and/or FOSS-only) |
| S9 Core parity | Core + fixtures on Android + one other target | Identical match decisions and item fields |
| S10 Browser extension | Visible-tab, page text, exact URL, optional tab audio; DRM labelled | URL stored; 2/sec respected; no page slowdown in manual test |
| S11 Desktop | Display/window capture, ring exclusion, optional system audio, macOS picker, Windows border | OS indicators visible; capability report per OS |
| S12 RN bridge | Native modules from RN UI on Android | No S1–S3 regression |
| S13 Content-type benchmark | Web, long-video, article items | Precision reported per type before advertising that type |
| S14 Sync | Two-device E2E, offline edits, conflicts | No data loss; server ciphertext only; recovery documented |
| S15 Redaction | Tally/Excel/ERP-style samples | Detection rate honest; bounds preserved |
| S16 Voice + assistant | PTT + core intents, EN+HI samples | Intents on labeled set; visible mic |
| S17 Agents + imagination | On-device vs cloud vs BYO key | Provider/cost/privacy comparison for owner |
| S18 Protected content | Streaming apps, browser EME, desktop players | Blocked labelled; no false Saved check |

S16 and S18 **do** gate the corresponding P0 items (voice notes, DRM labeling). S14–S15, S17 do not gate Android MVP.

### 19.3 Phase 1 — Android MVP (estimate 40–55 engineering days after Phase 0)

Replaces v1.2 Phases 1–4 and absorbs D5 P0 Hub items.

| Slice | Estimate | Deliverable | Exit |
|---|---|---|---|
| 1a Foundation & visual | 6–8 d | Compose Home/Library shell, approved ring, persistence | Overlay matches reference; drag/tap |
| 1b Capture & matching | 10–14 d | Session, full frames, OCR, topics, manual/auto save, DRM detect | Offline labeled suite; FR-052 |
| 1c Audio, PTT, storage | 10–14 d | Permitted audio, whisper.cpp, PTT idea notes, item detail, share import | Partial/unavailable/blocked correct; FR-040 |
| 1d Hub & quality | 8–12 d | Today, tags, provenance, search, export/delete, privacy copy, Play packet, tests | All P0 AC + quality bar |
| 1e Buffer | 6–7 d | OEM bugs, overlay restricted settings, policy video, release | Signed beta APK |

**Exit criteria for Phase 1:** P0 FRs pass on the device matrix (§20); S1–S4, S7, S16, S18 done; privacy checklist; no placeholder UI; README limitations honest.

**Alpha path:** after S1–S4, a screen/OCR-only alpha may ship to testers in ~10–15 additional days without PTT/Hub polish.

### 19.4 Phase 2 — Browser extension (estimate after S9+S10; **not dated until Phase 1 learnings**)

Chromium MV3: active-tab observe, page text, exact URL, side panel Library, export, local store. **Exit:** S10 pass; store privacy disclosure; 2/sec cap; DRM labelled; no other-tab access.

Planning order: **core extraction (S9) then extension**, because the owner wants connected devices — but Phase 2 may ship **without** full sync (local extension library + FR-097 export). **Full-res sync stays Phase 4 (D1).** Minimal continuity in Phase 2 is **D7**, pending owner sign-off.

### 19.5 Phase 3 — Desktop (estimate after S11; OS-by-OS release allowed)

macOS first (ScreenCaptureKit + `SCContentSharingPicker`), then Windows (WGC, keep yellow border), then Linux if portal path passes. **Exit:** S11 per OS; OS indicators visible; lock/sleep stop; excluded apps honored.

### 19.6 Phase 4 — Sync, React Native, iOS discovery

| Work | Gate | Exit |
|---|---|---|
| Optional account + E2E sync | D1 confirmed; S14; DPDP notice review | No data loss; ciphertext only; device revoke; account delete |
| React Native UI | S12 | No Android P0 regression |
| iOS discovery | S25 (new): ReplayKit BUE memory/overlay reality | Written decision: companion share-extension vs postpone |

### 19.7 Mapping from v1.2 phase numbers

| v1.2 | v2.0 |
|---|---|
| 0 Feasibility | Phase 0 |
| 1–4 Android MVP (~30–45 d) | Phase 1 (~40–55 d) |
| 5 Core extraction | Prerequisite of Phases 2–4 (S9) |
| 6 Browser | Phase 2 |
| 7 Desktop | Phase 3 |
| 8 React Native | Phase 4 |
| 9 Account/sync | Phase 4 |
| 10 Assistant/AI | P1 inside Phase 1 (PTT only) then remainder with S16–S17 after MVP |

### 19.8 Mapping web-grade work onto phases (v2.0 addendum + v2.1)

Web-grade FRs do **not** start before S1–S4. They attach to existing phases. **v2.1** adds marketing-site, continuity, and D7 rows.

| Phase | Web-grade / GTM / proposals / continuity |
|---|---|
| 0 Feasibility | Unchanged. No Hub web work. Marketing-site **copy draft** allowed; no public waitlist until privacy page exists (FR-094). |
| 1 Android MVP | FR-063 team-free; FR-072 onboarding; FR-073 empty states; FR-074 overlay non-drag alternative; FR-075 dark mode; FR-076 EN+Hindi strings; FR-078 trust page in Settings; FR-081 OCR URL confirm; FR-092 voice language tag with S16; **FR-097 encrypted export/import**; **FR-106** local-only status; **FR-094** public marketing origin (home, privacy, download CTAs, changelog stub) |
| 2 Browser extension | FR-058 side-panel Hub (**option c**, §21.7); FR-059–061; **FR-095 browse**; FR-062 MD/PDF/archive; FR-065–071; FR-077; FR-088 templates; FR-091 timestamps; **FR-096** web onboarding; CWS listing; **optional D7-B FR-103** and **D7-C FR-104** if owner accepts; **FR-098/100/101/102** as proposals after Hub schema |
| 3 Desktop | Same Hub UI if the desktop shell embeds it; native-messaging host for option **b** local bridge; no new GTM store |
| 4 Sync / PWA | FR-064 E2E Hub client (**option a**, §21.7); standalone PWA; Notion token path; FR-086 MCP; FR-087 + **FR-105** read-only E2E share; full-res FR-047 if D1 proceeds |
| GTM | §22 calendar + **§22.9 step-by-step checklists**. GitHub/Obtainium/Izzy/Show HN before Play; PH only when tryable; no Featured-badge campaign |

New spikes (do not start before S1–S4): **S19** CWS packet (permissions, single-purpose copy, listing video of a docs page); **S20** Hub CWV + side-panel first paint; **S21** MCP stdio threat model (prompt injection); **S22** (v2.1) D7-B minimal-sync protocol + one user-owned storage adapter (Drive appData or WebDAV); **S23** (v2.1) fragment-key share threat model (key leakage via Referer, screenshots of URLs, revoke semantics).

---

## 20. Testing, open-source release and definition of done

### 20.1 Device coverage (Android MVP)

At least: Android 10, 13, 14, 15, **16**, plus recent OEM skins. Two densities, small and large screens, cutouts, gesture navigation, 60/90/120 Hz where available. One entry-level and one midrange phone for battery/STT.

Follow-up matrices set at spike time.

### 20.2 Required scenarios

Keep v1.2’s list, plus: Android 15 QPR1 chip tap and lock-to-stop; single-app vs entire-screen; sideload overlay restricted settings; `captureVisibleTab` throttling; Sequoia picker (desktop); Windows yellow border visible; DRM Netflix/Prime-class app; Tally/Excel opt-in path; Hindi and English PTT.

### 20.3 Example acceptance scenarios

```gherkin
Given the user has started a valid Observe session
And the floating ring is visible at the left screen edge
When a matching Reel title is visible
Then the app may save the entire captured projection frame
And it must not crop away text at the frame corners
And it must not display a blocking custom progress dialog

Given a source app prevents playback audio capture
When a relevant clip is saved
Then the screenshot and readable OCR text are retained
And the item displays audio_unavailable
And the transcript is not invented

Given the candidate is saved successfully
When the overlay acknowledges the save
Then the logo remains the approved blue hollow ring
And the attached status glyph is a standalone soft-green check
And the overlay returns to normal state automatically

Given the user is on Android 15 QPR1 or higher with an active session
When the user taps the status-bar capture chip or locks the device
Then projection stops, buffers are released, and the ring shows idle/paused
And a new session requires a new OS consent dialog

Given the browser extension has an active Observe session
And the active tab shows an article matching a user topic
When the article is saved
Then the item stores the exact tab URL with origin browser_tab
And captureVisibleTab has not been called more than twice in any rolling one-second window
And no other tab is read

Given the user holds push-to-talk while viewing an Excel sheet that is opted in
When the user says "Save this, idea: reuse this layout for invoices"
Then the capture is saved with the full frame, time seen and source app
And a voice note with audio and transcript is linked
And the provenance narrative has no guessed fields

Given the user is watching a DRM-protected movie
When the user taps Save
Then the item is saved as capture_blocked with time and any known source
And no blank screenshot is presented as a successful capture
And the overlay does not show the green saved check for a screenshot
```

### 20.4 Open-source release

- Repositories: `obzrai-android` for the scaffold; multi-platform `obzrai` monorepo later. Avoid `com.observer.ai` (collision with Observer AI listings).
- License proposal: **Apache-2.0**; model/library audit.
- README: permissions, approved overlay screenshot, compatibility, **“This is not a universal screen/audio recorder.”**
- Docs: PRD, ARCHITECTURE, ANDROID_COMPATIBILITY, PRIVACY, DESIGN_SYSTEM, CONTRIBUTING, RELEASE_CHECKLIST, PLATFORMS, OBSERVER_CORE, plus per-platform docs when those phases start; SYNC_AND_ACCOUNT, AI_AGENTS, SENSITIVE_DATA, QUALITY_BAR.
- CI: Gradle, ktlint/detekt, unit/Compose tests, SBOM, reproducible debug APK.
- No private screenshots in bug reports by default.
- Distribution: signed releases, SHA256, IzzyOnDroid / Obtainium / F-Droid RFP; Play after S8.

FOSS channel facts (distribution, not product claims): IzzyOnDroid **2,149,476 downloads in Sep 2026** ([monthly report generated 8 Oct 2026](https://codeberg.org/IzzyOnDroid/iod-monthly-report/src/branch/main/reports/monthly_report_2026-09.md)); F-Droid 2.0 announced **24 Sep 2026** ([F-Droid blog](https://f-droid.org/2026/09/24/f-droid-2.0-a-new-chapter-for-android-freedom.html)).

### 20.5 Definition of done — Android V1

- [ ] Approved exact blue-ring overlay; attached small state glyph; no pipe; no replacement logo.
- [ ] Drag, snap, persistence, compact Save/Skip/Settings.
- [ ] User sees and controls OS capture consent, FGS notification and (where present) status-bar chip; stop correct including lock.
- [ ] Full-frame originals; thumbnails may be scaled.
- [ ] OCR + topic matching; manual Save/Skip.
- [ ] Audio when available; blocked audio labelled; no invented transcript.
- [ ] Exact share URL or honest empty source.
- [ ] Home, Library, Search, Item detail, Topics, Settings; Today; tags; provenance.
- [ ] PTT spoken idea notes with separate mic consent.
- [ ] Local-only default; deletion; no covert capture.
- [ ] Banking/payment/password excluded; DRM labelled `capture_blocked`; no FLAG_SECURE bypass.
- [ ] Device matrix, 200-item evaluation set, license audit.
- [ ] GitHub repo, README, tagged beta APK.
- [ ] Any on-screen content type, not only Reels; `content_type` recorded.
- [ ] Observer core contract in place for later ports.
- [ ] Quality bar (§4.5): P0 tests green, threat model, accessibility, no placeholder UI, signed builds, release notes, support channel.
- [ ] Play packet (if submitting): FGS video, Data safety, permission justifications.
- [ ] Developer verification registration complete before relying on certified-device installs in 2027.
- [ ] Encrypted export/import (FR-097) round-trips on a fresh install; Trust center (FR-078) and public `/privacy` exist before any store listing.

Each follow-up platform gets its own DoD at phase start.

---

## 21. Web-grade product experience

Owner direction for this addendum (17 October 2026): make OBZRAi a **professional, web-grade product** — a Research Hub people can live in, an extension that feels like a first-class Chrome companion, and a design system that would pass a store review and a design critique. This section specifies that experience. It does not replace Android-first delivery (§5, §19). Android spikes S1–S4 remain the first engineering gate.

**Locked product decisions for this surface (addendum, 17 Oct 2026):**

| Decision | Choice | Why |
|---|---|---|
| First web-grade Hub | Chromium **side panel** in Phase 2 | Same session as capture; no extra origin until E2E exists |
| Standalone Hub | **PWA / local-first web app** in Phase 4 | Needs D1/S14 so a second origin can decrypt |
| Teams | **Team-free in V1** | No shared workspaces, orgs, or live co-edit. Read-only collection share is specified below (FR-087 + FR-105); still not teams. |
| CWS “Featured” badge | **Do not campaign for it** | Google is sunsetting the badge later in 2026; self-nominations closed **20 Aug 2026** ([Chrome blog](https://developer.chrome.com/blog/cws-review-updates-2026), 20 Aug 2026) |
| Lifetime license | Optional **P2**, not V1 | See §22.4 |
| Web Hub architecture | **Phase 2 = (c) side panel; Phase 2 optional (b) local bridge; Phase 4 = (a) E2E web client** | See §21.7. A hosted Hub that can read plaintext is out of scope on every phase. |
| Marketing origin | **Public site from Phase 1** | Home, privacy/trust, per-platform install CTAs, docs, changelog, open-source. FR-094. Distinct from the Hub app origin. |

New functional requirements for this section are **FR-058–FR-078** (v2.0 addendum) plus **FR-094–FR-096, FR-105** (v2.1). Proposed strength features in §23 continue as **FR-079–FR-093** and **FR-098–FR-102**, marked **PROPOSAL**. Continuity FRs FR-097, FR-103, FR-104, FR-106 live in §24.

### 21.1 Research Hub web app and dashboard

The Hub is the same Library specified in §12, rendered as a **local-first web surface**. Phase 2 ships it **inside the extension side panel** (architecture option **c**, §21.7). Phase 4 may promote the same UI to a standalone origin (`app.obzrai` or equivalent after clearance) installable as a PWA **only as an E2E client** (option **a**). Until E2E sync exists, each client’s Hub is that device’s library plus encrypted export/import (FR-097).

| Capability | Spec | Phase | FR |
|---|---|---|---|
| Local-first web app / PWA | IndexedDB (or OPFS) holds items, tags, collections, voice notes. The Hub works offline after first load. A web app manifest + service worker make it installable. No account required (FR-046, FR-049). W3C TAG draft *Installing Websites Must Be Easy* (16 Oct 2026) is a direction, not a shipping dependency ([W3C TAG](https://www.w3.org/blog/2026/installing-websites-must-be-easy/), 16 Oct 2026 — **Unverified** as a Recommendation; it is a draft). | Phase 2: side-panel origin. Phase 4: standalone PWA | FR-058 |
| Browse | **All** view: reverse-chronological list and a compact grid of thumbnails. Filters (date, device, platform, content type, source, collection, has-voice, has-audio, capture-blocked). Sort: newest, oldest, by collection. Keyboard: j/k move, enter opens, `/` focuses search. Same entities as Android Library. | Phase 2 | FR-095 |
| Today / timeline | Same Today view as Android (FR-035): today’s items newest first, time-block grouping, calendar for earlier days, badges for voice / imagination / audio / blocked. Web adds keyboard j/k, a date chip, and a “jump to now” control. | Phase 2 | FR-059 |
| Boards / collections | Web name for **Collections** (FR-037). Kanban-style columns are optional visual sugar over the same collection entity; they are not a second data model. An item may sit on several boards. Live co-edit stays out of V1 (FR-063). Read-only share is the share primitive (next row). | Phase 2 (list/grid); Phase 4 (optional board layout) | FR-060 |
| Search | Keyword search on the web matches OCR, page text, transcripts, voice-idea text, tags, collections, sources (FR-014, FR-061). Filters as Browse. Semantic search remains P1 (FR-024, FR-080, FR-100). p95 ≤ 500 ms on a 1,000-item fixture. | Phase 2 keyword; Phase 4 semantic if S17 allows | FR-061 |
| Sharing / export | **V1 share = export**, plus a later **read-only collection link** (next row). Targets: **Markdown** (Obsidian-friendly wikilinks + YAML front matter), **PDF** (print stylesheet; one item or one day), **portable archive** (media + `metadata.json`), HTML, CSV (FR-039). **Notion:** create pages via Notion API (`Notion-Version` header; current public version at research time **2026-03-11** — [developers.notion.com](https://developers.notion.com/reference/intro), date not shown / live Oct 2026). User pastes an integration token; OBZRAi never scrapes Notion. **Obsidian:** write a folder of `.md` + attachments the user can drop into a vault; no undocumented vault RPC. **Google Drive:** user-authorized folder as an export target (FR-099, **PROPOSAL**). Readwise is a **PROPOSAL** (FR-084). | Phase 2: MD / archive / PDF. Phase 4: Notion token; Drive export as proposal | FR-062 |
| Read-only collection share | Explicit publish of **one collection**. Viewer gets a read-only page: title, item list, thumbnails, provenance, redacted items stay redacted. **E2E share links:** ciphertext on a blob store; **decryption key in the URL fragment** (`#k=…`), following Excalidraw ([blog, 21 Mar 2020](https://plus.excalidraw.com/blog/end-to-end-encryption)). RFC 3986 fragments are processed by the user agent; HTTP requests do not include `#` ([RFC 3986](https://www.rfc-editor.org/rfc/rfc3986)). **Revoke** deletes or tombstones the ciphertext (Proton Drive “stop sharing”, help updated 16 Oct 2026). **Expiry** default **7 days**, max **31 days**, matching Bitwarden Send ([help, 16 Sep 2026](https://bitwarden.com/help/send-lifespan/)). Optional password (Proton pattern). `robots noindex`. Viewers need no account. This is still team-free: no comments, no presence, no edit. S23 threat model before ship. | Spec in v2.1; ships P2 after a blob store exists (Phase 4 default; Phase 2 only if D7-B already has a ciphertext store) | FR-087, FR-105 |
| Team-free V1 | No org, no seats, no live presence, no ACL on items. A “Share” button offers export and, when FR-087 is on, a read-only link. Copy says “read-only link,” never “for teams.” | All V1 clients | FR-063 |
| E2E-synced Hub | When D1/S14 ships, the web Hub is another E2E client: ciphertext on the relay, keys in the browser’s platform authenticator / wrapped recovery key (§16.5). Standalone PWA **does not ship** before this, except as a local-only demo on the same machine as the extension (option **b**). | Phase 4 | FR-064 |

**Information architecture (web Hub).** Left rail: Today · All (browse) · Boards · Search. Item pane: full frame, provenance narrative, voice idea, tags, open-source (only if known), continuity chip (FR-106). Settings: topics, capture, privacy, **Trust center** (FR-078), export, share-links (when FR-087 is on), account (optional). Empty and error states are specified in §21.4. Marketing pages are **not** this IA; they live on the public origin (§21.6).

**Performance budgets for the Hub (targets, FR-077):** public marketing origin and installed PWA aim for Core Web Vitals “good” at p75: **LCP ≤ 2.5 s, INP ≤ 200 ms, CLS ≤ 0.1** ([web.dev Core Web Vitals](https://web.dev/articles/vitals), thresholds unchanged in 2026 write-ups; [webhelpagency, 7 Jul 2026](https://webhelpagency.com/blog/core-web-vitals-2026/)). Side panel first paint **< 300 ms** on a mid-range laptop (labelled target; measure in S10). Extension capture must not slow the host page (already S10).

### 21.2 Browser-extension UX

Phase 2 is a **Chromium Manifest V3** extension. Firefox is a later port (open question 11). The extension is a capture client whose Library is the side panel Hub.

#### Side panel (FR-065)

- `sidePanel` permission; `side_panel.default_path` for a **global** Hub panel.
- API available **Chrome 114+**; `chrome.sidePanel.open()` from **Chrome 116+** and **requires a user gesture** plus **`tabId` or `windowId`** ([sidePanel API](https://developer.chrome.com/docs/extensions/reference/api/sidePanel), last updated **11 Sep 2026**).
- Open on toolbar click via `setPanelBehavior({ openPanelOnActionClick: true })`.
- Panel contents: Today, search, last-saved item, Save/Skip for the active tab during a session, voice-note button.
- Do not hijack search or the new-tab page (Quality Guidelines: side panels that hijack browsing are a listed violation — [Quality guidelines](https://developer.chrome.com/docs/webstore/program-policies/quality-guidelines), last updated **10 Jul 2024**).

#### Keyboard shortcuts (FR-066)

`chrome.commands`: shortcuts **must include Ctrl or Alt**; **at most four `suggested_key` bindings**; users may add more at `chrome://extensions/shortcuts` ([commands API](https://developer.chrome.com/docs/extensions/reference/api/commands), last updated **11 Sep 2026**). Suggested defaults (all overridable):

| Command | Suggested | Action |
|---|---|---|
| `_execute_action` | Ctrl+Shift+O | Open side panel |
| `save-this` | Alt+Shift+S | Save active tab / current moment |
| `skip-this` | Alt+Shift+X | Skip candidate |
| `voice-note` | Alt+Shift+V | Start/stop PTT in the panel |

Document conflicts in first-run. Never register a global (Chrome-unfocused) command in V1.

#### Highlight and clip (FR-067)

On the active tab, during a user-started Observe session **or** an explicit toolbar/context-menu action: user selects text → context menu **“Save highlight to OBZRAi”** stores selection, surrounding paragraph, exact URL (`source_url_origin = browser_tab`), and a viewport screenshot if `captureVisibleTab` quota allows. No crawling of other pages (FR-029). Prefer **`activeTab`** over host_permissions; `<all_urls>` triggers in-depth review.

#### YouTube timestamp capture (FR-068)

When the active tab is `youtube.com/watch` or `youtu.be`, Save stores the **exact URL plus a timestamp** only when the player time is genuinely readable (content script on the watch page, or the URL’s existing `t` / `start`). Canonical forms:

- Watch URL: `https://www.youtube.com/watch?v=VIDEO_ID&t=90s` (`&t=` because `v=` is already present)
- Short URL: `https://youtu.be/VIDEO_ID?t=90`
- Embed (export only): `start=` in seconds

YouTube’s share UI uses the `t` parameter in seconds or `1m30s` form (secondary guides, e.g. [Sendible, 1 Oct 2026](https://www.sendible.com/insights/youtube-timestamp-link); [YoutubeView.app, 11 Oct 2026](https://youtubeview.app/blog/youtube-timestamp-link)). **Never** download the video file. Chrome Web Store troubleshooting lists **“facilitating download of YouTube videos”** as a prohibited-product rejection reason ([Troubleshooting](https://developer.chrome.com/docs/webstore/troubleshooting), page seen 17 Oct 2026; last-updated in this environment **20 Jul 2026**).

#### Voice note in the extension (FR-069)

Same PTT contract as FR-040: hold-to-talk in the side panel, separate mic permission, visible recording state, audio + transcript linked to the current item or a standalone idea. No wake word (FR-042 stays P2). No off-device audio until the user opts into a cloud STT provider (D2).

#### Chrome Web Store — review, permissions, payments (FR-070, FR-071)

These are **shipping constraints**, not optional polish.

| Topic | Reality (sourced) | OBZRAi rule |
|---|---|---|
| MV3 only | CWS accepts Manifest V3 | `manifest_version: 3` |
| Registration | One-time registration fee; **India is a supported country** ([Register](https://developer.chrome.com/docs/webstore/register), last updated **13 Feb 2024**). The official register page does **not** state the dollar amount. Secondary 2026 write-ups and checklists still quote **US$5 one-time** ([ExtensionBooster, 22 Jun 2026](https://extensionbooster.net/blog/chrome-web-store-developer-registration-fee-2026-guide/); [Extension Radar, 16 Feb 2026](https://www.extensionradar.com/blog/chrome-web-store-developer-fee-2026)). Treat **$5 as widely reported, official amount not on the register page**. | Budget $5; confirm at checkout |
| 2-Step Verification | Required to publish or update ([Program Policies — 2-Step Verification](https://developer.chrome.com/docs/webstore/program-policies/two-step-verification), last updated **1 Nov 2022**; restated in [CWS API docs](https://developer.chrome.com/docs/webstore/using-api), **8 Oct 2026**) | Enable 2SV on the publisher Google account before first upload |
| Extension slots | Default **two** extension slots per publisher as of **20 Aug 2026**; existing extras grandfathered; increase via dashboard ([Chrome blog](https://developer.chrome.com/blog/cws-review-updates-2026), 20 Aug 2026) | Ship **one** item (the observer). Do not split Hub into a second extension in V1 |
| Single purpose | “An extension must have a single purpose that is narrow and easy to understand” ([Quality guidelines](https://developer.chrome.com/docs/webstore/program-policies/quality-guidelines), 10 Jul 2024; [FAQ](https://developer.chrome.com/docs/webstore/program-policies/quality-guidelines-faq), 10 Jul 2024). Excessive permissions count as extra purposes. Limited Use (enforced **1 Aug 2026**): collected data must be strictly necessary to the disclosed single purpose ([policy update blog](https://developer.chrome.com/blog/cws-policy-updates-2026), 1 Jul 2026) | Single purpose copy: **“Save useful moments from the page you are looking at into a private local research library.”** Capture, clip, timestamp, voice note, and Hub search all serve that job. Do not add a new-tab page, ad blocking, or generic screenshot-to-cloud |
| Least privilege | Request the narrowest permission; do not future-proof ([Use of Permissions](https://developer.chrome.com/docs/webstore/program-policies/permissions), 1 Nov 2022) | Prefer `activeTab`, `sidePanel`, `storage`, `offscreen`, `commands`, `scripting`. `tabCapture` only if S10 proves tab audio needs it. Justify every permission in the dashboard Privacy tab (FR-070) |
| Host permissions | `<all_urls>` / broad hosts invite in-depth review | Default: **no** broad host_permissions. Content scripts on YouTube watch URLs only if timestamp capture needs them, listed explicitly |
| Remote hosted code | MV3 forbids executing remote script | Bundle all JS; no CDN eval |
| Featured badge | Sunsetting **later in 2026**; self-nominations closed **20 Aug 2026** ([Chrome blog](https://developer.chrome.com/blog/cws-review-updates-2026), 20 Aug 2026). Third-party snapshot 20 Jul 2026: Featured was **7.8%** of MV3 listings and **48.3%** of displayed users ([Chrome-Stats](https://chrome-stats.com/blog/2026/07/20/featured-badge-maintenance-signal), 20 Jul 2026) — **Unverified** as Google’s own metric | Do not plan a Featured campaign. Compete on listing quality, ratings, and editorial home merchandising (editors curate; you cannot pay — [CWS discovery](https://developer.chrome.com/docs/webstore/discovery), 21 Mar 2022) |
| Payments | Google is **not** the seller. Developer must identify that they, not Google, sell the product ([Accepting Payment](https://developer.chrome.com/docs/webstore/program-policies/accepting-payment), 1 Nov 2022). Declare **Contains in-app purchases** in Distribution ([dashboard distribution](https://developer.chrome.com/docs/webstore/cws-dashboard-distribution), date not shown). Native CWS payments are deprecated ([payments deprecation doc](https://developer.chrome.com/docs/webstore/cws-payments-deprecation), date not shown / listed on [CWS docs index](https://developer.chrome.com/docs/webstore/), updated **28 Apr 2026**) | Pro on the extension: **Stripe** (or ExtensionPay wrapping Stripe). Never tell users “pay Google.” Keep local capture free |
| YouTube download | Facilitating YouTube video download is a stated rejection reason ([Troubleshooting](https://developer.chrome.com/docs/webstore/troubleshooting), seen 17 Oct 2026) | Moments + timestamped URL only. No MP4, no youtube-dl, no “download this video” copy |

**Listing craft (Phase 2 exit):** one-sentence purpose, privacy policy URL, screenshots of side panel + Today, a 30s video of Save on a documentation page (not Instagram), permission justifications, “Contains in-app purchases” if Pro is wired, trader/non-trader declaration. Full CWS checklist: §22.9.

#### Extension UX polish (v2.1, still FR-065–FR-071)

These are Phase 2 **exit** details, not a second product:

- Toolbar icon uses the approved blue hollow ring at 16/32/48/128; grayscale when no session, ring-fill when observing.
- First-run: pin-prompt copy, then open the side panel on the first toolbar click (`setPanelBehavior`).
- Save confirmation is the existing Saved glyph plus a 2-second “Saved to Today” chip in the panel; the chip is a link to the new item.
- Context-menu label is exactly **“Save highlight to OBZRAi”** (FR-067); no extra context-menu entries in V1.
- Side panel empty state matches FR-073; loading skeleton ≤ 300 ms first paint target (FR-077).
- Errors: DRM/black frame shows `capture_blocked` in the panel with the same honesty copy as Android.
- Uninstall: CWS listing and in-panel Settings both link to export (FR-097) before the user loses the IndexedDB library.
- `nativeMessaging` is requested **only** if D7-C / option **b** ships; justify it as “talk to the OBZRAi app on this computer.”

### 21.3 Design system and polish

Applies to Android (already in §4.5 / §11), the extension, and the Hub. New FRs below are the **web-grade bar**.

| Item | Spec | Phase | FR |
|---|---|---|---|
| Onboarding | Three screens max: (1) what the ring/session is, (2) local-first + kill switch, (3) first save. Extension: pin the toolbar icon, grant `activeTab` on first Save, open the panel. **Web (FR-096):** marketing site CTA picks a platform → store or GitHub → in-product three screens. Skip always available. No account wall. Waitlist is email-only and only **before** a public APK; it is never the in-product first screen. | Phase 1 Android; Phase 2 extension; marketing site Phase 1 | FR-072, FR-096 |
| Empty states | Today empty: “Nothing saved yet. Start a session or clip this page.” Search empty: “No matches. Try OCR text or a tag.” Boards empty: “Create a board for a project.” Each empty state has **one** primary action. | Phase 1–2 | FR-073 |
| Accessibility | **WCAG 2.2 Level AA** on Hub, extension pages, and Android where Compose maps. WCAG 2.2 is a W3C Recommendation (**5 Oct 2023**); current edition **12 Dec 2024** ([W3C WAI what’s new](https://www.w3.org/WAI/standards-guidelines/wcag/new-in-22/), page seen 17 Oct 2026). AA adds **2.4.11 Focus Not Obscured (Minimum), 2.5.7 Dragging Movements, 2.5.8 Target Size (Minimum), 3.2.6 Consistent Help, 3.3.7 Redundant Entry, 3.3.8 Accessible Authentication (Minimum)** (six A/AA additions; 4.1.1 Parsing removed). Overlay drag already needs a non-drag alternative (Save/Skip menu). Hub targets ≥ 24×24 CSS px. No puzzle CAPTCHA on sign-in. | Phase 1 Android overlay alternatives; Phase 2 Hub AA | FR-074 |
| Dark mode | Follow OS `prefers-color-scheme`; user override in Settings. Ring identity stays the approved blue hollow ring on both themes. | Phase 1 Android; Phase 2 Hub | FR-075 |
| i18n | **English + Hindi** UI strings in V1. Hinglish is accepted in **voice notes and search queries** (user language), not as a third UI locale. Pseudo-l10n in CI. Locale in Settings, not inferred from IP. | Phase 1 EN; Hindi strings before Play India listing | FR-076 |
| Performance budgets | Hub CWV as §21.1. Extension: no measurable host-page jank in S10 manual test; `captureVisibleTab` ≤ 2/sec (already FR-028). Side panel first paint target < 300 ms. | Phase 2 | FR-077 |
| Trust center / privacy dashboard | In-app page: what is stored, where (device / optional relay ciphertext), session status, permission list, last network call (should be “none” on default), export, delete all, account delete. Public `obzrai.app/privacy` and `/security` mirrors the same facts. No dark-pattern analytics opt-out. | Phase 1 Android Settings; Phase 2 Hub page | FR-078 |

**Visual language.** Keep §11. Rejected styles still rejected. Web Hub is compact, bookmarks-like, not a colorful “AI dashboard.” Motion: 150–200 ms ease; respect `prefers-reduced-motion`.

### 21.4 Web-grade FR index

| ID | Pri | Phase | Requirement | Acceptance |
|---|---|---|---|---|
| FR-058 | P1 | 2 / 4 | Local-first Hub web surface (side panel, then PWA) | Offline library after first load; no account required |
| FR-059 | P1 | 2 | Today / timeline on web | Matches FR-035 content; keyboard accessible |
| FR-060 | P1 | 2 | Boards = collections on web | Same entity as FR-037; no sharing in V1 |
| FR-061 | P1 | 2 | Hub search on web | FR-014 parity; p95 ≤ 500 ms / 1,000 items |
| FR-062 | P1 | 2 / 4 | Export Markdown, PDF, archive; Notion/Obsidian paths | Obsidian vault folder valid; Notion pages only with user token; PDF print of a day works |
| FR-063 | P0 | all | Team-free V1 | No org/seat/ACL UI shipped |
| FR-064 | P1 | 4 | E2E Hub client | Ciphertext only on relay; matches FR-047 |
| FR-065 | P1 | 2 | Side panel Hub | Chrome 114+; open() only on gesture with tabId/windowId |
| FR-066 | P1 | 2 | Keyboard shortcuts | ≤ 4 suggested keys; Ctrl/Alt required; documented |
| FR-067 | P1 | 2 | Highlight / clip | Selection + URL on explicit action; no other-tab read |
| FR-068 | P1 | 2 | YouTube timestamp | `t=` / `?t=` stored only if known; no video file |
| FR-069 | P1 | 2 | Extension voice note | FR-040 contract in the panel |
| FR-070 | P1 | 2 | Least-privilege + justifications | Privacy tab filled; no unused permissions |
| FR-071 | P1 | 2 | Single-purpose listing | One-sentence purpose; no extra surfaces |
| FR-072 | P1 | 1–2 | Onboarding | ≤ 3 screens; skippable; no account wall |
| FR-073 | P1 | 1–2 | Empty states | Every empty view has one primary action |
| FR-074 | P1 | 1–2 | WCAG 2.2 AA | Overlay has non-drag alternative; Hub AA checklist |
| FR-075 | P1 | 1–2 | Dark mode | OS follow + override; ring identity preserved |
| FR-076 | P1 | 1 | i18n EN + Hindi | UI in both; Hinglish OK in voice/search |
| FR-077 | P1 | 2 | Performance budgets | CWV good targets; 2/sec cap; panel paint target |
| FR-078 | P1 | 1–2 | Trust center | In-app + public page; delete-all works |
| FR-094 | P1 | 1 | Marketing site | Pages in §21.6 live; privacy URL used by Play/CWS |
| FR-095 | P1 | 2 | Hub browse | All list/grid + filters; keyboard |
| FR-096 | P1 | 1–2 | Web onboarding | CTA → install → ≤ 3 screens; no account wall |
| FR-105 | P2 | 4 (2 if D7-B store exists) | Share revoke / expiry | Fragment key; 7-day default; one-tap revoke |

### 21.5 Out of scope for web-grade V1

Multi-player editing, org admin, SSO, a second CWS listing, Firefox/Safari stores, a **hosted Hub that can read plaintext**, in-page rings that block page controls, YouTube/Instagram downloaders, always-on content scripts on all sites. Read-only E2E share (FR-087/105) is specified and still team-free.

### 21.6 Marketing site (FR-094)

The marketing origin is a **public website**, distinct from the Hub app origin. It ships in **Phase 1** (privacy URL is a Play/CWS dependency) and grows in later phases. It never holds user libraries.

**Pages (V1 information architecture)**

| Path (illustrative) | Job | Phase |
|---|---|---|
| `/` Home | Promise, 20s demo (OS chip visible, docs/YouTube — not Instagram download), three pillars from §22.2, platform CTAs | 1 |
| `/download` or install section | **Per-platform CTAs:** Android APK (GitHub Releases + checksums), Obtainium JSON, Izzy, Play (when live), Chrome Web Store (Phase 2), desktop (Phase 3). Each CTA states OS requirements and the honest limitation line. | 1; stores as they open |
| `/privacy` and `/security` | Mirror FR-078. What is stored, where, session-only capture, no default upload, DPDP contact, deletion. This URL is the Play / CWS privacy policy. | 1 (blocker for listings) |
| `/docs` | Getting started, overlay restricted-settings, DRM honesty, export, BYO key, FAQ | 1 stub; 2 full |
| `/changelog` | Dated releases; known-broken apps | 1 |
| `/open-source` | License (Apache-2.0 intent), repo link, how to build, how to contribute, code of conduct, “hosted sync is separate” | 1 |
| `/blog` or notes | Optional; AEO/SEO articles from §22.6 | 2 |
| Waitlist | Email-only, **pre-APK only**; disabled the day a public APK exists | Pre-launch |

**Messaging.** Headline carries the product promise. Subhead: session-only, local-first, Research Hub. CTAs are **Install on Android** / **Add to Chrome** / **Get the APK**. Pricing table matches §8 / §22.4 (Free, BYO key, Pro). No “for teams.” No downloader language.

**SEO / AEO.** Answer-first titles (examples already in §22.6). `robots.txt` allows `OAI-SearchBot` if the owner wants citation in AI answers. Public pages aim for Core Web Vitals “good” at p75 (FR-077). Changelog and docs are crawlable. The Hub app origin, once it exists, is `noindex` for any page that could contain user content.

**Trust.** `/privacy` + `/security` + in-app Trust center are the same facts. Link them from Settings, Play Data safety, CWS Privacy tab, and the footer.

### 21.7 How the web app fits local-first + E2E sync

Three architectures can put a Hub in a browser. OBZRAi may use more than one, **in this order**. A server that can read the library is forbidden in every phase.

| Option | How it works | When | Privacy | Recommendation |
|---|---|---|---|---|
| **(c) Extension side panel as the Hub** | The Hub UI runs in the extension origin (`chrome-extension://…/sidepanel.html`). IndexedDB is the local store. Capture and library share a process. No extra origin, no account. | **Phase 2 default** (already FR-058/065) | Strong. Bytes stay in the profile unless the user exports. | **Ship this first.** |
| **(b) Local bridge** | The Android app, desktop app, or a native-messaging host **serves the local store** to a page on `localhost` or a WebRTC pairing (QR). Chrome native messaging: [docs, 16 Sep 2026](https://developer.chrome.com/docs/extensions/develop/concepts/native-messaging). | **Phase 2 optional**, tied to D7-C (FR-104). Desktop Phase 3 can host the same bridge. | Strong if bound to loopback / paired device. `nativeMessaging` needs a CWS justification. | **Add when pairing is wanted** without an account. Useful for “open Hub in a full tab on this machine.” |
| **(a) Synced E2E web client** | A standalone origin (`app.obzrai` / PWA) downloads **ciphertext**, decrypts **in-browser** with a key from the platform authenticator or recovery key. Same model as Standard Notes’ web client and Obsidian’s published E2E claim. | **Phase 4**, gated on D1 + S14 + FR-064. If D7-B is accepted, a thinner client (metadata + thumbs) may appear as a Phase 2 fast-follow against user-owned storage, still decrypting in-browser. | Server (or Drive appData, WebDAV) sees ciphertext. Lost key = lost remote copy (Obsidian’s stated failure mode, WorkifyLab 31 Aug 2026). | **This is the only standalone web Hub.** It waits for keys. |

**Per-phase recommendation (locked for planning):**

- **Phase 1:** Android Hub only. Continuity = FR-097 encrypted export/import. Marketing site is public; it is not a Hub.
- **Phase 2:** **(c)** side panel is the web Hub. **(b)** local bridge only if D7-C is accepted. Encrypted export remains the remote-device path unless D7-B is accepted, in which case a thin E2E payload (FR-103) may use user-owned storage, still decrypted on device.
- **Phase 3:** Desktop embeds the same Hub; may expose the **(b)** bridge for a full-tab view.
- **Phase 4:** **(a)** E2E web client / PWA. Full-res optional sync (D1). Read-only fragment-key shares (FR-087/105).

Copy on the marketing site: “Your Hub lives on your device. A browser tab can open it when you install the extension, or later when you opt into encrypted sync.”

## 22. Go-to-market strategy

How OBZRAi is run in the market. Companion qualitative research: `/workspace/AI_Observer_Marketing_Research.md` (17 Oct 2026). This section is a **plan with labelled targets**, not a forecast. Every external claim has a URL and date. Metrics in §22.7 are **targets**.

### 22.1 Target segments (ICP)

**Primary ICP (launch):** India + global **Android developers and technical learners** who already live on GitHub / F-Droid / Obtainium / Chrome, and who lose GitHub repos, CLI tools, papers and tutorial moments from Reels, Shorts, YouTube and docs. Matches §3.2 and the marketing-research recommendation.

**Secondary ICP:** researchers and creators who clip tutorials; desktop knowledge workers who want one Hub across phone and browser; accountants/operators **only** after per-app opt-in (D6) — not a launch ad audience.

**Not V1:** recipe mass-market (ReciMe), Hinglish general “memory of everything” (BhaiFinder), dance downloaders, Notion-sync second-brain workers (Recall.it / Readwise) until export (FR-062) exists.

**Why India + global, not India-only:** Meta/IPSOS (4,000+ respondents, 23 centres) reported **97% of surveyed users in India watch video on Meta platforms daily** ([Meta newsroom](https://about.fb.com/news/2026/06/reels-is-shaping-indias-video-first-future-across-gen-z-women-bharat/), 3 Jun 2026). YouTube Shorts were stated at **over 200 billion daily views** ([YouTube Blog / Cannes](https://blog.youtube/news-and-events/neal-mohan-cannes-2025/), 18 Jun 2025). ReciMe proves people pay to rescue one vertical of saves (Play **1M+**, listing updated **1 Oct 2026**). BhaiFinder proves an Indian founder can price memory in INR (**₹99 / ₹199 per month**, [Stork](https://www.stork.ai/en/bhaifinder), 30 Sep 2026) but is share-sheet and cloud. The FOSS/dev channel (IzzyOnDroid **2,149,476** downloads in Sep 2026, report generated **8 Oct 2026**) is already global. Launch copy: English first, Hindi UI (FR-076), Hinglish demos.

**Persona snapshots (launch messaging, not separate SKUs):**

| Persona | Job | Channel | Offer |
|---|---|---|---|
| Dev / AI learner | “That repo from a Reel at 1am” | GitHub, r/LocalLLaMA, Show HN, Hinglish tech YouTube | Overlay + local Hub, no account |
| Researcher / creator | Day of tabs and timestamps | Chrome Web Store, PH, X | Side panel clip + Today |
| Privacy Android user | Pixel-cloud backlash refugee | r/fossdroid, Graphene, Izzy | FOSS APK, session-only, Trust center |

### 22.2 Positioning versus competitors

**Positioning statement.** For people who learn from screens (Reels, YouTube, docs, tools), OBZRAi is the **session-only, local-first observer** that files the moment — full frame, spoken idea, honest provenance — into a searchable Research Hub. Unlike share-sheet importers, it does not need a public link. Unlike always-on screen memory, it runs only while the OS capture indicator is on. Unlike Pixel Screenshots, it is not Pixel-only and does not require Google’s cloud.

**Messaging pillars**

1. **Save is a bookmark. This is a backup you can search.** Instagram watch history is documented as **30 days**; saved items disappear if the creator deletes the Reel ([Crown Media, 9 Oct 2026](https://crownmediacreative.com/how-to-find-saved-posts-on-instagram/); [Lilach Bullock, 3 Oct 2026](https://www.lilachbullock.com/how-to-find-your-saved-reels-on-instagram/)).
2. **Nothing leaves the device unless you opt in.** Pixel Screenshots’ 2026 cloud shift produced visible backlash ([Android Authority, 24 Jun 2026](https://www.androidauthority.com/google-made-me-turn-off-pixel-screenshots-3680197/)).
3. **You start the session. The OS chip stays on. You can kill it.** Contrast Microsoft Recall (Copilot+ opt-in OS feature) and Rewind/Limitless (acquired by Meta **5 Dec 2025**, capture disabled **19 Dec 2025** — [TechCrunch](https://techcrunch.com/2025/12/05/meta-acquires-ai-device-startup-limitless/)).
4. **Built for technical learners, not downloaders.** Do not lead with Instagram. Demo YouTube tutorials and docs. CWS rejects YouTube downloaders (§21.2).

**Comparison (qualitative; prices dated in §8.2)**

| | OBZRAi | ReciMe | BhaiFinder | Raindrop | Readwise | Observer AI | Pixel Screenshots | Screenpipe |
|---|---|---|---|---|---|---|---|---|
| Capture | Overlay + session | Share sheet | Share sheet | Manual bookmark | Share / highlight | Agents watch screen | OS screenshot | Always-on desktop |
| Account | Not required | Required for cloud | Cloud | Optional | Account | Local + Plus | Google | Local / hosted |
| On-device default | Yes | No | No | n/a | No | Yes | Was; now hybrid cloud | Yes |
| Spoken idea | P0 | No | No | No | No | Different job | Notes | Transcripts of screen |
| India INR list | ₹149/mo Pro target | US-priced | ₹99–199/mo | USD | USD | $5/mo Plus | Bundled | High USD |
| FOSS | Apache-2.0 intent | No | No | No | No | Yes | No | Relicensed 2026 |
| Teams in V1 | No | n/a | n/a | Yes (paid) | n/a | n/a | No | Business SKU |

Do not claim “the only” anything. Claim the **combination** in §7.4.

### 22.3 Launch channels

Sequence (aligned with marketing research §6): **GitHub Releases + Obtainium → IzzyOnDroid → Show HN / Reddit / Lemmy → F-Droid RFP (parallel) → Play after S8 → Chrome Web Store with Phase 2 → Product Hunt when the product is tryable, not as the plan.** ReciMe’s Product Hunt launch was **91 upvotes, #21 on 12 Oct 2023**, then 1M+ Play — PH was neither necessary nor sufficient ([hunted.space ReciMe](https://www.hunted.space/product/recime-2/launches/recime-2)).

#### Product Hunt (2025–26 mechanics)

Official prep: schedule for **12:01am PST** so the listing gets the full 24-hour Pacific cycle; self-hunt is allowed and **paying a hunter is discouraged**; company accounts are the wrong vehicle ([Prepare for your launch](https://www.producthunt.com/launch/preparing-for-launch), page seen **17 Oct 2026**). Launch Guide: free to launch; **cannot ask for upvotes**; live products only ([producthunt.com/launch](https://www.producthunt.com/launch)).

Featuring is **editorial**. Criteria: useful, novel, high craft, creative. **Waitlisted products are not featured** (unless immediate access is provided); also excluded: directories, templates, boilerplates, courses, services ([Featuring guidelines](https://help.producthunt.com/en/articles/9883485-product-hunt-featuring-guidelines), date not shown; help-center “updated over 5 months ago” at research time — **Unverified** as a last-updated stamp). Relaunch: **six months** plus a significant update; pricing/UI tweaks do not count ([AgentLaunch citing PH help updated 7 Jul 2026](https://www.agentlaun.ch/blogs/product-hunt-relaunch-rules), 5 Oct 2026).

Nicheloom (14 Oct 2026) reported **28,255 launches / 35 days** and ~**3.8% featured** — treat as third-party. ReciMe later won **RevenueCat Shippies “Best onboarding”** (announced on X around **14 Oct 2025** — qualitative, not a PH mechanic).

**OBZRAi PH rule:** launch only with a **tryable APK or CWS listing**, not a waitlist. Maker comment in the first minutes. Ask for visits and comments, never votes. India timezone: Basant staffs the 12:01am PT window (~12:31pm IST in October) or schedules and stays on the thread.

#### Chrome Web Store featuring / discovery

Editors curate home merchandising; **publishers cannot pay** for placement ([CWS discovery](https://developer.chrome.com/docs/webstore/discovery), 21 Mar 2022). Featured **badge** is sunsetting (§21.2). Discovery levers that still exist: listing quality, category, ratings (refreshed to weight recent reviews — Chrome blog 20 Aug 2026), screenshots, privacy transparency. **Do not** buy install traffic that looks like deceptive installation (policy 10 Jul 2024).

#### Google Play

Publication after S8, FGS demo video, Data safety, permission justifications (§17.8, §20.4). **No paid featuring.** 13 Oct 2026 Play surface notes (Ask Play comparisons, Game Finder, Gemini discovery) reward **store-listing quality**; they are not a paid slot. India alternative billing: Play fee **reduced by 4%** when the user pays the alternative system ([Play Help 13306652](https://support.google.com/googleplay/android-developer/answer/13306652)). Fee split for US/UK/EEA from **30 Jun 2026**: **10%** service on first $1M and on auto-renewing subscriptions, plus **5%** billing fee if using Play Billing ([Android Developers Blog](https://android-developers.googleblog.com/2026/06/play-expanded-billing.html), seen 16 Oct 2026). **Rest of World including India: 30 Sep 2027**; RoW billing-fee % **not announced** (existing Unverified #9). Play’s own figure that **97% of developers distribute at no charge** appears in secondary 2026 fee explainers ([ecorpit, 19 Jul 2026](https://ecorpit.com/google-play-service-fee-split-2026-app-economics/)) — treat as Google-stated via secondary.

#### F-Droid, IzzyOnDroid, Obtainium

- **IzzyOnDroid:** **2,149,476** downloads in Sep 2026 ([monthly report](https://codeberg.org/IzzyOnDroid/iod-monthly-report/src/branch/main/reports/monthly_report_2026-09.md), generated 8 Oct 2026). Inclusion via Codeberg issue; APKs from **tagged GitHub/Codeberg releases**, typically ~24h. Faster than f-droid.org for this ICP.
- **F-Droid:** client **2.0** announced **24 Sep 2026** ([blog](https://f-droid.org/2026/09/24/f-droid-2.0-a-new-chapter-for-android-freedom.html)). Inclusion Policy: entire app **FLOSS**, no Play Services / Firebase / Crashlytics, FLOSS toolchain ([Inclusion Policy](https://f-droid.org/docs/Inclusion_Policy/), seen 16 Oct 2026). TWIF **16 Oct 2026**: **299** apps added in ~4 weeks and “review process is long” ([TWIF](https://fdroid.gitlab.io/fdroid-website/2026/10/16/twif.html)). File an RFP/MR **in parallel**; do not wait for it to launch.
- **Obtainium:** GitHub updater. Star counts in the wild: **~20,370** (hysenlabs, 12 Oct 2026) / ~20.5k GitHub topics (16 Oct 2026). A **35-star** bar for config inclusion was claimed on X by @arferrari (**10 Oct 2026**) — **Unverified** as Obtainium project policy. Ship an Obtainium JSON from day 0 regardless.

#### Show HN

Official guidelines: something people can **play with**; **no** blog posts, sign-up pages, newsletters, lists; make it easy to try, **ideally without signups or emails**; personally worked on; no landing-page-only; upgrades generally not enough ([showhn.html](https://news.ycombinator.com/showhn.html), date not shown). Dang restated waitlists are against the rules (secondary: [swanbase, 25 Sep 2026](https://swanbase.co/en/blog/launch-product-us-hacker-news-product-hunt-reddit/) citing 27 Jul 2026). **Do not ask for upvotes.** Title shape: `Show HN: OBZRAi – local-first overlay that files screen moments into a research hub`. Link the APK + source. Honest limitations in the first paragraph.

#### Reddit / Lemmy

Post when there is a public APK: **r/androidapps, r/fossdroid, r/selfhosted, r/LocalLLaMA, r/degoogle, r/GrapheneOS, r/chrome_extensions** (extension later). Lemmy: `degoogle`, `android`, `selfhosted`. Graphene/FOSS reviewer **before** r/fossdroid (R6). No astroturf; lead with limitations.

#### X, build in public

Owner account (after IP India clearance of the name). Cadence: weekly build notes, one demo clip, one honest fail (FLAG_SECURE, IG audio). Do not run ads that say “download Reels.” Qualitative creator proof on X is not a census.

#### Indian and global tech creators

- **Hitesh Choudhary** (@Hiteshdotcom): X bio self-reports **2 YT channels (1M & 800k)** (bio seen on posts **20–21 Sep 2026**, e.g. [status/2101983978194653589](https://x.com/Hiteshdotcom/status/2101983978194653589)). Seeding here is a **reach option**, not a booked deal.
- **Sanskar Tiwari** (@IndianAppGuy): Chrome/Notion tutorial history; YouTube count in older listings — treat current subscriber count as **Unverified** unless re-scraped at outreach.
- **Suhas Sumukh** (localhost India) and similar indie-dev podcasts: good ICP overlap.
- **BhaiFinder** Hinglish Shorts ran the right hook at **26–62 views** (marketing research, 17 Oct 2026) — the hook is right, the channel is empty. Room for a **technical** demo.

**Recommendation:** seed **5** Hinglish/English **dev** creators (tools, Claude Code, GitHub, local LLM) with APK + talking points. Mass tech YouTubers only if a 20-second visual works without niche knowledge. Conversion from creator seeding is **Unverified** (no OBZRAi data).

### 22.4 Pricing tiers (GTM view)

Product model stays **§8.4**: open-core, local-first free forever, BYO key, modest Pro. This subsection is how that model is **sold**.

| Tier | INR | USD | What the customer hears | Channel |
|---|---|---|---|---|
| **Free / FOSS** | ₹0 | $0 | Capture, overlay, OCR, on-device ASR, Today, tags, export. No account. | GitHub, Izzy, F-Droid, Play, CWS |
| **BYO key** | ₹0 platform | $0 | Paste OpenAI / Anthropic / xAI / Gemini / others. Key in OS keystore. | All |
| **Pro (hosted)** | **₹149/mo or ₹1,499/yr** | **$4.99/mo or $39/yr** | Optional account, E2E sync relay, hosted AI quota, priority support. Local app remains usable if Pro lapses. | Play IAP (India until 2027 on 15/30% card); Stripe on web/CWS; India alt-billing −4% |
| **Lifetime / BYO-key forever** | **P2 optional:** ~2–3 years of Pro, target **₹3,999** or **$79–99** | One-time; still FOSS core | Not V1. Willingness to pay a lifetime SKU for OBZRAi is **Unverified** |

**Comparables (do not copy blindly):** BhaiFinder ₹99/₹199 per month ([Stork, 30 Sep 2026](https://www.stork.ai/en/bhaifinder)); Raindrop Pro **$3/mo or $28/yr**, no lifetime in the 2026 roundup ([ContextBolt, 10 Sep 2026](https://contextbolt.com/blog/raindrop-pricing/); savethisone 16 Sep 2026); Readwise **$9.99/mo** yearly ([Marqly, 6 Oct 2026](https://www.marqly.com/blog/readwise-reader-review-2026)); Observer AI Plus **$5/mo**; ReciMe ~$39.99/yr in one writeup (**Unverified** exact SKUs); Screenpipe aggregator **$21/$42** (**Unverified** at checkout).

**Play payment reality:** India stays on the legacy 15%/30% structure until **30 Sep 2027**; US/UK/EEA already on 10%+5% from **30 Jun 2026** (§8.3). Digital goods on Play use Play Billing unless alternative billing is declared.

**CWS payment reality:** Google is not the seller; native CWS payments deprecated; declare **Contains in-app purchases**; Stripe/ExtensionPay (~5% + Stripe fees — ExtensionPay’s exact take is vendor-stated, confirm at integration). Never paywall Save.

**Not V1:** ads, paid APK as the only model, seat-based teams.

### 22.5 India-plus-global angle

- **One product, two price lists, two trust stories.** INR near BhaiFinder without dump-pricing; USD near Raindrop, far below always-on desktop.
- **Distribution is global on day 0** (GitHub). Play listing: India + English-speaking markets after S8. CWS: India is a supported developer country (register page, 13 Feb 2024).
- **Copy:** English default; Hindi UI; Hinglish in demos (“Reel pe dikha repo, Saved mein milta nahi”).
- **Legal:** DPDP Rules 2025 (Gazette 13 Nov 2025) before any account; India-region option for the sync relay (open question 20).
- **Do not** run India ads on Instagram downloading. Demo Chrome + YouTube tutorial + GitHub.

### 22.6 Early-traction tactics

| Tactic | How | When |
|---|---|---|
| Waitlist | Email-only on the marketing site **before** a public APK. **Never** use a waitlist as a PH or Show HN artifact | Pre-launch only |
| Design partners | 8–12 Indian/global developers who install nightlies and file GitHub issues. Written consent for quotes | Phase 0–1 |
| Referral | P2. FOSS users hate dark referral paywalls. If used: extra hosted-AI quota, not unlock of capture | After Pro exists |
| Templates | Persona starter boards (developer, student, accountant, designer) — **PROPOSAL FR-088**. PH does not feature “templates” as a launch type; ship them **inside** the app | Phase 2 Hub |
| Creator seeding | 5 dev creators, APK + 20s talking points, no paid vote rings | Launch week |
| Open-source contributors | CONTRIBUTING.md, good-first-issues, Apache-2.0 intent, CODE_OF_CONDUCT. **Target:** ≥ 3 external PRs merged in 90 days; ≥ 1 non-owner maintainer triaging issues by Phase 2. FOSS United meetups (Bengaluru last-Saturday pattern, Delhi 14 Nov 2026 — [fossunited.org/c/delhi/nov-26](https://fossunited.org/c/delhi/nov-26); [@fossdot, 13 Oct 2026](https://x.com/fossdot/status/2109935155830022158)) are contributor funnels, not ad buys. IndiaFOSS 2026 already ran **26–27 Sep 2026** at NIMHANS Bengaluru ([fossunited.org/c/indiafoss](https://www.fossunited.org/c/indiafoss), retrospective 5 Oct 2026) — next IndiaFOSS is the 2027 cycle; use city meetups in the meantime. | From public GitHub |
| SEO / AEO | Answer-first pages: “How to save a YouTube timestamp privately”, “Pixel Screenshots alternative that is not Pixel-only”, “local-first research hub”. Allow `OAI-SearchBot` if the owner wants citation in AI answers. YouTube is heavy on Google AI surfaces — the demo video is an AEO asset. Citations are engine-specific; do not promise ChatGPT citations | From public README onward |

### 22.7 Launch milestones and metrics (**targets**)

All numbers below are **targets**, not commitments. Retention comparables: MWM catalog (US, state of **April 2026**) median **D1 27.3% / D7 9.2% / D30 3.9%**; Productivity & Tools **D7 8.9% / D30 4.5%** ([MWM glossary](https://mwm.ai/glossary/retention); [MWM guide, 9 Jun 2026](https://mwm.ai/guides/how-to-improve-app-retention)). Prooflytics **subscription productivity** D7 **30–45%**, D30 **18–28%** ([Prooflytics, 21 Apr 2026](https://prooflytics.io/blog/d7-d30-retention-benchmarks-by-app-category)) — higher because those apps are already paid cohorts. OBZRAi is closer to a **utility + productivity** hybrid; use MWM Productivity as the **floor** and Prooflytics as an **aspirational paid-cohort** band.

Existing product activation targets (§4.2) stay: session start **70%**, manual save **50%**, Today **40%**.

| Window | Milestone | Metric targets (labelled) |
|---|---|---|
| **Pre-launch** | Public GitHub, privacy/threat-model page, Obtainium JSON, Izzy issue, F-Droid RFP, waitlist | Waitlist **500–1,500** emails (indie FOSS; no census). Design partners **8–12**. IP India search filed |
| **Launch week** | GitHub Release APK; X thread; r/fossdroid + r/androidapps + r/LocalLLaMA; Show HN the same week as a tryable APK | Activation: session start **≥ 70%** of completers of onboarding. First save **≥ 50%**. Show HN: tryable, no waitlist. Do not target a PH rank this week unless CWS/Play is also live |
| **30 days** | Izzy live; Obtainium one-tap; changelog; known-broken-apps list | D7 retention **≥ 12%** (above MWM Productivity median 8.9%, still a target). Saves per weekly active user **≥ 5**. GitHub issues from real users **≥ 10**. Play packet submitted or FOSS-only decision recorded |
| **60 days** | Play listing **or** public FOSS-only post-mortem; first Hindi UI pass | D30 **≥ 6%** (above MWM Productivity 4.5% median; far below subscription-productivity 18–28%). Waitlist→install **≥ 25%** if a waitlist existed |
| **90 days** | Phase 2 extension unlisted CWS testers **or** date slipped with a written reason | Weekly active / install **≥ 15%** among 30-day survivors. Free→Pro **2–5% of weekly actives after Pro exists** — **target**, no independent OBZRAi conversion data. ReciMe/BhaiFinder prove *willingness to pay for rescue of saves*, not this conversion rate |

**North star remains §4.1** (saves that get reopened). Guardrails §4.4 still apply (no silent upload, no Play policy fail).

### 22.8 Channel calendar (recommendation)

| When | Channel |
|---|---|
| −7 to 0 | Rename clearance; GitHub public; privacy page; Izzy issue; Obtainium; F-Droid RFP; waitlist |
| Day 1 | GitHub Release + checksums; X; Reddit/Lemmy |
| Day 2–3 | Show HN with APK |
| Day 4–10 | 8–12 Reels/Shorts (technical, OS chip visible); 5 creator seeds |
| Week 1–2 | Izzy announcement |
| Day 14–21 | Play packet |
| Phase 2 ready | CWS unlisted testers → public; then PH with a tryable extension **and** APK |
| +6 months | PH relaunch only if the Hub/PWA is a significant update |

### 22.9 Step-by-step launch checklists (v2.1)

Each checklist is a **runbook**. Dates inside a phase are relative to that phase’s exit, not calendar promises. Confirm live help pages the week of launch; specs below were checked **17 Oct 2026**.

#### A. Product Hunt

Sources: [Preparing for launch](https://www.producthunt.com/launch/preparing-for-launch) (seen 17 Oct 2026); [producthunt.com/launch](https://www.producthunt.com/launch); featuring guidelines [help 9883485](https://help.producthunt.com/en/articles/9883485-product-hunt-featuring-guidelines) (date not shown); asset sizes from PH launch guide via [Submitator, 3 Aug 2026](https://submitator.com/blog/product-hunt-launch-assets).

**T−14 to T−7**

1. Confirm the product is **tryable** (public APK and/or CWS). Waitlists are not featured.
2. Maker account is a **personal** account; company accounts are the wrong vehicle.
3. Write **name** (name only, no emoji) and **tagline ≤ 60 characters**. Draft: `Session-only observer that files screen moments into a private research hub` (count before paste).
4. Gallery: **≥ 2 images at 1270×760**, first image is the social preview. Thumbnail **240×240**. Each image **< 3 MB** (Submitator citing PH, 3 Aug 2026).
5. Optional YouTube demo, **public** URL, OS capture chip visible, docs/YouTube — never a downloader.
6. First-comment draft (how we built it, what is local, what is broken). 70% of Products of the Day/Week/Month had a maker first comment (PH launch guide via Submitator, 3 Aug 2026).
7. Schedule for **12:01am PST** so the listing gets the full 24-hour Pacific cycle.

**Launch day (India: ~12:31pm IST in October)**

8. Publish on schedule. Paste the first comment in the first minutes.
9. Staff the thread for 24h. Ask for **visits and comments**. Do not ask for upvotes (PH Launch Guide).
10. Share the PH URL on X and in the GitHub Release notes. No vote rings.

**After**

11. Relaunch only after **six months** plus a significant update (AgentLaunch citing PH help updated 7 Jul 2026, [5 Oct 2026](https://www.agentlaun.ch/blogs/product-hunt-relaunch-rules)).

**When:** Phase 2 ready (tryable extension **and** APK), per §22.8. Phase 1 APK-only is allowed if visually complete; recommendation remains “whichever is first tryable.”

#### B. Chrome Web Store

Sources: CWS register (13 Feb 2024); [review updates 20 Aug 2026](https://developer.chrome.com/blog/cws-review-updates-2026); [quality guidelines](https://developer.chrome.com/docs/webstore/program-policies/quality-guidelines) (10 Jul 2024); listing template [CHROMEWEBSTORE.md](https://raw.githubusercontent.com/GoogleChrome/modern-web-guidance-src/main/skills-src/chrome-extensions/references/webstore/chromewebstore-template.md); image sizes [CWS Kit, Apr 2026](https://cwskit.khanakia.com/chrome-web-store-image-sizes); Kromio submit guide (updated Jul 2026, [28 Sep 2026 page](https://kromio.ai/learn/submit-chrome-web-store)).

**T−21**

1. Google account with **2-Step Verification** (required to publish, CWS API docs 8 Oct 2026).
2. Pay the **one-time registration fee** (India is a supported country). Official page omits the dollar amount; secondaries still quote **US$5** — confirm at checkout (Unverified #18).
3. One extension slot is enough (default two slots as of 20 Aug 2026).

**Listing copy**

4. **Name ≤ 75 characters**, must match `manifest.json` `name`. Draft: `OBZRAi — Save the page you're looking at into a private library`.
5. **Short description ≤ 132 characters** (search tile). Draft: `Session-only capture of the tab you are on. Local Research Hub, spoken ideas, honest sources. No account required.`
6. Detailed description: one-sentence purpose first; then how to use; then privacy/permissions; then support. CWS strips Markdown (template note).
7. Single-purpose sentence (FR-071): `Save useful moments from the page you are looking at into a private local research library.`
8. Privacy policy URL = marketing `/privacy` (FR-094). Fill every Privacy-tab justification (FR-070).
9. Declare **Contains in-app purchases** if Pro is wired; Google is not the seller.

**Assets**

10. Store icon **128×128 PNG** (required). Also 16/48 in the package.
11. **≥ 1 screenshot** at **1280×800** (preferred) or **640×400**; up to 5. Show side panel Today, Save on a docs page, empty state. Full-bleed, no phone frames ([Versely, 24 Aug 2026](https://www.versely.studio/blog/chrome-web-store-listing-is-the-screenshot-row)).
12. Small promo tile **440×280** (recommended / required on some surfaces — Kromio Jul 2026). Marquee **1400×560** optional.
13. 30s listing video: Save on documentation, OS/browser indicator visible.

**Submit**

14. Unlisted testers first (Phase 2 exit), then public.
15. No `<all_urls>` unless S10 proves it; prefer `activeTab`.
16. Do not plan a Featured-badge campaign (sunset later 2026).

#### C. Google Play listing + ASO

Sources: [Best practices for your store listing](https://support.google.com/googleplay/android-developer/answer/13393723) (title 30 / short 80 / full 4000); [Add preview assets](https://support.google.com/googleplay/android-developer/answer/9866151) (min 2 screenshots, up to 8 per device type, 320–3840 px, long side ≤ 2× short); feature graphic 1024×500 and icon 512×512 via Play Console Help as restated by [StoreShot, 20 Sep 2026](https://storeshot.ai/blog/play-store-listing-assets-checklist/) and [Ecranify, 16 Oct 2026](https://ecranify.com/guides/google-play-feature-graphic) (checked against Play Help Oct 2026). Play indexes title, short description, and full description ([AppDrift, 7 Oct 2026 / updated 11 Oct 2026](https://appdrift.co/blog/google-play-seo)).

**Packet (after S8)**

1. Play Console app created; Data safety form; MediaProjection FGS **demo video** of a tutorial site (§17.8).
2. **Title ≤ 30 characters.** Draft: `OBZRAi Research Observer` (24 chars) or `OBZRAi: Screen Research Hub`.
3. **Short description ≤ 80 characters.** Draft: `Session-only screen observer. Local Hub, spoken ideas, honest sources.` (count before paste).
4. **Full description ≤ 4,000 characters.** First ~167 characters carry the promise (what “Read more” truncates — SEM Nexus 29 Jun 2026). Sections: what it is, how a session works, local-first, what it does not do (downloaders, always-on), permissions, FOSS link.
5. **ASO keywords (targets, English + Hindi listing later):** `screen capture notes`, `research bookmark`, `YouTube timestamp save`, `local screenshot library`, `privacy screen memory`, `overlay notes`, `GST research notes` (accountant is not the launch ad audience; keep the phrase inside the body, not the title). Repeat primary terms 2–3 times in the full description; skip stuffing.
6. Icon **512×512** 32-bit PNG with alpha, ≤ 1024 KB. Feature graphic **exactly 1024×500**, JPEG or 24-bit PNG, **no alpha**.
7. Phone screenshots **2–8**; for featuring, ≥ 4 at 1080+ (1080×1920 or 1920×1080). Sequence: overlay on a docs page → Save → Today → item detail with provenance → Trust center.
8. Hindi listing strings (FR-076) before the India listing goes public.
9. Trader/non-trader, privacy URL, Data safety. Alternative billing −4% only if declared.
10. Submit. Treat a MediaProjection+overlay rejection as a distribution incident (R1); FOSS channels stay live.

#### D. GitHub README + Show HN

Sources: [showhn.html](https://news.ycombinator.com/showhn.html) (date not shown, fetched 17 Oct 2026).

**README (Phase 1, day 0 of public repo)**

1. One-sentence purpose; screenshot of overlay + Today.
2. Honest limitations: DRM/`FLAG_SECURE`, audio policy, session-only, no downloader.
3. Install: GitHub Release APK + checksums, Obtainium JSON, Izzy status, Play/CWS when live.
4. Build from source; license; privacy pointer; CONTRIBUTING.md; good-first-issues.
5. Changelog link. Known-broken-apps list.

**Show HN**

6. Title: `Show HN: OBZRAi – local-first overlay that files screen moments into a research hub`.
7. Link the **tryable APK + source**. Make it easy to try **without signup or email** (official guideline).
8. First paragraph: what it does, what the OS chip means, what is broken.
9. Personally worked on; be in the thread. Do not ask for upvotes. New-account restrictions: confirm `/showlim` and newsguidelines before posting (Unverified #29).
10. Timing: Day 2–3 after GitHub Release (§22.8). Never a waitlist or landing-page-only Show HN.

#### E. Reddit / Lemmy

1. Graphene/FOSS reviewer **before** r/fossdroid (R6).
2. When a public APK exists, post: **r/androidapps, r/fossdroid, r/selfhosted, r/LocalLLaMA, r/degoogle, r/GrapheneOS**. Extension later: **r/chrome_extensions**.
3. Lemmy: `degoogle`, `android`, `selfhosted`.
4. Title is the job, not the brand. Lead with limitations. No astroturf, no vote brigades.
5. Stay for comments the first 48h.

#### F. X (build in public)

1. Owner account after IP India clearance of the name.
2. Pin: 20s demo + APK + privacy link.
3. Cadence: weekly build notes, one demo clip, one honest fail (`FLAG_SECURE`, IG audio).
4. Launch-week thread: Release notes, then PH/Show HN links as they go live.
5. Do not run ads that say “download Reels.”

#### G. Indian dev and creator communities

1. **FOSS United Bengaluru** — meetups last Saturday 2–5 pm ([fossunited.org/c/bengaluru](https://fossunited.org/c/bengaluru)); next listed October meetup **24 Oct 2026**, Samagata Foundation, Church Street ([fossunited.org](https://fossunited.org/), seen 17 Oct 2026).
2. **FOSS United Delhi/NCR** — **14 Nov 2026**, 3:30–6:00 pm, Essentia Sector 62 Noida; CFP until 5 Nov ([fossunited.org/c/delhi/nov-26](https://fossunited.org/c/delhi/nov-26); [@fossdot, 13 Oct 2026](https://x.com/fossdot/status/2109935155830022158)).
3. Talk proposal: session-only capture, local Hub, Play overlay restricted-settings — a demo with the OS chip on.
4. Hasgeek / local.city / indie-hackers India Slack or Discord if the owner already has a seat; no cold spam.
5. IndiaFOSS 2026 (26–27 Sep, NIMHANS) has **concluded**; next festival is the 2027 cycle. Use city chapters until then.

#### H. YouTube creators

1. Seed **5** mid-size **dev** creators (tools, Claude Code, GitHub, local LLM). Mass tech YouTubers only if a 20-second visual works without niche knowledge.
2. Kit: APK + 20s talking points + overlay on a docs page + “this is not a downloader.”
3. Named reach options, not booked deals: Hitesh Choudhary (@Hiteshdotcom, self-reported 1M & 800k YT in bio, seen 20–21 Sep 2026); Sanskar Tiwari / IndianAppGuy (subscriber count **Unverified** — re-scrape); Suhas Sumukh / localhost India.
4. No paid vote rings. Conversion from seeding is **Unverified** (no OBZRAi data).
5. Owner-channel demo: OS chip visible; YouTube tutorial + GitHub; Hindi/Hinglish optional on a second cut.

### 22.10 Launch timeline aligned to Phases 0–4 (v2.1)

All dates inside a phase are **relative**. Android S1–S4 remain the first engineering gate.

| Phase | Product that can be sold | GTM motion | Continuity the user feels |
|---|---|---|---|
| **0 Feasibility** | Nothing public except a privacy-stub if needed | IP India search; GitHub private; design partners 8–12 | None |
| **1 Android MVP** | Signed APK: GitHub Releases, Obtainium, Izzy issue, F-Droid RFP | Marketing site live (FR-094); waitlist **off** the day the APK is public; Show HN + Reddit + X; Play packet after S8 | Encrypted export/import (FR-097). Phone-only Hub. |
| **2 Browser extension** | CWS unlisted → public; side-panel Hub | CWS checklist §22.9-B; PH when tryable; r/chrome_extensions; creator second wave | Side-panel Hub (option **c**). D7-B/C only if owner accepted. |
| **3 Desktop** | macOS first, then Windows, then Linux | GitHub Releases; no new store required | Same Hub UI; optional local bridge (option **b**) |
| **4 Sync / PWA** | Optional account, E2E full-res, standalone web client (option **a**), read-only shares | Pro SKU live (Play IAP / Stripe); PH relaunch only if the PWA is a significant update | FR-047 / FR-064 / FR-087 / FR-105 |

Pricing stays **§8.4 / §22.4**: Free local forever, BYO key, Pro ₹149 / $4.99 for hosted E2E relay + hosted AI. If D7-B uses only user-owned storage, Pro is not required for thin sync; the relay remains a Pro feature.

## 23. Proposed features for strength and defensibility

Items in this section are **PROPOSAL**. They are not Android MVP scope unless an ID is already P0 in §13. Each item states priority (**P0 / P1 / P2 / Research**), rationale, effort (**S / M / L**), **phase**, risk, and **moat type** (data / network / workflow / trust / integration). v2.0 IDs are **FR-079–FR-093**. v2.1 adds **FR-098–FR-102**. Owner may promote or drop any proposal without revising the locked overlay or Phase 0 gates. Continuity FRs FR-097 / FR-103 / FR-104 / FR-106 are specified in §24 (they are product requirements or D7 options, not strength proposals).

### 23.1 How to read this section

- **P0** here means “do not ship the named surface without it” — none of these displace Android S1–S4.
- **P1** means “after the named phase’s exit, this is the next strength bet.”
- **P2** means optional.
- **Research** means a spike before a build estimate.
- Effort: **S** ~1–3 eng-days, **M** ~1–2 weeks, **L** ~3+ weeks, one engineer, excluding the spike.
- Moat types stack; the honest V1 moat is **workflow + trust**, not network effects (team-free).

### 23.2 Proposals

#### FR-079 — Daily AI research digest — **PROPOSAL**

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | M |
| **Risk** | Cloud cost; hallucinated recap; “AI slop” email unread |
| **Moat** | Workflow + data |
| **Rationale** | Today (FR-035/059) is a feed. A digest turns a day of saves into a one-screen recap with citations back to items — the habit loop ReciMe has for recipes and Readwise has for highlights. On-device summary default; cloud per D2. |
| **Acceptance sketch** | User can generate “Today’s digest” offline with the on-device model; each bullet links to an item id; empty day produces no fake content. Optional evening notification is off by default. |
| **Phase** | After Phase 1d Hub; ships with S17 |

#### FR-080 — Semantic search across visual + voice — **PROPOSAL** (extends FR-024)

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | L |
| **Risk** | On-device embedding RAM/thermal; multilingual (EN+HI) quality |
| **Moat** | Data + workflow |
| **Rationale** | Keyword search misses “that rust crate from the blue thumbnail.” Combining frame embeddings, OCR, and voice-idea transcripts is the library nobody else has for *session captures*. |
| **Acceptance sketch** | Query in EN or Hindi returns cited items; explicit empty state; embeddings never leave the device unless the user opts into a cloud index (off by default). |
| **Phase** | P1 after S17; not Android MVP |

#### FR-081 — Auto-citations and source-link recovery — **PROPOSAL**

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | M |
| **Risk** | Fabricated URLs (forbidden by FR-015); ToS if we scrape |
| **Moat** | Trust + workflow |
| **Rationale** | Provenance is already P0, but many Reels have no URL. Recovery must be **honest**: QR/on-screen URL via OCR, user-shared link, or “unknown.” A citation panel on export (Markdown footnotes) makes the Hub usable in real research. |
| **Acceptance sketch** | Export of a day includes footnotes only for known URLs; OCR-detected URLs are labelled `source_url_origin = ocr` and user-confirmable; never invent a GitHub URL from a repo name. |
| **Phase** | Phase 1d for OCR URLs; Phase 2 for clipper URLs |

#### FR-082 — Research sessions / projects — **PROPOSAL**

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | M |
| **Risk** | IA clutter; overlap with boards (FR-060) |
| **Moat** | Workflow |
| **Rationale** | A “session” is the capture window; a “project” is a named research effort across days (e.g. “Rust async,” “GST filing 2026”). Projects are a **label on items**, implemented as a first-class collection type, not a second database. Distinct from team workspaces. |
| **Acceptance sketch** | User starts/stops a project; new saves inherit the active project; Today can filter to one project; deleting a project does not delete items unless confirmed. |
| **Phase** | Phase 2 Hub |

#### FR-083 — Spaced-repetition review — **PROPOSAL**

| | |
|---|---|
| **Priority** | P2 |
| **Effort** | M |
| **Risk** | Turns a library into a chore app; retention theatre |
| **Moat** | Workflow |
| **Rationale** | Learners who save tutorials forget them (the graveyard problem). Optional SRS on items the user flags “learn this” — not default on every save. |
| **Acceptance sketch** | Opt-in per item; due queue in Hub; skip never penalizes; no streak dark patterns. |
| **Phase** | After digest (FR-079) exists |

#### FR-084 — Export to Readwise — **PROPOSAL**

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | S |
| **Risk** | API change; user token leakage |
| **Moat** | Integration |
| **Rationale** | Readwise is the power-reader habit graph. Official export API is documented as `GET https://readwise.io/api/v2/export/` (Readwise v2 docs, date not shown / live Oct 2026). OBZRAi should **push highlights** (create) rather than scrape export. Complements Notion/Obsidian (FR-062). |
| **Acceptance sketch** | User pastes a Readwise access token; selected items become highlights with source URL when known; token in OS keystore; one-click revoke. |
| **Phase** | Phase 2–4 with other exporters |

#### FR-085 — Local LLM on-device Q&A — **PROPOSAL** (extends FR-043 / FR-045)

| | |
|---|---|
| **Priority** | P1 (assistant) / Research (small models on midrange) |
| **Effort** | L |
| **Risk** | Thermal, APK size, Hindi quality, “it made that up” |
| **Moat** | Trust + workflow |
| **Rationale** | “What did I see yesterday about GST?” with **citations to item ids** is the assistant job. On-device default is the privacy story versus Recall.it / ReciMe cloud. |
| **Acceptance sketch** | Answers cite item ids or say “I don’t have that”; model download is explicit; airplane-mode demo in marketing is true. |
| **Phase** | S16–S17; not MVP |

#### FR-086 — MCP server so AI assistants can query the Hub — **PROPOSAL**

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | M (stdio) / L (remote) |
| **Risk** | Prompt injection via saved page text; accidental plaintext exposure |
| **Moat** | Integration + workflow |
| **Rationale** | MCP spec **2026-07-28** is the current revision: stateless core, no `initialize` handshake, stdio still the local transport ([MCP blog](https://blog.modelcontextprotocol.io/posts/2026-07-28/), 28 Jul 2026). A **local stdio MCP server** that tools `search_items`, `get_item`, `list_today` against the on-device Hub lets Cursor/Claude/other clients query *the user’s* library without a cloud index. Remote MCP is out of V1. Breadth of on-device client support for 2026-07-28 is **Unverified**. |
| **Acceptance sketch** | `obzrai-mcp` stdio; read-only by default; write tools (save/tag) require a config flag; never starts a network listener in V1; README threat model for prompt injection. |
| **Phase** | After Phase 1 Hub schema is stable |

#### FR-087 — Shareable read-only boards — **PROPOSAL**

| | |
|---|---|
| **Priority** | P2 |
| **Effort** | M |
| **Risk** | Accidental PII leak; DPDP if URLs are public |
| **Moat** | Network (weak) + workflow |
| **Rationale** | V1 is team-free (FR-063). A later **signed, expiring, read-only link** to one board (no accounts for viewers) is the smallest share primitive. Not live collab. |
| **Acceptance sketch** | User explicitly publishes **one collection**. Ciphertext blob; **key in the URL fragment** (`#k=`, Excalidraw pattern, 21 Mar 2020). Default expiry **7 days**, max **31 days** (Bitwarden Send, 16 Sep 2026). One-tap **revoke** tombstones the blob (Proton Drive stop-sharing, 16 Oct 2026). Optional password. Redacted items stay redacted. `robots noindex`. Viewers need no account. No comments, no presence, no edit. S23 before ship. FR-105 is the revoke/expiry contract. |
| **Phase** | **P2, Phase 4** by default (needs a ciphertext store). Phase 2 only if D7-B already has user-owned or relay storage. Still team-free. |

#### FR-088 — Templates by persona — **PROPOSAL**

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | S |
| **Risk** | PH “templates” exclusion if launched as a template pack; clutter |
| **Moat** | Workflow |
| **Rationale** | Developer / student / accountant / designer starter boards + tag sets reduce empty-state friction (FR-073). Accountant template never auto-opts into Tally capture (D6 still required). |
| **Acceptance sketch** | First-run picker, skippable; templates are JSON in the repo; user can reset. |
| **Phase** | Phase 2 Hub; also Android empty state |

#### FR-089 — Duplicate detection — **PROPOSAL** (extends FR-023)

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | M |
| **Risk** | Silent merge data loss |
| **Moat** | Data + workflow |
| **Rationale** | Reels loop; users Save twice. Perceptual hash + same-URL grouping, **visible** not silent (FR-023 already). Web Hub should surface a “possible duplicate” chip. |
| **Acceptance sketch** | Group, do not delete; user can merge; voice notes on duplicates are preserved. |
| **Phase** | P1 after library exists |

#### FR-090 — OCR of code into runnable snippets — **PROPOSAL**

| | |
|---|---|
| **Priority** | P2 |
| **Effort** | L |
| **Risk** | Hallucinated code; CWS “unrelated functionality” if we ship a runner |
| **Moat** | Workflow |
| **Rationale** | Devs save CLI/code from videos. OCR → **copyable fenced Markdown**, language tagged when detected. **Not an in-app executor** (security + single-purpose). |
| **Acceptance sketch** | Item detail shows a fenced block; copy button; user warned OCR may be wrong; no `eval`. |
| **Phase** | Research spike on code-OCR accuracy, then P2 |

#### FR-091 — YouTube timestamp deep links — **PROPOSAL** (pairs with FR-068)

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | S |
| **Risk** | Shorts ignore `t=` (secondary: some 2026 guides say Shorts always start at 0 — **Unverified** as YouTube official) |
| **Moat** | Integration + workflow |
| **Rationale** | “Open source” must jump to the moment. Store canonical `watch?v=&t=` plus `youtu.be?t=`. |
| **Acceptance sketch** | Open source on a YouTube item launches at stored seconds; missing time opens at 0 and does not lie. |
| **Phase** | Phase 2 with FR-068 |

#### FR-092 — Hindi / Hinglish voice notes — **PROPOSAL** (pairs with FR-076, S16)

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | M |
| **Risk** | Whisper-tiny Hindi WER; code-switching |
| **Moat** | Data + trust (India) |
| **Rationale** | Primary ICP includes Indian developers who think in Hinglish. S16 already requires EN+HI samples. Productize: language tag on the voice note, search across both. |
| **Acceptance sketch** | PTT in Hindi or mixed speech produces a transcript labelled `lang=hi` or `mixed`; search finds it; UI stays proper Hindi or English, not forced Hinglish chrome. |
| **Phase** | Phase 1c (PTT exists) + S16 gate |

#### FR-093 — Integrations catalog — **PROPOSAL**

| | |
|---|---|
| **Priority** | P2 |
| **Effort** | L (as a program) |
| **Risk** | Scope creep; single-purpose CWS violation if the extension becomes a Zapier |
| **Moat** | Integration |
| **Rationale** | After Notion, Obsidian, Markdown, PDF, Readwise: GitHub issues (create from a saved error), Linear, Apple Notes via MD, RSS export. Each integration is a **separate optional module** with its own permission. |
| **Acceptance sketch** | Settings list; each off by default; CWS listing still describes one purpose; Android FOSS build may omit proprietary SDKs (F-Droid Inclusion Policy, seen 16 Oct 2026). |
| **Phase** | After FR-062 |

#### FR-098 — AI summaries per collection — **PROPOSAL**

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | M |
| **Phase** | Phase 2 Hub after FR-060; cloud path per D2 |
| **Risk** | Hallucinated recap; cost if cloud; empty-board slop |
| **Moat** | Workflow + data |
| **Rationale** | A board is a project. A cited summary of *this* collection (not the whole day — that is FR-079) is the unit researchers share internally. On-device default. |
| **Acceptance sketch** | “Summarise this board” produces bullets each linking an item id; empty collection produces no fake content; `device_only` / `redacted` items are omitted unless the user confirms. |

#### FR-099 — Google Drive export — **PROPOSAL** (pairs with FR-062)

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | M |
| **Phase** | Phase 2–4 with other exporters |
| **Risk** | Users think Drive *is* the Hub; quota; plaintext in a Drive folder |
| **Moat** | Integration |
| **Rationale** | Notion and Obsidian are already on FR-062. Drive is the remaining India-common target. Export writes a user-picked folder of Markdown + attachments. This is **export**, not sync. (Drive `appDataFolder` as an **encrypted** D7-B store is a different path — §24 — and stays ciphertext.) |
| **Acceptance sketch** | User authorizes a folder; export of a collection/day lands as files the user can see in Drive; token in OS keystore; revoke works; copy says “export,” never “your library now lives in Drive.” |

#### FR-100 — Semantic “ask my library” — **PROPOSAL** (productizes FR-043 / FR-085)

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | L |
| **Phase** | Phase 2 after S17; on-device default |
| **Risk** | Hallucination; thermal; Hindi quality |
| **Moat** | Trust + workflow |
| **Rationale** | The assistant job in one sentence: “What did I save about GST last week?” Answers **cite item ids** or return empty. This is the Hub’s query box, not a chatbot skin. |
| **Acceptance sketch** | Query box on Android Hub and web Hub; each answer lists cited items; airplane-mode demo is true for the on-device model; cloud Q&A is per-feature opt-in (D2). |

#### FR-101 — YouTube learning-path mode — **PROPOSAL** (pairs with FR-068 / FR-091)

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | M |
| **Phase** | Phase 2 with FR-068 |
| **Risk** | Playlist API quota; treating YouTube as a LMS; CWS purpose creep |
| **Moat** | Workflow + integration |
| **Rationale** | Learners save a course, not one timestamp. A **learning path** is a collection bound to a playlist: progress through items, auto-chapter notes from saved timestamps. Playlist membership via YouTube Data API `playlistItems.list` (quota cost **1 unit** per call — [docs, 14 Sep 2026](https://developers.google.com/youtube/v3/docs/playlistItems/list); resource docs [15 Oct 2026](https://developers.google.com/youtube/data/reference/rest/v3/playlistItems)). **Never** download video files. |
| **Acceptance sketch** | User pastes a playlist URL; Hub creates a board with one slot per public playlist item (title + position); saved timestamps attach as chapter notes; progress is local (watched/saved/unseen); missing `t=` opens at 0 and does not lie. |

#### FR-102 — Accounting snippets library — **PROPOSAL** (pairs with D6, D4, FR-050)

| | |
|---|---|
| **Priority** | P1 |
| **Effort** | M |
| **Phase** | Phase 2 after D6 opt-in exists; redaction defaults on |
| **Risk** | Wrong GST/TDS formula; looks like employee monitoring; PII in screenshots |
| **Moat** | Workflow + trust (India operators) |
| **Rationale** | Owner use case: Tally, Excel, GST, TDS. A **snippets library** stores copyable formulas and filed notes bound to a capture, with **redaction on by default** for this library (D4 becomes default-on here even though it is default-off globally). Banking/password apps stay excluded (D6). |
| **Acceptance sketch** | Persona template (FR-088 accountant) includes a “Snippets” board. New saves from opted-in Tally/Excel packages land here with `redacted` default. Snippet body is fenced text (formula / GSTIN-masked note). Copy button. Unredacted frame is not kept when redaction is on. Workplace-policy reminder shown once. |

### 23.3 Prioritized summary

| ID | Proposal | Pri | Effort | Phase | Moat |
|---|---|---|---|---|---|
| FR-091 | YouTube timestamp deep links | P1 | S | Phase 2 with FR-068 | Integration / workflow |
| FR-088 | Persona templates | P1 | S | Phase 2 empty state | Workflow |
| FR-084 | Readwise export | P1 | S | Phase 2–4 | Integration |
| FR-081 | Auto-citations / honest URL recovery | P1 | M | Phase 1d–2 | Trust / workflow |
| FR-089 | Duplicate detection (visible) | P1 | M | P1 library | Data / workflow |
| FR-092 | Hindi/Hinglish voice notes | P1 | M | Phase 1c + S16 | Data / trust |
| FR-079 | Daily AI digest | P1 | M | After Phase 1d Hub; with S17 | Workflow / data |
| FR-098 | AI summaries per collection | P1 | M | Phase 2 Hub | Workflow / data |
| FR-082 | Research sessions / projects | P1 | M | Phase 2 | Workflow |
| FR-099 | Google Drive export | P1 | M | Phase 2–4 with FR-062 | Integration |
| FR-101 | YouTube learning-path mode | P1 | M | Phase 2 with FR-068 | Workflow / integration |
| FR-102 | Accounting snippets library | P1 | M | Phase 2 after D6 | Workflow / trust |
| FR-086 | Local MCP server (stdio) | P1 | M | After Hub schema | Integration / workflow |
| FR-100 | Ask my library | P1 | L | Phase 2 after S17 | Trust / workflow |
| FR-080 | Semantic visual+voice search | P1 | L | S17 | Data / workflow |
| FR-085 | Local LLM Q&A with citations | P1 / Research | L | S16–S17 | Trust / workflow |
| FR-087 | Shareable read-only boards (E2E fragment key) | P2 | M | Phase 4 (Phase 2 if D7-B store) | Network (weak) / trust |
| FR-083 | Spaced-repetition resurfacing | P2 | M | After digest (FR-079) | Workflow |
| FR-090 | Code OCR → fenced snippets | P2 | L | Research then P2 | Workflow |
| FR-093 | Broader integrations | P2 | L | After FR-062 | Integration |

**Build order recommendation:** FR-091 + FR-088 + FR-081 (honest sources) → FR-089 + FR-092 → FR-062 exporters including FR-084 + FR-099 → FR-101 learning-path → FR-102 snippets (after D6) → FR-079 digest + FR-098 board summaries → FR-086 MCP → FR-100/080/085 when models fit the thermal budget → P2 share (FR-087/105) / SRS (FR-083) / code-OCR.

### 23.4 What this does not change

Android spikes S1–S4, approved overlay, local-first default, no FLAG_SECURE bypass, no YouTube/Instagram downloader, team-free V1, and D1–D6 recommendations remain as in §§2, 8, 9, 11. **D7 is pending** and does not promote any §23 proposal into Phase 1.

## 24. Cross-device continuity

**Job.** A moment observed in the browser appears in the Hub on the phone, and a moment observed on the phone appears in the Hub in the browser — with the same capture record (resource, time, spoken idea, provenance). This is JTBD 11 and FR-054. v2.0 already recommended **optional E2E sync, off by default, Phase 4 (D1)**. v2.1 keeps that recommendation for **full-resolution** media and states **when** a thinner continuity path can arrive.

This section is the product spec for that path. It does not replace §16.5 (sync data model) or §9 D1. **D7** is the pending owner decision on pulling a *minimal* payload into Phase 2.

### 24.1 What “connected platforms” means

| Direction | User-visible result | Earliest phase |
|---|---|---|
| Phone → phone (reinstall) | Encrypted archive restores the library | Phase 1 (FR-097) |
| Phone → laptop, offline | User carries the encrypted archive (USB, Drive as a file, AirDrop) | Phase 1 |
| Browser → phone, co-located | QR / same-LAN pairing copies the local store | Phase 2 if D7-C (FR-104) |
| Browser ↔ phone, remote | Opt-in E2E of a **thin** payload (items, tags, transcripts, thumbnails) | Phase 2 if D7-B (FR-103) |
| Browser ↔ phone, full-res | Opt-in E2E of frames, voice audio, imagination images | Phase 4 (D1, FR-047) |
| Friend / public | Read-only collection link, fragment key, revoke, expiry | Phase 4 (FR-087/105); Phase 2 only if a ciphertext store already exists |

Default for every phase: **each device is a complete local Hub**. Continuity is additive and off until the user turns it on.

### 24.2 Continuity by phase (locked for planning)

| Phase | What ships | Architecture (§21.7) | User copy |
|---|---|---|---|
| **0** | Nothing | — | — |
| **1 Android MVP** | **Encrypted export / import** (FR-097). Passphrase-wrapped archive of items, metadata, optional media. Round-trip tested on the device matrix. Hub shows “this library is on this phone” (FR-106). | Android only | “Take a backup. Restore it on another device when that device exists.” |
| **2 Browser extension** | Side-panel Hub is **local to the browser profile** (option **c**). Export/import still works. **If D7-A (default):** stop here. **If D7-C:** QR + LAN pairing (FR-104, option **b**). **If D7-B:** opt-in thin E2E (FR-103) via user-owned storage first. | (c) required; (b)/(thin-a) optional | “Saves in this browser stay in this browser until you export or opt into sync.” |
| **3 Desktop** | Same Hub UI. Desktop may host the native-messaging / localhost bridge so a full tab can open the local store. | (b) natural on desktop | Same as Phase 2 |
| **4 Sync / PWA** | Optional account. Full-res E2E (FR-047, FR-064). Standalone web client decrypts in-browser (option **a**). Read-only shares (FR-087/105). Per-collection device-only still wins. | (a) | “Sync is optional, encrypted, off by default. Lost recovery key means lost remote copy.” |

### 24.3 D7 recap and recommendation

Full options, effort, cost, DPDP and precedents are in **§9 D7**. Short form:

- **A (default):** keep D1. Full sync in Phase 4. Phase 2 is two local libraries plus export.
- **B (owner call):** minimal E2E in Phase 2 — items + thumbnails + transcripts, no full-res media, opt-in, ciphertext, user-owned storage first (Drive `appDataFolder` / WebDAV / iCloud later / Syncthing-style P2P), hosted relay later as Pro.
- **C (bonus):** device pairing on the LAN or via QR, no server.

**Recommendation recorded here:** A is the planning default. Accept B as a Phase 2 fast-follow if Basant wants phone↔browser on the day the extension ships. Ship C as a pairing bonus. S1–S4 and the overlay freeze do not wait on D7.

### 24.4 New functional requirements (continuity)

These continue numbering after FR-093. Marketing-site and Hub-browse IDs in §21 are listed in §13; the four below are the continuity set.

#### FR-097 — Encrypted export / import — Phase 1 continuity (P0)

| | |
|---|---|
| **Priority** | P0 (Phase 1 multi-device path) |
| **Phase** | 1 |
| **Acceptance** | User can export an encrypted archive (items + metadata; media optional) with a passphrase. Import on a fresh install restores items, tags, collections, voice-idea text. Wrong passphrase fails closed. Redacted items stay redacted. Archive format documented in the repo so later clients can read Phase 1 files. |

#### FR-103 — Minimal E2E sync payload — D7-B (P1, only if accepted)

| | |
|---|---|
| **Priority** | P1 **if D7-B is accepted**; otherwise deferred to Phase 4 as a subset of FR-047 |
| **Phase** | 2 fast-follow |
| **Acceptance** | Opt-in. Ciphertext only on the store. Payload = item records, tags, collections, provenance, OCR/page text, transcripts, thumbnails ≤ a documented byte cap. Full-res frames and voice audio stay local unless the user later enables Phase 4. Per-collection device-only is honored. Conflict rule matches §16.5.3 (no silent data loss). Off by default. |

#### FR-104 — LAN / QR device pairing — D7-C (P2)

| | |
|---|---|
| **Priority** | P2 |
| **Phase** | 2 bonus |
| **Acceptance** | Two devices on the same network (or a QR-initiated WebRTC session) can copy the local Hub after a visible pairing confirmation on both. No cloud account. Session ends on Stop. Native-messaging host, if used, is justified in the CWS Privacy tab. |

#### FR-106 — Continuity status UI (P1)

| | |
|---|---|
| **Priority** | P1 |
| **Phase** | 1 (local-only chip); 2+ (export/sync state) |
| **Acceptance** | Every Hub (Android, side panel, later PWA) shows: last backup / last sync time or “never”; “local-only” on device-only items; the device that last wrote an item when known. No green “synced” on a device that has not completed a successful round-trip. |

### 24.5 Transport choices if D7-B is accepted

Preference order (first that the user enables):

1. **Google Drive application data folder** — hidden, unshareable, `drive.appdata` scope, deleted on uninstall ([Drive API, 3 Sep 2026](https://developers.google.com/workspace/drive/api/guides/appdata)). Store **ciphertext**. Counts against the user’s quota.
2. **WebDAV** (Nextcloud, a NAS, a self-hosted folder) — FOSS-friendly; same ciphertext blobs.
3. **Syncthing-style P2P** — no cloud copy; untrusted-device encryption is still documented as beta (7 Oct 2026).
4. **Hosted ciphertext relay** — Pro SKU, India-region option (open question 20), Phase 4 unless the owner pulls it earlier.

iCloud is an Apple-client adapter after desktop/iOS discovery; it is not an Android MVP dependency.

### 24.6 Privacy, DPDP and failure modes

- Export/import (FR-097) never creates a fiduciary: the file is the user’s.
- User-owned storage (Drive appData, WebDAV) keeps OBZRAi off the data-fiduciary hook **if** the app never has the key and never sees plaintext. Counsel still reviews the OAuth consent screen (open question 36).
- A hosted relay **does** create fiduciary duties (D1 risks). Notices, deletion, India-region option.
- Lost passphrase / recovery key: remote ciphertext is unrecoverable. Copy says so before the first opt-in (Obsidian’s stated failure mode; Bitwarden Send’s expiry/revoke is the share analogue).
- Referer leakage of fragment keys on share links is an S23 item: open share pages with `Referrer-Policy: no-referrer`.

### 24.7 What this section does not change

D1 remains: full-res E2E optional, off by default, Phase 4. Team-free V1 (FR-063). No plaintext hosted Hub. Android S1–S4 still gate all of this.

---

## 25. Changes from v1.2

1. **Rename** throughout: product is **OBZRAi**; former name “AI Observer” retained only as history and collision context.
2. **Platform constraints rewritten** with live sources; **single-app picker dated to Android 14 / 14 QPR2** (v1.2 left this vague / easy to misread as Android 15-only).
3. Added **Android 15 QPR1 status-bar chip and lock-to-stop**, **AudioPlaybackCapture policy table**, **Play FGS demo video**, **sideload restricted overlay**, **2026–27 developer verification**, **Play target API 36 (cited)**.
4. Added **Chrome 2/sec `captureVisibleTab` quota**, MV3 **offscreen + tabCapture** path, side panel as Library surface.
5. Added **macOS Sequoia recurring prompts** and **`SCContentSharingPicker`**; **Windows yellow border / `IsBorderRequired`**; **Recall** as privacy case study.
6. Added **RN/iOS reality**: no first-class RN capture API; ReplayKit BUE **~50 MB**; no iOS system overlay.
7. New **§6.8 Platform constraints matrix** (Verified / Source / Date).
8. New **§7 Competitive landscape** with dated prices/stars and a positioning map (Screenpipe, Meta/Limitless, Pocket, ReciMe, BhaiFinder, Raindrop, Notion Clipper, Observer AI, Pixel, Recall, etc.).
9. New **§8 Pricing** with INR/USD recommendation, Play 15/30% until 30 Sep 2027 for India, alternative billing −4%, BYO key, open-core.
10. **D1–D6 each have a recommendation, rationale and risks** (§9), no longer only a pending table.
11. **FRs tightened** (measurable AC); added FR-055–057; PTT remains P0.
12. **Metrics** split into north star, input metrics and guardrails; all numbers labelled **targets**.
13. **Roadmap rephased** to Phase 0–4 matching the brief; Android MVP re-estimated **40–55 days**; no unjustified calendar dates.
14. **Legal**: DPDP Rules 2025 Gazette **13 Nov 2025**, 12- and 18-month phases; GDPR note; Play User Data; Instagram ToS 4.2; YouTube caution.
15. **Naming/trademark** section for OBZRAi; IP India **not done** (CAPTCHA).
16. **Risk register**, **Unverified claims**, **Sources table** with URLs and dates.
17. Quality bar, overlay spec, flows A–K, pipeline, architecture, data model, spikes S1–S18, open-source spec **kept and tightened**, not dropped.
18. Monetization (v1.2 open question 25) answered with a recommended model; still reversible by the owner.

### v2.0 addendum (17 October 2026)

19. New **§21 Web-grade product experience**: Research Hub as local-first web app / PWA (side panel first, standalone PWA in Phase 4), boards, search, export to Notion/Obsidian/Markdown/PDF, **team-free V1**, extension UX (side panel, shortcuts, highlight/clip, YouTube timestamps, voice note), CWS MV3 / single-purpose / least-privilege / payments realities, design system (onboarding, empty states, WCAG 2.2 AA, dark mode, i18n EN+Hindi, performance budgets, trust center).
20. New **§22 Go-to-market strategy**: ICP India+global, positioning, Product Hunt 2025–26 mechanics, CWS/Play/F-Droid/Izzy/Obtainium/Show HN/Reddit/X/creators, pricing INR/USD plus lifetime-as-P2, traction tactics, labelled 30/60/90-day targets with retention benchmarks.
21. New **§23 Proposed features for strength and defensibility**: FR-079–FR-093 marked PROPOSAL with priority, effort, risk, moat type, and a build-order table.
22. Functional requirements **FR-058–FR-093** added to §13. §19.8 maps them onto Phase 0–4. Spikes S19–S21 named (CWS packet, Hub CWV, MCP threat model).
23. Former §§21–27 renumbered **24–30**. Unverified list, risk register, open questions, glossary and sources extended. Document control records **v2.0 addendum, 17 Oct 2026**.
24. Locked addendum decisions: Phase 2 side panel is the first web Hub; standalone PWA waits for E2E; no CWS Featured-badge campaign (badge sunsetting later 2026); lifetime SKU is P2.

### v2.1 changes (17 October 2026)

25. **§21 extended in place** (not duplicated): Hub **browse** (FR-095); **read-only collection share** with E2E key in the URL fragment, revoke, and expiry (FR-087 tightened, FR-105); extension UX polish; web/marketing **onboarding** (FR-096); **marketing site** pages, messaging, SEO/AEO, privacy/trust, per-platform download CTAs, docs, changelog, open-source (FR-094, §21.6); explicit fit of the web app to local-first + E2E — options **(a)** synced E2E web client, **(b)** local bridge, **(c)** side panel as Hub — with a per-phase recommendation (§21.7).
26. **§22 extended in place:** step-by-step launch checklists for Product Hunt, Chrome Web Store, Play listing + ASO, GitHub README + Show HN, Reddit, X, Indian FOSS communities, YouTube creators (§22.9); open-source contributor tactic; launch timeline aligned to Phases 0–4 (§22.10); pricing still tied to §8; all launch metrics remain labelled **targets**.
27. **§23 extended in place:** every proposal now has **priority, effort, and phase**. New **PROPOSAL** IDs: FR-098 AI summaries per collection; FR-099 Google Drive export; FR-100 ask my library; FR-101 YouTube learning-path mode; FR-102 accounting snippets library (redaction defaults on). FR-087 acceptance now specifies fragment-key E2E share.
28. New **§24 Cross-device continuity**: what you observe in the browser shows up on mobile and vice versa, per phase. D1 (full-res E2E, optional, off by default, Phase 4) is unchanged. Phase 1 = encrypted export/import; Phase 2 = local Hubs + optional D7; Phase 4 = full sync + standalone E2E web client.
29. New pending **D7** in §9 (and this changes list): pull minimal E2E sync alongside Phase 2 vs keep full sync in Phase 4. Options A / B / C with scope, effort, cost, DPDP, risks, live precedents (Obsidian, Standard Notes, Bitwarden, Syncthing, Excalidraw, Proton Drive, Ente, Drive appDataFolder), and a recommendation (A default; B as owner-call fast-follow; C as pairing bonus).
30. Functional requirements **FR-094–FR-106** added to §13. §19.8 mapping and spikes **S22–S23** updated. Former §§24–30 become **25–31**.
31. Unverified list, risk register, open questions, glossary and sources extended for v2.1 facts (store-listing limits, fragment-key share, sync precedents, FOSS United dates). Document control records **v2.1, 17 Oct 2026**.

---

## 26. Unverified claims

The following were **not confirmed** with a primary source on 17 October 2026. Do not repeat them as facts in marketing.

1. **IP India trademark search for OBZRAi / OBZRAI / OBZRAI.ai in classes 9 and 42** — official tool is CAPTCHA-gated (`tmsearch.ipindia.gov.in`); not completed.
2. **Exact Screenpipe consumer prices on screenpipe.com/pricing** — aggregators on 1 Oct 2026 report $21 / $42; older pages $25 / $50. Confirm at checkout.
3. **Observer AI GitHub star count** — ~1.7k at marketing-research scrape; GitHub UI date not shown; re-check at launch.
4. **ReciMe free-tier “5 imports/week” and exact Plus SKUs** — from 2026 roundups, not a live App Store receipt.
5. **Recall.it “700,000+ users”** — vendor about page, Oct 2026; not independently audited.
6. **iOS 27 ScreenCaptureKit replacing ReplayKit BUE** — Forasoft 10 Oct 2026 secondary article; not confirmed in Apple’s public documentation in this research pass.
7. **Android 16-specific extra token rules beyond Android 14 one-shot consent** — Forasoft 1 Aug 2026; confirm against Android 16 behavior-changes doc at implementation.
8. **Play target API 36 deadline 31 Aug 2026 / extension 1 Nov 2026** — Forasoft; re-verify on Play policy deadlines page at submission.
9. **Rest-of-World Play billing-fee percentage after 30 Sep 2027** — Google had not announced it in the sources found.
10. **Whether a purely local, no-account Android app is a DPDP “Data Fiduciary” for on-device screenshots** — legal question; not answered by the Gazette text alone.
11. **USPTO/EUIPO full clearance for OBZRAi** — not filed; only a web/X/Play collision scan was done.
12. **Instagram/YouTube current app versions’ `allowAudioPlaybackCapture` value** — must be measured in S4; do not generalize.
13. **Meta acquisition of Limitless terms (price, product roadmap)** — acquisition date verified; commercial terms not verified.
14. **No X posts for OBZRAi** — true for the query run on 17 Oct 2026; not a trademark clearance.
15. **developer.android.com direct fetch** — some official Android URLs were blocked as SSRF in this environment (resolved to 198.18.0.1); facts were taken from mirrored docs (`developer.android.google.cn`), Play Help, and secondary engineering posts. Re-open official URLs from a normal network before implementation.
16. **Obtainium “35 GitHub stars required for config inclusion”** — claimed on X by @arferrari (10 Oct 2026). Not confirmed in Obtainium project docs in this pass.
17. **Product Hunt Featuring Guidelines last-updated stamp** — help article https://help.producthunt.com/en/articles/9883485-product-hunt-featuring-guidelines was thin in one fetch; content cited from a subsequent search snippet. Re-open before a launch checklist is frozen.
18. **Chrome Web Store registration fee dollar amount** — official register page (last updated 13 Feb 2024) says “one-time registration fee” and lists India, but does not state US$5. Amount is from 2026 secondary checklists (ExtensionBooster 22 Jun 2026; Extension Radar 16 Feb 2026). Confirm at checkout.
19. **CWS Featured badge exact sunset date** — official blog 20 Aug 2026 says “later this year”; self-nominations closed that day. End date not a calendar day in the post.
20. **Chrome-Stats Featured share (7.8% of MV3 listings / 48.3% of displayed users)** — third-party snapshot 20 Jul 2026, before the sunset announcement. Not a Google metric.
21. **Lifetime-tier willingness to pay for OBZRAi** — no product data. ₹3,999 / $79–99 is a planning envelope only.
22. **Indian / global creator seeding conversion** — no OBZRAi data. Hitesh Choudhary subscriber counts are **self-reported in X bio** (seen 20–21 Sep 2026).
23. **MCP 2026-07-28 client support breadth on-device** (which assistants speak stdio + the stateless core) — not surveyed. Ship stdio first and document tested clients.
24. **YouTube Shorts ignoring `t=` timestamps** — stated in some 2026 secondary guides; not confirmed in YouTube’s own parameter documentation in this pass.
25. **W3C TAG “Installing Websites Must Be Easy” (16 Oct 2026)** — draft, not a Recommendation. Do not treat as a shipping requirement.
26. **ExtensionPay / Stripe blended take-rate (~5% + Stripe)** — vendor-stated; confirm at integration. Not a Google figure.
27. **Nicheloom Product Hunt volume (28,255 launches / 35 days, ~3.8% featured, 14 Oct 2026)** — third-party scraper, not Product Hunt official.
28. **Sanskar Tiwari / IndianAppGuy current YouTube subscriber count** — older listings; re-scrape before outreach.
29. **Show HN new-account restriction / AI-text ban dates** — restated by secondary launch blogs (swanbase 25 Sep 2026). Confirm against current news.ycombinator.com/newsguidelines.html before posting.
30. **developer.chrome.com SSRF** — some official Chrome URLs resolved to 198.18.0.1 in this environment in an earlier pass; later `open_page` fetches of register and sidePanel succeeded. Re-open from a normal network before implementation.

Existing items 2 (Screenpipe prices), 4 (ReciMe SKUs) and 9 (RoW Play billing-fee %) remain unverified and still apply to §22 pricing.

**New in v2.1 (17 October 2026):**

31. **Ente paid SKUs ($2.49 Starter / $4.99 Popular / $9.99 Pro / $19.99 Pro+)** — aggregators citing ente.com on 15 Oct 2026 ([MacMyths](https://macmyths.com/apps/ente-photos/pricing/), [The Geeks Club, 17 Oct 2026](https://www.thegeeksclub.com/platforms/ente-photos/pricing/)). Official help confirms **10 GB free forever** ([ente.io/help](https://ente.io/help/photos/faq/storage-and-plans)). Checkout on ente.com was not fetched (SPA). Confirm at checkout.
32. **Obsidian Sync Cure53 (Oct 2024) and Trail of Bits (Dec 2025) audit PDFs** — cited by WorkifyLab (31 Aug 2026) as published together in May 2026. Primary PDFs were not opened in this pass. E2E AES-256 and unrecoverable-password copy are taken from obsidian.md/sync and kepano’s X posts.
33. **CWS small promo tile 440×280 “required on some surfaces”** — Kromio submit guide (updated Jul 2026). Official template marks it RECOMMENDED. Treat as recommended unless the dashboard blocks submit.
34. **Play feature graphic exact 1024×500 / icon 512×512 / screenshot 2–8** — restated from Play Console Help by StoreShot (20 Sep 2026), Nuvex (checked 10 Oct 2026), Ecranify (16 Oct 2026). Re-open [Add preview assets](https://support.google.com/googleplay/android-developer/answer/9866151) and [listing best practices](https://support.google.com/googleplay/android-developer/answer/13393723) from a normal network before freezing the packet (SSRF risk as in item 15).
35. **Product Hunt gallery 1270×760, thumbnail 240×240, tagline 60, image < 3 MB** — taken from PH launch guide as restated by Submitator (3 Aug 2026). Confirm on producthunt.com/launch the week of launch.
36. **Drive appDataFolder quota** — official docs state the folder exists and is deleted on uninstall (3 Sep 2026). That it **counts against user quota** is community-confirmed (r/developersIndia, 19 Apr 2026) and widely reported; the official appdata guide does not spell out the quota sentence in the fetch used here. Treat quota-counts-against-user as **widely reported, confirm in Drive settings before promising “free sync.”**
37. **Syncthing untrusted-device mode still beta** — docs.syncthing.net/users/untrusted.html dated 7 Oct 2026. Do not market P2P as a guaranteed E2E cloud replacement.
38. **YouTube Data API `playlistItems.list` quota = 1 unit** — official docs last updated 14 Sep 2026. Daily quota remaining for a new GCP project is account-specific; do not claim unlimited playlist imports.
39. **Fragment keys leaking via Referer, screenshots, or chat unfurls** — known class of issues for Excalidraw-style links; S23 must measure. No OBZRAi measurement yet.
40. **D7-B engineering calendar (~3+ weeks)** — relative effort label only; no team velocity data.
41. **FOSS United Bengaluru “every last Saturday”** — chapter page (fossunited.org/c/bengaluru, seen 17 Oct 2026). October 2026 listing showed **24 Oct 2026**; confirm before travelling.
42. **Official CWS image-size page** — CWS Kit (Apr 2026) and the GoogleChrome CHROMEWEBSTORE.md template agree on 1280×800 / 640×400 / 128×128 / name 75 / summary 132. Re-open developer.chrome.com/docs/webstore/images from a normal network (SSRF as in item 30).

---

## 27. Naming and trademark risk

### 27.1 Why the rename

“AI Observer” collides with **Observer AI** (Roy3838) — the closest product and the worst privacy-headline collision — plus Xeoma “AI Observer” (CCTV) and OBSERVER Ai Weather on Play. Marketing research (17 Oct 2026) already recommended renaming before launch.

### 27.2 OBZRAi collision scan (17 October 2026)

| Name | What | Risk | Source | Date |
|---|---|---|---|---|
| **OBZRAi / obzrai** | This product | — | — | — |
| **X posts `OBZRAi OR obzrai`** | No posts found | Brand unused on X at scan time | X keyword search | 17 Oct 2026 |
| **Obz AI / obz.ai** | Computer-vision **model monitoring** (XAI), Python SDK `obzai` | Phonetic/visual collision in AI circles; different job | [obz.ai](https://obz.ai/); [docs.obz.ai](https://docs.obz.ai/introduction) (7 Jan 2026) | Live / 7 Jan 2026 |
| **Obsera** | UK TM UK00004399049, classes 9, 41, 42, Amalthea Estates Ltd | Similar look in class 9/42 if expanding to UK | [UK IPO journal 2026/024](https://www.ipo.gov.uk/t-tmj/tm-journals/2026-024/UK00004399049.html) | 12 Jun 2026 (journal); filed 8 Jun 2026 |
| **Observer AI** | Screen-watching agents | Highest **product** collision with the old name | GitHub Roy3838 | Live Oct 2026 |
| **O’zera / Obzera chocolate** | Confectionery TM | Low (class 30) | WIPO 1838422 | 2024–2025 |
| **OBZOR** | Russian wordmark | Low for India Android | RBC companies | Updated 15 Feb 2026 |

**Play Store:** no exact “OBZRAi” app found in this pass (**Unverified** as a full Play crawl; search was web-based).

**IP India:** **not searched.** `https://tmsearch.ipindia.gov.in/` is CAPTCHA-gated. India is first-to-use and first-to-file. **Do a class 9 and class 42 search, then file, before printing stickers or a Play listing under OBZRAi.**

**Package ID:** do not use `com.observer.ai`. Prefer something like `app.obzrai.android` after clearance.

**Recommendation:** keep **OBZRAi** as the working consumer name *subject to IP India clearance*. Have a backup shortlist (from marketing research): Keepframe, SeenLocal, ReelMemory. Do not lead ads with the word “Observer” until legal is done.

---

## 28. Risk register

| ID | Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|---|
| R1 | Play rejects MediaProjection + overlay | Med | High | Conservative UX; FGS video of a tutorial site; FOSS track (GitHub/Izzy/F-Droid) as default if rejected |
| R2 | Instagram/YouTube audio always blocked | High | Med | Honest UI; S4 matrix; screenshot+OCR still valuable |
| R3 | FLAG_SECURE / DRM on target apps | High | Med | `capture_blocked`; never market bypass |
| R4 | Sideload overlay blocked (Android 15+) | High for FOSS | Med | Restricted-settings education; Play build for less-technical users |
| R5 | 2027 developer verification breaks GitHub APKs on certified devices | Med (India 2027) | High | Register now; communicate advanced flow; keep F-Droid/participating stores in mind |
| R6 | Privacy perception (“spyware”) | High | High | Session-only; OS chip; local-first; no wake word in V1; Graphene/FOSS review before r/fossdroid |
| R7 | Name collision (Observer AI / Obz AI) | Med | Med | OBZRAi + IP India filing; avoid “Observer” in first-line ads |
| R8 | DPDP 18-month duties (May 2027) if accounts exist | Med | High | Local-first MVP; counsel before account launch; notices and deletion already in the product |
| R9 | Instagram ToS enforcement | Low–Med | High | No scraper/downloader; no IG-focused ads; OS capture only |
| R10 | Scope creep (sync + assistant + imagination in MVP) | Med | High | D1–D5 recommendations; Phase 1 freeze |
| R11 | Midrange thermal/battery on OCR+ASR | Med | Med | Adaptive sampling; profile before claims; tiny Whisper by default |
| R12 | E2E key loss | Med (when sync ships) | High | Recovery key; honest copy; device-only sensitive collections |
| R13 | Cloud AI cost | Med | Med | BYO key first; meters; off by default |
| R14 | iOS overlay impossible | Certain | Med for “one app everywhere” | Do not promise the ring on iOS; share extension / broadcast picker |
| R15 | Screenpipe-like relicensing backlash if core closes | Low | Med | Apache-2.0 intent; say so in README; hosted sync clearly separate |
| R16 | CWS rejection (single purpose, broad hosts, YouTube download appearance, RHC) | Med | High | FR-070/071; activeTab; no downloader; S19 packet; docs-site demo video |
| R17 | CWS Featured sunset removes a discovery lever | Certain (badge) | Low–Med | Do not plan a badge campaign; listing quality + ratings + editorial home |
| R18 | PH not featured (waitlist, thin craft, company account) | Med | Low | Launch only tryable; self-hunt personal account; Show HN/FOSS as the real plan |
| R19 | Play fee / India RoW cutover (30 Sep 2027) surprises unit economics | Med | Med | Model 15/30% until then; alt billing −4%; Stripe on CWS/web |
| R20 | PWA Hub ships before E2E and becomes a plaintext second copy | Med | High | Standalone PWA gated on FR-064 / S14 |
| R21 | MCP / assistant prompt injection via saved page text | Med (when FR-086) | High | Read-only default; no network listener; S21 threat model |
| R22 | Hindi STT quality too low for FR-092 claim | Med | Med | S16 labelled set; do not advertise “perfect Hinglish transcription” |
| R23 | Scope creep from §23 proposals into Android MVP | Med | High | PROPOSAL marker; Phase 1 freeze (R10) still wins |
| R24 | DPDP exposure from shareable board URLs (FR-087) | Low until built | High | P2; explicit publish; expiry; fragment key; noindex; counsel before public links; S23 |
| R25 | D7-B pulled into Phase 2 slips the extension exit | Med if B accepted | Med | Default remains A; B is a fast-follow after CWS unlisted testers; S22 spike first |
| R26 | Users treat thin sync (thumbs) as a full-res backup | Med if B ships | Med | FR-106 copy; full-res stays local; Phase 4 upsell is honest |
| R27 | Drive appData / WebDAV quota fills with thumbnails | Med | Med | Byte cap on thumbs; user-visible quota; no full-res on B |
| R28 | Fragment-key share URL leaked (chat unfurl, Referer, screenshot) | Med when FR-087 ships | High | `Referrer-Policy: no-referrer`; optional password; short default expiry (7 days); revoke |
| R29 | Native messaging / localhost bridge rejected or poorly justified on CWS | Low–Med if C ships | Med | Ship (c) side panel without nativeMessaging; add (b) only with a written purpose |
| R30 | Marketing site origin confused with a hosted plaintext Hub | Med | High | Distinct origins; Hub pages noindex; copy in §21.7 |

---

## 29. Open questions

Engineering validation (keep from v1.2; still open):

1. Audio coverage matrix for current Instagram/YouTube/TikTok builds (S4).
2. OCR adapter: ML Kit vs Tesseract (size/accuracy/FOSS).
3. STT model: Whisper tiny/base multilingual; download UX; heat.
4. Long-clip segmentation and audio duration cap.
5. Source-app identification without invasive permissions.
6. Overlay sizing across OEM skins.
7. Play vs FOSS-only publication after S8.
8. Observer core technology (KMP vs Rust/C++ vs contract).
9. React Native: replace Compose on Android, or iOS/cross-platform only?
10. Desktop framework: Tauri vs Electron vs native; which OS first (recommendation: **macOS first**).
11. Browser: Chromium-only first vs Chromium+Firefox (recommendation: **Chromium first**).
12. Sync backend: managed vs self-host vs user-owned storage.
13. Sign-in: email/passkey vs third-party vs both (recommendation: **passkey + email**, no mandatory social login).
14. E2E recovery-key UX.
15. Imagination provider and who pays (recommendation: **BYO key first**).
16. Default exclusion-list package names (maintain as a versioned list).
17. Quality-bar numeric targets for sync latency and assistant latency after S14/S16.
18. Backup consumer name if IP India blocks OBZRAi.
19. Whether Pro IAP ships in the first Play listing or only after product-market fit on FOSS.
20. Data residency region for the sync relay if D1 proceeds (recommendation: **India region option** for Indian accounts).
21. Confirm CWS **$5** registration at checkout (official page omits the amount).
22. Which four `suggested_key` bindings to ship if OS/browser conflicts appear in S10 (recommendation: panel, save, skip, voice — FR-066).
23. Notion integration: internal integration token vs public OAuth (recommendation: **token in V1**, OAuth later).
24. Whether to ship **lifetime** Pro at all (recommendation: **P2**, after 90-day conversion data).
25. MCP write tools (save/tag) in V1 or read-only only (recommendation: **read-only**).
26. Product Hunt date: Android APK launch vs Phase 2 extension (recommendation: **whichever is first tryable and visually complete**; do not PH a waitlist).
27. Creator seeding budget and whether Hitesh-class channels are in scope (recommendation: **five mid-size dev creators first**).
28. Hindi UI before first Play India listing, or English-only Play with Hindi in a fast follow (recommendation: **Hindi strings before India listing**, FR-076).
29. F-Droid flavor: ML Kit vs Tesseract if ML Kit is considered non-free (ties to open question 2).
30. Whether the standalone PWA origin is `app.obzrai` / `obzrai.app` after domain and TM clearance.
31. **D7 (new, Basant):** keep full E2E sync in Phase 4 (A), pull minimal E2E into Phase 2 (B), and/or ship LAN/QR pairing (C). Recommendation in §9: **A default; B as Phase 2 fast-follow if phone↔browser must work when the extension ships, user-owned storage first; C as pairing bonus.**
32. First D7-B storage adapter: Drive `appDataFolder` vs WebDAV vs both (recommendation: **WebDAV + Drive**, WebDAV for FOSS flavor).
33. Thumbnail byte cap for FR-103 (recommendation: **≤ 200 KB JPEG**, revisit after S22).
34. Share-link default expiry: 7 vs 31 days (recommendation: **7 days**, Bitwarden Send default; max 31).
35. Whether FR-087 ships in Phase 2 if D7-B already has a ciphertext store, or waits for Phase 4 (recommendation: **wait for Phase 4** unless B is already in users’ hands).
36. Counsel review: is user-owned Drive appData (ciphertext, key on device) enough to keep OBZRAi off DPDP fiduciary duties for D7-B?

Until spikes close, copy must say **“capture and transcribe when permitted,”** never “records every Reel,” “saves every page on every platform,” or “captures any movie.”

---

## 30. Glossary

| Term | Meaning |
|---|---|
| OBZRAi | This product (formerly AI Observer). |
| Observer core | Shared matching, schema, privacy and Hub logic reused by all clients. |
| Observation session | User-started, OS-authorized capture window; ends on Stop, chip, lock, revoke or kill. |
| Research Hub | The Library: All / Today / Collections. |
| Capture record | Linked resource + time + idea + optional imagination + provenance. |
| Provenance narrative | Template sentence built only from stored facts. |
| PTT | Push-to-talk microphone for idea notes and later assistant commands. |
| `capture_blocked` | Frame/audio blocked by DRM, FLAG_SECURE or equivalent. |
| MediaProjection | Android API for user-consented screen capture. |
| FGS | Foreground service; type `mediaProjection` required on Android 14+. |
| ALLOW_CAPTURE_BY_* | Android playback-capture policies (`ALL`, `SYSTEM`, `NONE`). |
| FLAG_SECURE | Android window flag that blacks out captures. |
| MV3 | Chrome Manifest V3 extension model. |
| ScreenCaptureKit | Apple macOS (and evolving iOS) screen-capture framework. |
| SCContentSharingPicker | macOS system picker that avoids Sequoia monthly bypass nags. |
| WGC | Windows Graphics Capture. |
| BUE | iOS ReplayKit Broadcast Upload Extension (~50 MB). |
| E2E | End-to-end encryption (server cannot read content). |
| DPDP | India’s Digital Personal Data Protection Act, 2023, and Rules, 2025. |
| BYO key | User-supplied cloud AI API key. |
| P0 / P1 / P2 | Must-have for Android MVP / after / research. |
| PWA | Progressive Web App: installable local-first Hub origin (Phase 4). |
| Side panel | Chromium `sidePanel` surface (Chrome 114+); first web Hub. |
| Board | Web name for a Collection (FR-060); not a second data model. |
| Team-free V1 | No orgs, seats, or live co-edit (FR-063). |
| Trust center | In-app + public privacy dashboard (FR-078). |
| MCP | Model Context Protocol; local stdio server proposed as FR-086 (spec 2026-07-28). |
| CWS | Chrome Web Store. |
| CWV | Core Web Vitals (LCP, INP, CLS). |
| AEO | Answer-engine optimisation (answer-first public pages). |
| PROPOSAL | §23 item; not Android MVP unless already P0 in §13. |
| Continuity | A save on one signed-in or paired device appearing in the Hub on another (§24). |
| Fragment-key share | Read-only link whose decryption key lives after `#` in the URL (Excalidraw pattern; FR-087/105). |
| appDataFolder | Google Drive hidden per-app folder (`drive.appdata` scope); candidate ciphertext store for D7-B. |
| Local bridge | Option (b): extension or desktop serves the local store over native messaging, localhost, or WebRTC pairing. |
| D7 | Pending decision: pull minimal E2E sync into Phase 2 vs keep full sync in Phase 4. |

---

## 31. Sources

Live research, 17 October 2026, unless a row states another date. These support feasibility and market context; they are not endorsements of unrestricted recording.

| # | Claim area | URL | Date |
|---|---|---|---|
| 1 | Android MediaProjection | https://developer.android.google.cn/media/grow/media-projection?authuser=0 | Live 17 Oct 2026 |
| 2 | Android 14 MediaProjection consent | https://developer.android.com/about/versions/14/behavior-changes-14 | Live |
| 3 | Android 14 QPR2 single-app recording | https://9to5google.com/2023/11/15/android-14-qpr2-single-app-recording/ | 15 Nov 2023 |
| 4 | Android screen sharing 2026 notes (API 36, 14 QPR2) | https://www.forasoft.com/blog/article/screen-sharing-android-1644 | 1 Aug 2026 |
| 5 | FGS types including mediaProjection | https://developer.android.com/develop/background-work/services/fgs/service-types | Live |
| 6 | Play FGS / FSI declaration + demo video | https://support.google.com/googleplay/android-developer/answer/16965181 | Live |
| 7 | Play Device and Network Abuse (FLAG_SECURE, FGS) | https://support.google.com/googleplay/android-developer/answer/16559646 | Live |
| 8 | Play FGS requirements (related) | https://support.google.com/googleplay/android-developer/answer/13392821 | Live |
| 9 | Status-bar chip + lock stop | https://developer.android.com/about/versions/12/12L/features/media-projection-large-screens | Live |
| 10 | Android 15 QPR1 stop screen shares | https://www.androidauthority.com/android-15-qpr1-stop-screen-shares-3489652/ | 11 Oct 2024 |
| 11 | Android 15 QPR1 recording chip | https://9to5google.com/2024/09/12/android-15-screen-recording-cast-chip/ | 12 Sep 2024 |
| 12 | Mirrativ onStop chip | https://tech.mirrativ.stream/entry/2024/12/17/103000 | 17 Dec 2024 |
| 13 | AudioPlaybackCapture / AV capture | https://developer.android.com/media/platform/av-capture | Live |
| 14 | AudioPlaybackCaptureConfiguration | https://developer.android.google.cn/reference/android/media/AudioPlaybackCaptureConfiguration | 3 Aug 2026 |
| 15 | Capturing audio in Q (policies) | https://android-developers.googleblog.com/2019/07/capturing-audio-in-android-q.html | Original 2019; page seen 16 Oct 2026 |
| 16 | Sideload restricted settings Android 15 | https://www.androidauthority.com/android-15-sideloaded-apps-restrictions-3481098/ | 12 Sep 2024 |
| 17 | Same, 9to5Google | https://9to5google.com/2024/09/12/android-15-sideloaded-apps-restrictions/ | 12 Sep 2024 |
| 18 | Android developer verification | https://developer.android.com/developer-verification | 30 Sep 2026 |
| 19 | Verification first wave | https://www.androidauthority.com/android-sideloading-developer-verification-first-wave-rollout-3717921/ | 1 Oct 2026 |
| 20 | Verification took effect | https://www.theverge.com/tech/1003920/androids-developer-verification-rules-just-took-effect-for-the-first-time | 2 Oct 2026 |
| 21 | 24-hour sideload flow | https://arstechnica.com/gadgets/2026/03/google-details-new-24-hour-process-to-sideload-unverified-android-apps/ | 19 Mar 2026 |
| 22 | Chrome tabs API / 2 per second | https://developer.chrome.com/docs/extensions/mv2/reference/tabs | 28 Sep 2026 |
| 23 | chrome.tabCapture | https://developer.chrome.com/docs/extensions/reference/api/tabCapture | 11 Sep 2026 |
| 24 | Offscreen documents MV3 | https://developer.chrome.com/blog/Offscreen-Documents-in-Manifest-v3 | 25 Jan 2023 |
| 25 | Chromium quota history | https://issues.chromium.org/issues/40764505 | 4–5 May 2021 |
| 26 | Sequoia monthly screen-recording prompt | https://9to5mac.com/2024/08/14/macos-sequoia-screen-recording-prompt-monthly/ | 14 Aug 2024 |
| 27 | Sequoia 15.1 fewer dialogs | https://9to5mac.com/2024/10/07/macos-sequoia-screen-recording-popups/ | 7 Oct 2024 |
| 28 | SCContentSharingPicker | https://developer.apple.com/documentation/screencapturekit/sccontentsharingpicker | 7 Oct 2026 |
| 29 | TidBITS Sequoia picker note | https://tidbits.com/2024/08/19/apple-reduces-excessive-sequoia-permission-requests-shifts-to-monthly/ | 19 Aug 2024 |
| 30 | ScreenCaptureKit audio | https://qiita.com/TakeshiKawai/items/5d5fcb08e7a46608f4fa | 6 Oct 2026 |
| 31 | Windows Graphics Capture | https://learn.microsoft.com/en-us/windows/apps/develop/media-authoring-processing/screen-capture | 27 Aug 2026 |
| 32 | IsBorderRequired | https://learn.microsoft.com/en-us/uwp/api/windows.graphics.capture.graphicscapturesession.isborderrequired?view=winrt-28000 | 5 Aug 2026 |
| 33 | WDA / DRM vs affinity | https://captmeelo.com/research/2026/09/13/the-drm-flag-that-isnt-drm.html | 13 Sep 2026 |
| 34 | Microsoft Recall privacy | https://support.microsoft.com/en-us/windows/privacy/privacy-and-control-over-your-recall-experience | Live Oct 2026 |
| 35 | Recall feature page | https://support.microsoft.com/en-us/windows/ai/ai-features/retrace-your-steps-with-recall | Live Oct 2026 |
| 36 | Recall on 26H2 | https://techdows.com/does-windows-11-26h2-have-recall.html | 30 Sep 2026 |
| 37 | Manage Recall | https://learn.microsoft.com/en-us/windows/client-management/manage-recall | 24 Aug 2026 |
| 38 | iOS BUE 50 MB (Apple Forums) | https://developer.apple.com/forums/thread/108854 | Date not shown |
| 39 | iOS BUE 50 MB (Twilio) | https://github.com/twilio/video-quickstart-ios/blob/master/ReplayKitExample/README.md | Repo live |
| 40 | iOS screen sharing 2026 | https://www.forasoft.com/blog/article/how-to-implement-screen-sharing-in-ios-1193 | 10 Oct 2026 |
| 41 | RN native modules | https://reactnative.dev/docs/native-platform | Live |
| 42 | xdg-desktop-portal ScreenCast | https://flatpak.github.io/xdg-desktop-portal/docs/doc-org.freedesktop.portal.ScreenCast.html | Live |
| 43 | Samsung Circle to Search / secure apps | https://www.samsung.com/us/support/answer/ANS10000753/ | 5 Oct 2026 |
| 44 | Meta/IPSOS India Reels | https://about.fb.com/news/2026/06/reels-is-shaping-indias-video-first-future-across-gen-z-women-bharat/ | 3 Jun 2026 |
| 45 | YouTube Shorts 200B | https://blog.youtube/news-and-events/neal-mohan-cannes-2025/ | 18 Jun 2025 |
| 46 | Shorts view-counting change | https://www.thekeyword.co/stats/youtube-shorts | 2025–2026 |
| 47 | ReciMe Play listing | https://play.google.com/store/apps/details?id=com.recime.app | Updated 1 Oct 2026 |
| 48 | Pocket shutdown | https://blog.mozilla.org/en/mozilla/building-whats-next/ | 22 May 2025 |
| 49 | Pocket support / export end | https://support.mozilla.org/en-US/kb/future-of-pocket | 12 Nov 2025 |
| 50 | Meta acquires Limitless | https://techcrunch.com/2025/12/05/meta-acquires-ai-device-startup-limitless/ | 5 Dec 2025 |
| 51 | Rewind/Limitless 9to5Mac | https://9to5mac.com/2025/12/05/rewind-limitless-meta-acquisition/ | 5 Dec 2025 |
| 52 | Pixel Screenshots cloud | https://9to5google.com/2026/06/17/pixel-screenshots-cloud-ai/ | 17 Jun 2026 |
| 53 | Pixel Screenshots AA | https://www.androidauthority.com/pixel-screenshots-cloud-processing-3678819/ | 18 Jun 2026 |
| 54 | Pixel backlash | https://www.androidauthority.com/google-made-me-turn-off-pixel-screenshots-3680197/ | 24 Jun 2026 |
| 55 | Observer AI GitHub | https://github.com/Roy3838/Observer | Live Oct 2026 |
| 56 | BhaiFinder pricing | https://www.stork.ai/en/bhaifinder | 30 Sep 2026 |
| 57 | Recall.it pricing | https://www.recall.it/pricing | Live Oct 2026 |
| 58 | Readwise pricing check | https://www.marqly.com/blog/readwise-reader-review-2026 | 6 Oct 2026 |
| 59 | mymind pricing | https://mymind.com/pricing | Live |
| 60 | Glasp price hike | https://blog.glasp.co/price-update-2026-05-01/ | 27 Mar 2026 |
| 61 | Raindrop pricing | https://contextbolt.com/blog/raindrop-pricing/ | 10 Sep 2026 |
| 62 | Screenpipe GitHub | https://github.com/screenpipe/screenpipe | 17 Oct 2026 |
| 63 | Screenpipe license | https://screenpipe.com/blog/screenpipe-license-update | 9 Jun 2026 |
| 64 | Screenpipe prices (aggregator) | https://www.positioniseverything.net/tools/screenpipe/pricing/ | 16 Oct 2026 (cites 1 Oct 2026) |
| 65 | Instagram Terms | https://help.instagram.com/581066165581870/ | Live 17 Oct 2026 |
| 66 | DPDP Rules text | https://cadp.in/resources/official-texts/dpdp-rules-2025/ | 15 Oct 2026 |
| 67 | MeitY DPDP Rules listing | https://www.meity.gov.in/documents/act-and-policies/digital-personal-data-protection-rules-2025-gDOxUjMtQWa | Published 14 Nov 2025 |
| 68 | DPDP commencement PDF | https://www.medianama.com/wp-content/uploads/2025/11/Date-of-applicability-of-Rules.pdf | 14 Nov 2025 |
| 69 | Mondaq DPDP notify | https://www.mondaq.com/india/data-protection/1708932/the-digital-personal-data-protection-rules-2025-a-new-digital-frontier | 24 Nov 2025 |
| 70 | Play India alternative billing | https://support.google.com/googleplay/android-developer/answer/13306652 | Live |
| 71 | Play fee split Verge | https://www.theverge.com/policy/956296/google-play-app-store-alternative-billing-fee-antitrust | 24 Jun 2026 |
| 72 | Play billing AA | https://www.androidauthority.com/google-play-billing-changes-3681025/ | 24 Jun 2026 |
| 73 | Play fees India RoW 2027 | https://ecorpit.com/google-play-service-fee-split-2026-app-economics/ | 19 Jul 2026 |
| 74 | Obz AI | https://obz.ai/ | Live |
| 75 | Obz AI docs | https://docs.obz.ai/introduction | 7 Jan 2026 |
| 76 | UK TM Obsera | https://www.ipo.gov.uk/t-tmj/tm-journals/2026-024/UK00004399049.html | 12 Jun 2026 |
| 77 | IzzyOnDroid Sep 2026 | https://codeberg.org/IzzyOnDroid/iod-monthly-report/src/branch/main/reports/monthly_report_2026-09.md | Generated 8 Oct 2026 |
| 78 | F-Droid 2.0 | https://f-droid.org/2026/09/24/f-droid-2.0-a-new-chapter-for-android-freedom.html | 24 Sep 2026 |
| 79 | Play AccessibilityService | https://support.google.com/googleplay/android-developer/answer/10964491 | Live |
| 80 | ML Kit text recognition | https://developers.google.com/ml-kit/vision/text-recognition/v2/android | Live |
| 81 | whisper.cpp | https://github.com/ggml-org/whisper.cpp | Live |
| 82 | Instagram saved-posts 30-day history | https://crownmediacreative.com/how-to-find-saved-posts-on-instagram/ | 9 Oct 2026 |
| 83 | Saved Reels how-to | https://www.lilachbullock.com/how-to-find-your-saved-reels-on-instagram/ | 3 Oct 2026 |
| 84 | Pixel Screenshots help | https://support.google.com/pixelphone/answer/15312581 | Live |
| 85 | Keep Android Open / F-Droid campaign | https://keepandroidopen.org/ | Linked from F-Droid 24 Sep 2026 |
| 86 | CWS register (India listed; one-time fee) | https://developer.chrome.com/docs/webstore/register | Last updated 13 Feb 2024 |
| 87 | CWS $5 fee (secondary) | https://extensionbooster.net/blog/chrome-web-store-developer-registration-fee-2026-guide/ | 22 Jun 2026 |
| 88 | CWS $5 fee (secondary) | https://www.extensionradar.com/blog/chrome-web-store-developer-fee-2026 | 16 Feb 2026 |
| 89 | CWS review updates (slots, Featured sunset) | https://developer.chrome.com/blog/cws-review-updates-2026 | 20 Aug 2026 |
| 90 | Chrome-Stats Featured snapshot | https://chrome-stats.com/blog/2026/07/20/featured-badge-maintenance-signal | 20 Jul 2026 |
| 91 | CWS 2-Step Verification policy | https://developer.chrome.com/docs/webstore/program-policies/two-step-verification | 1 Nov 2022 |
| 92 | CWS API (2SV to publish) | https://developer.chrome.com/docs/webstore/using-api | 8 Oct 2026 |
| 93 | CWS quality guidelines (single purpose) | https://developer.chrome.com/docs/webstore/program-policies/quality-guidelines | 10 Jul 2024 |
| 94 | CWS quality FAQ | https://developer.chrome.com/docs/webstore/program-policies/quality-guidelines-faq | 10 Jul 2024 |
| 95 | CWS use of permissions | https://developer.chrome.com/docs/webstore/program-policies/permissions | 1 Nov 2022 |
| 96 | CWS accepting payment | https://developer.chrome.com/docs/webstore/program-policies/accepting-payment | 1 Nov 2022 |
| 97 | CWS dashboard distribution / IAP badge | https://developer.chrome.com/docs/webstore/cws-dashboard-distribution | Date not shown |
| 98 | CWS docs index (payments deprecation listed) | https://developer.chrome.com/docs/webstore/ | Updated 28 Apr 2026 |
| 99 | CWS policy updates (Limited Use 1 Aug 2026) | https://developer.chrome.com/blog/cws-policy-updates-2026 | 1 Jul 2026 |
| 100 | CWS troubleshooting (YouTube download) | https://developer.chrome.com/docs/webstore/troubleshooting | Seen 17 Oct 2026 (updated 20 Jul 2026 in this fetch) |
| 101 | CWS discovery (editors; cannot pay) | https://developer.chrome.com/docs/webstore/discovery | 21 Mar 2022 |
| 102 | chrome.sidePanel API | https://developer.chrome.com/docs/extensions/reference/api/sidePanel | Last updated 11 Sep 2026 |
| 103 | chrome.commands API | https://developer.chrome.com/docs/extensions/reference/api/commands | Last updated 11 Sep 2026 |
| 104 | WCAG 2.2 what’s new (W3C WAI) | https://www.w3.org/WAI/standards-guidelines/wcag/new-in-22/ | Rec 5 Oct 2023; page seen 17 Oct 2026 |
| 105 | WCAG 2.2 AA additions (secondary) | https://www.hemispheredm.com/wcag-2-2-explained | Updated 13 Aug 2026 |
| 106 | Core Web Vitals thresholds 2026 | https://webhelpagency.com/blog/core-web-vitals-2026/ | 7 Jul 2026 |
| 107 | web.dev vitals | https://web.dev/articles/vitals | Live / seen 17 Oct 2026 |
| 108 | Product Hunt prepare for launch | https://www.producthunt.com/launch/preparing-for-launch | Seen 17 Oct 2026 |
| 109 | Product Hunt launch guide | https://www.producthunt.com/launch | Seen 17 Oct 2026 |
| 110 | Product Hunt featuring guidelines | https://help.producthunt.com/en/articles/9883485-product-hunt-featuring-guidelines | Date not shown |
| 111 | Product Hunt relaunch rules (secondary, cites 7 Jul 2026 help) | https://www.agentlaun.ch/blogs/product-hunt-relaunch-rules | 5 Oct 2026 |
| 112 | ReciMe PH launch | https://www.hunted.space/product/recime-2/launches/recime-2 | 12 Oct 2023 |
| 113 | Show HN guidelines | https://news.ycombinator.com/showhn.html | Date not shown |
| 114 | Play expanded billing (10%+5%) | https://android-developers.googleblog.com/2026/06/play-expanded-billing.html | Seen 16 Oct 2026 (announced Jun 2026) |
| 115 | F-Droid Inclusion Policy | https://f-droid.org/docs/Inclusion_Policy/ | Seen 16 Oct 2026 |
| 116 | F-Droid TWIF | https://fdroid.gitlab.io/fdroid-website/2026/10/16/twif.html | 16 Oct 2026 |
| 117 | MWM retention glossary | https://mwm.ai/glossary/retention | State of April 2026 |
| 118 | MWM retention guide | https://mwm.ai/guides/how-to-improve-app-retention | 9 Jun 2026 |
| 119 | Prooflytics D7/D30 | https://prooflytics.io/blog/d7-d30-retention-benchmarks-by-app-category | 21 Apr 2026 |
| 120 | MCP spec 2026-07-28 | https://blog.modelcontextprotocol.io/posts/2026-07-28/ | 28 Jul 2026 |
| 121 | YouTube timestamp links (secondary) | https://www.sendible.com/insights/youtube-timestamp-link | 1 Oct 2026 |
| 122 | YouTube timestamp (secondary) | https://youtubeview.app/blog/youtube-timestamp-link | 11 Oct 2026 |
| 123 | Notion API intro (version header) | https://developers.notion.com/reference/intro | Live Oct 2026; version 2026-03-11 |
| 124 | W3C TAG installing websites draft | https://www.w3.org/blog/2026/installing-websites-must-be-easy/ | 16 Oct 2026 |
| 125 | Hitesh Choudhary X (self-reported YT scale) | https://x.com/Hiteshdotcom/status/2101983978194653589 | 21 Sep 2026 |
| 126 | Obtainium 35-star claim (X) | https://x.com/arferrari/status/2108913902448718258 | 10 Oct 2026 |
| 127 | Better Launch PH 12:01 PT | https://www.betterlaunch.co/blog/how-to-launch-on-product-hunt | 3 Oct 2026 |
| 128 | Signals PH 12:01 AM PT | https://signals.sh/blog/what-time-to-launch-on-product-hunt-pst | 23 Apr 2026 / updated 12 May 2026 |
| 129 | hunted.space daily PH | https://www.hunted.space/history | Seen 17 Oct 2026 |
| 130 | Raindrop pricing | https://contextbolt.com/blog/raindrop-pricing/ | 10 Sep 2026 |
| 131 | BhaiFinder pricing | https://www.stork.ai/en/bhaifinder | 30 Sep 2026 |
| 132 | Readwise review / price | https://www.marqly.com/blog/readwise-reader-review-2026 | 6 Oct 2026 |
| 133 | IzzyOnDroid Sep 2026 report | https://codeberg.org/IzzyOnDroid/iod-monthly-report/src/branch/main/reports/monthly_report_2026-09.md | Generated 8 Oct 2026 |
| 134 | F-Droid 2.0 | https://f-droid.org/2026/09/24/f-droid-2.0-a-new-chapter-for-android-freedom.html | 24 Sep 2026 |
| 135 | Meta/IPSOS India Reels | https://about.fb.com/news/2026/06/reels-is-shaping-indias-video-first-future-across-gen-z-women-bharat/ | 3 Jun 2026 |
| 136 | Pixel Screenshots backlash | https://www.androidauthority.com/google-made-me-turn-off-pixel-screenshots-3680197/ | 24 Jun 2026 |
| 137 | Limitless/Meta | https://techcrunch.com/2025/12/05/meta-acquires-ai-device-startup-limitless/ | 5 Dec 2025 |
| 138 | CWS Featured badges original | https://blog.google/products/chrome/find-great-extensions-new-chrome-web-store-badges/ | Date not shown (original announcement) |
| 139 | Play India alternative billing | https://support.google.com/googleplay/android-developer/answer/13306652 | Live |
| 140 | ecorpit Play fee split | https://ecorpit.com/google-play-service-fee-split-2026-app-economics/ | 19 Jul 2026 |
| 141 | Obsidian Sync product page (AES-256 E2E, Standard $4 / Plus $8 annual) | https://obsidian.md/sync | Seen 17 Oct 2026 |
| 142 | Obsidian pricing (aggregators verified) | https://sourcetier.com/pricing/obsidian | Observed 16 Oct 2026 |
| 143 | kepano on Obsidian Sync E2E vs Dropbox/Drive/GitHub | https://x.com/kepano/status/2090281274569380312 | 20 Aug 2026 |
| 144 | kepano: Sync encrypts before leave-device | https://x.com/kepano/status/2048818352953708994 | 27 Apr 2026 |
| 145 | Obsidian Sync options / unrecoverable password (secondary; cites audits) | https://workifylab.com/obsidian-sync-options-2026/ | 31 Aug 2026 |
| 146 | Standard Notes plans (free E2E unlimited device sync; $90 / $120) | https://standardnotes.com/plans | Seen 17 Oct 2026 |
| 147 | Bitwarden pricing (Premium $1.65/mo annual) | https://bitwarden.com/pricing | Seen 17 Oct 2026 |
| 148 | Bitwarden enhanced Premium price post | https://bitwarden.com/blog/bitwarden-launches-enhanced-premium-plan/ | 21 Jan 2026 |
| 149 | Bitwarden Send lifespan (7-day default, max 31, revoke) | https://bitwarden.com/help/send-lifespan/ | Help PDF 16 Sep 2026 |
| 150 | Syncthing untrusted devices (beta; XChaCha20-Poly1305 + AES-SIV) | https://docs.syncthing.net/users/untrusted.html | 7 Oct 2026 |
| 151 | Syncthing untrusted encryption spec | https://docs.syncthing.net/specs/untrusted.html | 7 Oct 2026 |
| 152 | Excalidraw E2E in the browser (key in URL hash, AES-GCM) | https://plus.excalidraw.com/blog/end-to-end-encryption | 21 Mar 2020 |
| 153 | RFC 3986 URI generic syntax (fragment) | https://www.rfc-editor.org/rfc/rfc3986 | Jan 2005 |
| 154 | Proton Drive password + expiry on share links | https://proton.me/ca/support/password-protect-files-proton-drive | Seen 17 Oct 2026 |
| 155 | Proton Drive mobile share / stop sharing | https://proton.me/support/proton-drive-mobile-share-files-folders | Updated 16 Oct 2026 |
| 156 | Proton Drive search-under-E2E | https://x.com/ProtonDrive/status/2110734481804099638 | 15 Oct 2026 |
| 157 | Ente storage FAQ (10 GB free forever) | https://ente.io/help/photos/faq/storage-and-plans | Seen 17 Oct 2026 |
| 158 | Ente paid SKUs (aggregator citing ente.com) | https://macmyths.com/apps/ente-photos/pricing/ | 15 Oct 2026 |
| 159 | Google Drive application data folder | https://developers.google.com/workspace/drive/api/guides/appdata | Last updated 3 Sep 2026 |
| 160 | Chrome native messaging | https://developer.chrome.com/docs/extensions/develop/concepts/native-messaging | Last updated 16 Sep 2026 |
| 161 | Play store listing best practices (title 30, short 80, full 4000) | https://support.google.com/googleplay/android-developer/answer/13393723 | Seen 17 Oct 2026 |
| 162 | Play add preview assets (screenshots 2–8, 320–3840) | https://support.google.com/googleplay/android-developer/answer/9866151 | Seen 17 Oct 2026 |
| 163 | Play listing assets checklist (icon 512, feature 1024×500) | https://storeshot.ai/blog/play-store-listing-assets-checklist/ | 20 Sep 2026 |
| 164 | Play feature graphic 1024×500 (checked against Play Help Oct 2026) | https://ecranify.com/guides/google-play-feature-graphic | 16 Oct 2026 |
| 165 | Play ASO fields indexed | https://appdrift.co/blog/google-play-seo | 7 Oct 2026, updated 11 Oct 2026 |
| 166 | CWS image sizes 2026 (1280×800, 128×128) | https://cwskit.khanakia.com/chrome-web-store-image-sizes | Last updated Apr 2026 |
| 167 | CWS listing template (name 75, summary 132) | https://raw.githubusercontent.com/GoogleChrome/modern-web-guidance-src/main/skills-src/chrome-extensions/references/webstore/chromewebstore-template.md | Seen 17 Oct 2026 |
| 168 | CWS submit guide (75 / 132 / screenshots) | https://kromio.ai/learn/submit-chrome-web-store | Page 28 Sep 2026; updated Jul 2026 |
| 169 | CWS screenshot row 1280×800 | https://www.versely.studio/blog/chrome-web-store-listing-is-the-screenshot-row | 24 Aug 2026 |
| 170 | Product Hunt launch assets (1270×760, 240×240, tagline 60) | https://submitator.com/blog/product-hunt-launch-assets | 3 Aug 2026 |
| 171 | Show HN guidelines | https://news.ycombinator.com/showhn.html | Date not shown; fetched 17 Oct 2026 |
| 172 | YouTube Data API playlistItems.list (quota 1 unit) | https://developers.google.com/youtube/v3/docs/playlistItems/list | Last updated 14 Sep 2026 |
| 173 | YouTube playlistItems resource | https://developers.google.com/youtube/data/reference/rest/v3/playlistItems | 15 Oct 2026 |
| 174 | IndiaFOSS chapter (2026 26–27 Sep NIMHANS; retrospective 5 Oct) | https://www.fossunited.org/c/indiafoss | Seen 17 Oct 2026 |
| 175 | FOSS United Bengaluru chapter | https://fossunited.org/c/bengaluru | Seen 17 Oct 2026 |
| 176 | FOSS United Delhi meetup 14 Nov 2026 | https://fossunited.org/c/delhi/nov-26 | Seen 17 Oct 2026 |
| 177 | @fossdot Delhi meetup announcement | https://x.com/fossdot/status/2109935155830022158 | 13 Oct 2026 |
| 178 | FOSS United home (Bengaluru 24 Oct 2026 listing) | https://fossunited.org/ | Seen 17 Oct 2026 |
| 179 | r/developersIndia on Drive appDataFolder quota | https://www.reddit.com/r/developersIndia/comments/1spzhw5/google_has_free_peruser_storage_built_in_and/ | 19 Apr 2026 |

Implementation references retained from v1.2 (still valid starting points): Android overlay permission, FGS background-start restrictions, Room FTS4, share `ACTION_SEND`, Tesseract, ScreenCaptureKit, Windows UWP screen capture, xdg-desktop-portal.

---

*End of OBZRAi PRD v2.1 (17 Oct 2026). Approval point: this document fixes product behavior, the approved overlay, live platform constraints, competition, pricing, D1–D6 recommendations, pending **D7**, the Phase 0–4 plan, the web-grade Hub/extension/marketing-site bar (§21), go-to-market (§22), proposed strength features (§23), and **cross-device continuity (§24)**. Engineering begins with Android spikes S1–S4 on the current Kotlin/Compose scaffold. Web-grade, GTM, D7-B, and standalone web-client work do not start before that gate.*
