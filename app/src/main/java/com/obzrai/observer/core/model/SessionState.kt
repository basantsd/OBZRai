package com.obzrai.observer.core.model

/**
 * Observation session state machine (PRD §9.3).
 * STOPPED → NEEDS_PERMISSION → STARTING → ACTIVE → PAUSED/STOPPING → STOPPED
 */
enum class SessionState {
    STOPPED,
    NEEDS_PERMISSION,
    STARTING,
    ACTIVE,
    PAUSED,
    STOPPING,
}

/**
 * Candidate lifecycle stub (PRD §9.3). Full matcher lands after spike.
 */
enum class CandidateState {
    NONE,
    DETECTED,
    EVALUATING,
    MATCHED,
    CAPTURING,
    PROCESSING,
    SAVED,
    SKIPPED,
    EXPIRED,
    FAILED,
}

enum class AudioStatus {
    NOT_REQUESTED,
    AVAILABLE,
    CAPTURING,
    BLOCKED_BY_SOURCE,
    NO_SIGNAL,
    ERROR,
}

enum class TranscriptStatus {
    PENDING,
    COMPLETE,
    PARTIAL,
    UNAVAILABLE,
    FAILED,
}

enum class SourceUrlOrigin {
    NONE,
    SHARE_SHEET,
    DEEP_LINK,
    EXTRACTED_FROM_SCREEN,
}

enum class MatchSensitivity {
    CONSERVATIVE,
    BALANCED,
    BROAD,
}
