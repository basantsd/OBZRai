package com.obzrai.observer.feature.capture

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Test double for unit tests. Production binds [DefaultMediaProjectionGateway].
 */
@Singleton
class StubMediaProjectionGateway @Inject constructor() : MediaProjectionGateway {
    private val _isSessionActive = MutableStateFlow(false)
    override val isSessionActive: StateFlow<Boolean> = _isSessionActive.asStateFlow()

    private val _lastStopReason = MutableStateFlow<String?>(null)
    override val lastStopReason: StateFlow<String?> = _lastStopReason.asStateFlow()

    private val _frameCount = MutableStateFlow(0)
    override val frameCount: StateFlow<Int> = _frameCount.asStateFlow()

    private val _lastFrameInfo = MutableStateFlow<MediaProjectionGateway.FrameInfo?>(null)
    override val lastFrameInfo: StateFlow<MediaProjectionGateway.FrameInfo?> =
        _lastFrameInfo.asStateFlow()

    private var consentOk: Boolean = true

    fun setConsentForTest(ok: Boolean) {
        consentOk = ok
    }

    override fun hasValidConsent(): Boolean = consentOk

    override fun createScreenCaptureIntent(context: Context): Intent = Intent()

    override fun onConsentResult(resultCode: Int, data: Intent?): Boolean {
        consentOk = resultCode == Activity.RESULT_OK
        return consentOk
    }

    override fun clearConsent() {
        consentOk = false
    }

    override fun startCaptureService(context: Context) {
        _isSessionActive.value = true
        _frameCount.value = 0
        Log.i(TAG, "Stub capture service started")
    }

    override fun stopSession(reason: String) {
        _isSessionActive.value = false
        _lastStopReason.value = reason
        Log.i(TAG, "Stub capture stopped: $reason")
    }

    override suspend fun captureLatestFrame(): Bitmap? = null

    companion object {
        private const val TAG = "MediaProjectionStub"
    }
}
