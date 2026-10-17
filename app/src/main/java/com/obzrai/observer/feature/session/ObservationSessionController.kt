package com.obzrai.observer.feature.session

import com.obzrai.observer.core.model.SessionState
import com.obzrai.observer.feature.capture.MediaProjectionGateway
import com.obzrai.observer.feature.overlay.OverlayController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

/**
 * In-memory session state machine stub.
 * Real MediaProjection consent + foreground service wiring lands in capture spike (S1).
 */
@Singleton
class ObservationSessionController @Inject constructor(
    private val mediaProjectionGateway: MediaProjectionGateway,
    private val overlayController: OverlayController,
) {
    private val _state = MutableStateFlow(SessionState.STOPPED)
    val state: StateFlow<SessionState> = _state.asStateFlow()

    fun requestStart() {
        when (_state.value) {
            SessionState.STOPPED,
            SessionState.NEEDS_PERMISSION,
            SessionState.PAUSED,
            -> {
                if (!mediaProjectionGateway.hasValidConsent()) {
                    _state.value = SessionState.NEEDS_PERMISSION
                    return
                }
                _state.value = SessionState.STARTING
                mediaProjectionGateway.startSessionStub()
                overlayController.showStub()
                _state.value = SessionState.ACTIVE
            }
            SessionState.STARTING,
            SessionState.ACTIVE,
            SessionState.STOPPING,
            -> Unit
        }
    }

    fun pause() {
        if (_state.value == SessionState.ACTIVE) {
            _state.value = SessionState.PAUSED
            overlayController.hideStub()
        }
    }

    fun stop(reason: String = "user") {
        when (_state.value) {
            SessionState.STOPPED -> return
            SessionState.STOPPING -> return
            SessionState.NEEDS_PERMISSION,
            SessionState.STARTING,
            SessionState.ACTIVE,
            SessionState.PAUSED,
            -> {
                _state.value = SessionState.STOPPING
                mediaProjectionGateway.stopSessionStub(reason)
                overlayController.hideStub()
                _state.value = SessionState.STOPPED
            }
        }
    }

    fun markPermissionRequired() {
        _state.update { SessionState.NEEDS_PERMISSION }
    }
}
