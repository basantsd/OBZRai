package com.obzrai.observer.feature.overlay

import android.util.DisplayMetrics
import kotlin.math.roundToInt

/**
 * S2 geometry helpers — 28 dp ring, edge-clipped so ~40% sits outside the display.
 * Validated conceptually at densities 1.0 / 2.0 / 3.0 (mdpi / xhdpi / xxhdpi+).
 */
object OverlayGeometry {
    const val RING_DP = 28f
    const val GLYPH_DP = 11f
    const val STROKE_DP = 2.5f

    /** Fraction of ring diameter that hangs off-screen when snapped. */
    const val EDGE_CLIP_FRACTION = 0.40f

    /** Touch target padding beyond the ring (dp) for drag affordance. */
    const val TOUCH_PAD_DP = 8f

    fun ringPx(metrics: DisplayMetrics): Int = dpToPx(RING_DP, metrics)

    fun glyphPx(metrics: DisplayMetrics): Int = dpToPx(GLYPH_DP, metrics)

    fun windowSizePx(metrics: DisplayMetrics): Int =
        dpToPx(RING_DP + TOUCH_PAD_DP * 2f, metrics)

    fun edgeClipOffsetPx(metrics: DisplayMetrics): Int =
        (ringPx(metrics) * EDGE_CLIP_FRACTION).roundToInt()

    fun dpToPx(dp: Float, metrics: DisplayMetrics): Int =
        (dp * metrics.density).roundToInt()

    /**
     * Snap [rawX] to left or right edge with clip offset.
     * Returns WindowManager x (can be negative for left clip).
     */
    fun snapX(
        rawX: Int,
        windowWidth: Int,
        screenWidth: Int,
        clipPx: Int,
    ): Int {
        val center = rawX + windowWidth / 2
        return if (center < screenWidth / 2) {
            -clipPx
        } else {
            screenWidth - windowWidth + clipPx
        }
    }

    data class DensityReport(
        val densityBucket: String,
        val density: Float,
        val ringPx: Int,
        val glyphPx: Int,
        val windowPx: Int,
        val clipPx: Int,
    )

    fun densityReport(metrics: DisplayMetrics): DensityReport {
        val d = metrics.density
        val bucket =
            when {
                d < 1.5f -> "mdpi(~1.0)"
                d < 2.5f -> "xhdpi(~2.0)"
                else -> "xxhdpi+(~3.0)"
            }
        return DensityReport(
            densityBucket = bucket,
            density = d,
            ringPx = ringPx(metrics),
            glyphPx = glyphPx(metrics),
            windowPx = windowSizePx(metrics),
            clipPx = edgeClipOffsetPx(metrics),
        )
    }
}
