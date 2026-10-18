package com.obzrai.observer.feature.overlay

import android.util.DisplayMetrics
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class OverlayGeometryTest {
    @Test
    fun ringPx_scalesAcrossThreeDensities() {
        val d1 = metrics(1.0f)
        val d2 = metrics(2.0f)
        val d3 = metrics(3.0f)
        assertEquals(28, OverlayGeometry.ringPx(d1))
        assertEquals(56, OverlayGeometry.ringPx(d2))
        assertEquals(84, OverlayGeometry.ringPx(d3))
        assertEquals(11, OverlayGeometry.glyphPx(d1))
        assertEquals(22, OverlayGeometry.glyphPx(d2))
        assertEquals(33, OverlayGeometry.glyphPx(d3))
    }

    @Test
    fun snapX_clipsOffLeftOrRightEdge() {
        val window = 44
        val screen = 1080
        val clip = 11
        val left = OverlayGeometry.snapX(100, window, screen, clip)
        val right = OverlayGeometry.snapX(900, window, screen, clip)
        assertEquals(-clip, left)
        assertEquals(screen - window + clip, right)
        assertTrue(left < 0)
        assertTrue(right + window > screen)
    }

    @Test
    fun densityReport_labelsBuckets() {
        assertTrue(OverlayGeometry.densityReport(metrics(1.0f)).densityBucket.contains("mdpi"))
        assertTrue(OverlayGeometry.densityReport(metrics(2.0f)).densityBucket.contains("xhdpi"))
        assertTrue(OverlayGeometry.densityReport(metrics(3.0f)).densityBucket.contains("xxhdpi"))
    }

    private fun metrics(density: Float): DisplayMetrics =
        DisplayMetrics().apply {
            this.density = density
            densityDpi = (160 * density).toInt()
            widthPixels = 1080
            heightPixels = 1920
        }
}
