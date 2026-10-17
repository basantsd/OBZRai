package com.obzrai.observer.feature.capture

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import com.obzrai.observer.feature.overlay.OverlayController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class CleanFrameCaptureTest {
    @Test
    fun hideAndSettle_hidesThenRestoresOverlay() =
        runBlocking {
            val overlay = TrackingOverlay(visible = true)
            val gateway = FrameGateway()
            val capture = CleanFrameCapture(gateway, overlay)
            val result = capture.captureCleanFrame(settleMs = 1L)
            assertEquals(CleanFrameCapture.Strategy.HIDE_AND_SETTLE, result.strategy)
            assertTrue(result.overlayWasVisible)
            assertTrue(overlay.hideCount >= 1)
            assertTrue(overlay.showCount >= 1)
            assertTrue(overlay.isVisible())
            assertTrue(result.notes.contains("Hide-and-settle"))
        }

    @Test
    fun whenOverlayHidden_doesNotToggle() =
        runBlocking {
            val overlay = TrackingOverlay(visible = false)
            val gateway = FrameGateway()
            val capture = CleanFrameCapture(gateway, overlay)
            val result = capture.captureCleanFrame(settleMs = 1L)
            assertFalse(result.overlayWasVisible)
            assertEquals(0, overlay.hideCount)
            assertEquals(0, overlay.showCount)
        }

    private class TrackingOverlay(
        visible: Boolean,
    ) : OverlayController {
        private val _showing = MutableStateFlow(visible)
        override val isShowing: StateFlow<Boolean> = _showing.asStateFlow()
        var hideCount = 0
        var showCount = 0

        override fun canDrawOverlays(): Boolean = true

        override fun overlaySettingsIntent(): Intent = Intent()

        override fun isVisible(): Boolean = _showing.value

        override fun show() {
            showCount++
            _showing.value = true
        }

        override fun hide() {
            hideCount++
            _showing.value = false
        }
    }

    private class FrameGateway : MediaProjectionGateway {
        private val _active = MutableStateFlow(true)
        override val isSessionActive: StateFlow<Boolean> = _active.asStateFlow()
        private val _stop = MutableStateFlow<String?>(null)
        override val lastStopReason: StateFlow<String?> = _stop.asStateFlow()
        private val _frames = MutableStateFlow(1)
        override val frameCount: StateFlow<Int> = _frames.asStateFlow()
        private val _info = MutableStateFlow<MediaProjectionGateway.FrameInfo?>(null)
        override val lastFrameInfo: StateFlow<MediaProjectionGateway.FrameInfo?> = _info.asStateFlow()

        override fun hasValidConsent(): Boolean = true

        override fun createScreenCaptureIntent(context: Context): Intent = Intent()

        override fun onConsentResult(resultCode: Int, data: Intent?): Boolean = true

        override fun clearConsent() = Unit

        override fun startCaptureService(context: Context) = Unit

        override fun stopSession(reason: String) = Unit

        override suspend fun captureLatestFrame(): Bitmap =
            Bitmap.createBitmap(8, 8, Bitmap.Config.ARGB_8888)
    }
}
