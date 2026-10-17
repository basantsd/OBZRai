package com.obzrai.observer.feature.capture

import android.graphics.Bitmap
import android.util.Log
import com.obzrai.observer.feature.overlay.OverlayController
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Spike S3 — clean frame without the floating overlay in the bitmap.
 *
 * Android MediaProjection has no public API to exclude TYPE_APPLICATION_OVERLAY
 * windows from the VirtualDisplay. Product fallback: hide-and-settle.
 *
 * [SETTLE_MS] leaves a brief gap where the ring is absent (documented OEM limit /
 * approved fallback per PRD §19.1).
 */
@Singleton
class CleanFrameCapture @Inject constructor(
    private val mediaProjectionGateway: MediaProjectionGateway,
    private val overlayController: OverlayController,
) {
    data class Result(
        val bitmap: Bitmap?,
        val strategy: Strategy,
        val settleMs: Long,
        val overlayWasVisible: Boolean,
        val notes: String,
    )

    enum class Strategy {
        /** Preferred if OEM/platform ever exposes exclusion — not available on stock AOSP. */
        EXCLUSION_API,

        /** Approved fallback: hide overlay, wait, capture, restore. */
        HIDE_AND_SETTLE,
    }

    suspend fun captureCleanFrame(settleMs: Long = SETTLE_MS): Result {
        val overlayWasVisible = overlayController.isVisible()
        if (overlayWasVisible) {
            overlayController.hide()
            delay(settleMs)
        }
        val bitmap = mediaProjectionGateway.captureLatestFrame()
        if (overlayWasVisible) {
            overlayController.show()
        }
        val notes =
            if (bitmap == null) {
                "No frame available (projection inactive or ImageReader empty)"
            } else {
                "Hide-and-settle used; Exclusion API unavailable on stock MediaProjection"
            }
        Log.i(TAG, "S3 clean frame: strategy=HIDE_AND_SETTLE settleMs=$settleMs ok=${bitmap != null}")
        return Result(
            bitmap = bitmap,
            strategy = Strategy.HIDE_AND_SETTLE,
            settleMs = settleMs,
            overlayWasVisible = overlayWasVisible,
            notes = notes,
        )
    }

    companion object {
        private const val TAG = "CleanFrameS3"
        /** ~2–3 frames at 60 Hz; short enough to feel seamless, long enough for compositor. */
        const val SETTLE_MS = 50L
    }
}
