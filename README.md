# OBZRAi

On-device Android companion that observes an **authorized** screen session, matches your topics, and saves full frames (plus audio/transcript **when permitted**) into a local, searchable library.

> **Watch anything. Remember what matters.**

This repository currently contains the **greenfield base scaffold** (Kotlin / Compose shell). Capture, OCR, ASR, and product features beyond Home + Library placeholders land after feasibility spikes.

## Stack

| Layer | Choice |
|---|---|
| Language / UI | Kotlin, Jetpack Compose, Material 3 |
| State | ViewModel + StateFlow + Coroutines |
| DI | Hilt |
| Persistence | Room (FTS later) |
| Settings | DataStore (wired later) |
| Capture | MediaProjection FGS + ImageReader pipeline (Phase 0 S1) |
| Overlay | WindowManager edge-clipped ring + Compose placeholder (Phase 0 S2) |
| Min / target SDK | **29** / 35 |

**Not in MVP:** AccessibilityService, cloud sync, accounts, billing.

## Project structure

```text
app/src/main/java/com.obzrai.observer/
  core/
    model/          # SessionState, AudioStatus, TranscriptStatus, …
    storage/        # Room entities, DAOs, LibraryRepository
    di/             # Hilt modules
    ui/             # Theme + ObserverRing placeholder
  feature/
    home/           # Observe Mode shell
    library/        # Searchable library shell
    navigation/     # Home · Library bottom nav
    session/        # Session state machine
    capture/        # MediaProjection gateway + FGS + clean-frame (S1/S3)
    overlay/        # Edge-clipped WindowManager ring (S2)
    audio/          # AudioPlaybackCapture honesty probe (S4)
```

## Phase 0 (S1–S4)

Feasibility spikes are implemented on branch `cursor/phase0-spikes-3a5a`. Results: `docs/phase0-spike-results.md`.

- **S1** MediaProjection session + frame samples + `Callback.onStop`
- **S2** 28 dp edge-clipped overlay, drag/snap, host tappable; reference PNG still missing
- **S3** Hide-and-settle clean frame (Exclusion API unavailable)
- **S4** AudioPlaybackCapture probe with honest third-party logging

**No** `.github/workflows` — local/`./gradlew` only.

## Asset gap

PRD references `assets/approved_overlay_reference.png` (owner-approved blue hollow ring). That file was **not** in the uploaded package. The app uses a **Compose ring placeholder** (`ObserverRing`) until the approved PNG is added. S2 `matches reference` stays **PENDING** until Basant supplies 28 dp PNG (+ optional @2x/@3x).

## Requirements

- JDK 17+
- Android SDK Platform **35**, Build-Tools **35.x**
- Android Studio Ladybug+ (or CLI Gradle)

Set SDK path in `local.properties` (gitignored):

```properties
sdk.dir=/path/to/Android/Sdk
```

## Build & run

```bash
# Unit tests (session state machine)
./gradlew :app:testDebugUnitTest

# Debug APK
./gradlew :app:assembleDebug

# Install on a connected device / emulator
./gradlew :app:installDebug
```

Open the project in Android Studio → Run **app**.

Package id: `com.obzrai.observer` · App name: **OBZRAi** (formerly AI Observer)

## What this scaffold does

- Home + Library navigation shells
- Observe start/stop driving an in-memory **session state machine**
- Room models for saved items, topics, media assets, sessions
- Hilt wiring for DB, capture gateway, overlay controller
- Phase 0 spike harness: projection, overlay, clean-frame, audio honesty probe

## Product constraints (locked)

- Screenshot-first alpha; full-frame originals never auto-cropped
- Fresh MediaProjection consent per session; never silent restart
- No AccessibilityService in default MVP
- On-device by default; no account/cloud required

See the project PRD for full behavior once Researchy / spike work lands.


## Browser extension (Phase 2 draft)

Chromium MV3 side-panel Hub lives in `extension/`. Load unpacked in Chrome. Thin sync uses a **mock** Drive `appDataFolder` adapter (no Google account created). See `docs/phase2-extension-sync.md`.

Shared matcher fixtures: `shared/observer-core/` (S9).
