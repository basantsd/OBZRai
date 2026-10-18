package com.obzrai.observer.feature.capture

import android.graphics.Bitmap
import android.graphics.Color
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class BlackFrameDetectorTest {
    private val detector = BlackFrameDetector()

    @Test
    fun blackBitmap_isBlocked() {
        val bmp = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888)
        bmp.eraseColor(Color.BLACK)
        assertTrue(detector.analyze(bmp).blocked)
    }

    @Test
    fun brightBitmap_isNotBlocked() {
        val bmp = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888)
        bmp.eraseColor(Color.WHITE)
        assertFalse(detector.analyze(bmp).blocked)
    }
}
