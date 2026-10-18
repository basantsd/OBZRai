package com.obzrai.observer.feature.capture

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import kotlinx.coroutines.flow.StateFlow

/**
 * MediaProjection session gateway (spike S1).
 *
 * Consent is one-shot per session (Android 14+). Token must not be cached across sessions.
 * Stop sources include user stop, status-bar chip, and lock (Android 15 QPR1+ via [Callback.onStop]).
 */
interface MediaProjectionGateway {
    val isSessionActive: StateFlow<Boolean>
    val lastStopReason: StateFlow<String?>
    val frameCount: StateFlow<Int>
    val lastFrameInfo: StateFlow<FrameInfo?>

    fun hasValidConsent(): Boolean

    fun createScreenCaptureIntent(context: Context): Intent

    /** Store Activity result; does not start capture until [startCaptureService]. */
    fun onConsentResult(resultCode: Int, data: Intent?): Boolean

    fun clearConsent()

    fun startCaptureService(context: Context)

    fun stopSession(reason: String)

    /**
     * Grab one full-res frame from the active VirtualDisplay pipeline.
     * Used by S1 verification and S3 clean-frame capture.
     */
    suspend fun captureLatestFrame(): Bitmap?

    data class FrameInfo(
        val width: Int,
        val height: Int,
        val path: String?,
        val capturedAtEpochMs: Long,
    )
}
