package com.obzrai.observer.feature.session

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import com.obzrai.observer.core.model.SessionState
import com.obzrai.observer.feature.capture.MediaProjectionGateway
import com.obzrai.observer.feature.overlay.OverlayController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class SessionStateMachineTest {
    @Test
    fun startAndStop_movesThroughActiveThenStopped() {
        val gateway = FakeGateway(consent = true)
        val overlay = FakeOverlay(canDraw = true)
        val controller =
            ObservationSessionController(
                context = RuntimeEnvironment.getApplication(),
                mediaProjectionGateway = gateway,
                overlayController = overlay,
            )

        assertEquals(SessionState.STOPPED, controller.state.value)
        val action = controller.requestStart()
        assertEquals(ObservationSessionController.StartAction.STARTED, action)
        assertEquals(SessionState.ACTIVE, controller.state.value)
        assertTrue(overlay.isVisible())
        controller.stop("test")
        assertEquals(SessionState.STOPPED, controller.state.value)
    }

    @Test
    fun startWithoutConsent_requestsProjectionConsent() {
        val gateway = FakeGateway(consent = false)
        val overlay = FakeOverlay(canDraw = true)
        val controller =
            ObservationSessionController(
                context = RuntimeEnvironment.getApplication(),
                mediaProjectionGateway = gateway,
                overlayController = overlay,
            )

        val action = controller.requestStart()
        assertEquals(
            ObservationSessionController.StartAction.REQUEST_PROJECTION_CONSENT,
            action,
        )
        assertEquals(SessionState.NEEDS_PERMISSION, controller.state.value)
    }

    @Test
    fun startWithoutOverlay_requestsOverlayPermission() {
        val gateway = FakeGateway(consent = true)
        val overlay = FakeOverlay(canDraw = false)
        val controller =
            ObservationSessionController(
                context = RuntimeEnvironment.getApplication(),
                mediaProjectionGateway = gateway,
                overlayController = overlay,
            )

        val action = controller.requestStart()
        assertEquals(
            ObservationSessionController.StartAction.REQUEST_OVERLAY_PERMISSION,
            action,
        )
        assertEquals(SessionState.NEEDS_PERMISSION, controller.state.value)
    }

    private class FakeGateway(
        private var consent: Boolean,
    ) : MediaProjectionGateway {
        private val _active = MutableStateFlow(false)
        override val isSessionActive: StateFlow<Boolean> = _active.asStateFlow()
        private val _stop = MutableStateFlow<String?>(null)
        override val lastStopReason: StateFlow<String?> = _stop.asStateFlow()
        private val _frames = MutableStateFlow(0)
        override val frameCount: StateFlow<Int> = _frames.asStateFlow()
        private val _info = MutableStateFlow<MediaProjectionGateway.FrameInfo?>(null)
        override val lastFrameInfo: StateFlow<MediaProjectionGateway.FrameInfo?> = _info.asStateFlow()

        override fun hasValidConsent(): Boolean = consent

        override fun createScreenCaptureIntent(context: Context): Intent = Intent()

        override fun onConsentResult(resultCode: Int, data: Intent?): Boolean {
            consent = true
            return true
        }

        override fun clearConsent() {
            consent = false
        }

        override fun startCaptureService(context: Context) {
            _active.value = true
        }

        override fun stopSession(reason: String) {
            _active.value = false
            _stop.value = reason
        }

        override suspend fun captureLatestFrame(): Bitmap? = null
    }

    private class FakeOverlay(
        private val canDraw: Boolean,
    ) : OverlayController {
        private val _showing = MutableStateFlow(false)
        override val isShowing: StateFlow<Boolean> = _showing.asStateFlow()

        override fun canDrawOverlays(): Boolean = canDraw

        override fun overlaySettingsIntent(): Intent = Intent()

        override fun isVisible(): Boolean = _showing.value

        override fun show() {
            _showing.value = true
        }

        override fun hide() {
            _showing.value = false
        }
    }
}
