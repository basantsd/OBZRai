package com.obzrai.observer.feature.session

import android.content.Context
import com.obzrai.observer.core.model.SessionState
import com.obzrai.observer.feature.capture.MediaProjectionGateway
import com.obzrai.observer.feature.capture.ObservationCaptureService
import com.obzrai.observer.feature.overlay.OverlayController
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Session state machine for Observe Mode.
 * Wires MediaProjection consent + FGS (S1) and overlay (S2).
 */
@Singleton
class ObservationSessionController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mediaProjectionGateway: MediaProjectionGateway,
    private val overlayController: OverlayController,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _state = MutableStateFlow(SessionState.STOPPED)
    val state: StateFlow<SessionState> = _state.asStateFlow()

    private val _needsProjectionConsent = MutableStateFlow(false)
    val needsProjectionConsent: StateFlow<Boolean> = _needsProjectionConsent.asStateFlow()

    private val _needsOverlayPermission = MutableStateFlow(false)
    val needsOverlayPermission: StateFlow<Boolean> = _needsOverlayPermission.asStateFlow()

    init {
        scope.launch {
            mediaProjectionGateway.isSessionActive.collect { active ->
                if (!active && _state.value == SessionState.ACTIVE) {
                    val reason = mediaProjectionGateway.lastStopReason.value ?: "projection_ended"
                    overlayController.hide()
                    _state.value = SessionState.STOPPED
                    _needsProjectionConsent.value = false
                    // Chip / lock stop path
                    if (reason.contains("on_stop") || reason.contains("lock") || reason.contains("chip")) {
                        // already stopped via callback
                    }
                }
            }
        }
    }

    /**
     * User tapped Start. Returns true if Activity should launch the OS capture consent Intent.
     */
    fun requestStart(): StartAction {
        when (_state.value) {
            SessionState.STOPPED,
            SessionState.NEEDS_PERMISSION,
            SessionState.PAUSED,
            -> {
                if (!overlayController.canDrawOverlays()) {
                    _state.value = SessionState.NEEDS_PERMISSION
                    _needsOverlayPermission.value = true
                    return StartAction.REQUEST_OVERLAY_PERMISSION
                }
                _needsOverlayPermission.value = false
                if (!mediaProjectionGateway.hasValidConsent()) {
                    _state.value = SessionState.NEEDS_PERMISSION
                    _needsProjectionConsent.value = true
                    return StartAction.REQUEST_PROJECTION_CONSENT
                }
                beginActiveSession()
                return StartAction.STARTED
            }
            SessionState.STARTING,
            SessionState.ACTIVE,
            SessionState.STOPPING,
            -> return StartAction.IGNORED
        }
    }

    fun onProjectionConsentGranted() {
        _needsProjectionConsent.value = false
        beginActiveSession()
    }

    fun onProjectionConsentDenied() {
        _needsProjectionConsent.value = false
        mediaProjectionGateway.clearConsent()
        _state.value = SessionState.NEEDS_PERMISSION
    }

    private fun beginActiveSession() {
        _state.value = SessionState.STARTING
        mediaProjectionGateway.startCaptureService(context)
        overlayController.show()
        _state.value = SessionState.ACTIVE
    }

    fun pause() {
        if (_state.value == SessionState.ACTIVE) {
            _state.value = SessionState.PAUSED
            overlayController.hide()
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
                ObservationCaptureService.stop(context)
                mediaProjectionGateway.stopSession(reason)
                overlayController.hide()
                _needsProjectionConsent.value = false
                _state.value = SessionState.STOPPED
            }
        }
    }

    fun markPermissionRequired() {
        _state.update { SessionState.NEEDS_PERMISSION }
    }

    enum class StartAction {
        STARTED,
        REQUEST_PROJECTION_CONSENT,
        REQUEST_OVERLAY_PERMISSION,
        IGNORED,
    }
}
