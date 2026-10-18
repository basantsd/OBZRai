package com.obzrai.observer.feature.capture

import android.graphics.Bitmap
import android.graphics.Color
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

/**
 * FR-052 / FR-055 — detect blank/black projection frames (FLAG_SECURE / DRM).
 * Never bypass secure surfaces; only label honestly.
 */
@Singleton
class BlackFrameDetector @Inject constructor() {
    data class Result(
        val blocked: Boolean,
        val meanLuma: Double,
        val sampleCount: Int,
        val notes: String,
    )

    fun analyze(bitmap: Bitmap, sampleStep: Int = 16): Result {
        if (bitmap.width <= 0 || bitmap.height <= 0) {
            return Result(true, 0.0, 0, "empty bitmap")
        }
        var sum = 0.0
        var n = 0
        var nearBlack = 0
        var y = 0
        while (y < bitmap.height) {
            var x = 0
            while (x < bitmap.width) {
                val c = bitmap.getPixel(x, y)
                val r = Color.red(c)
                val g = Color.green(c)
                val b = Color.blue(c)
                val luma = 0.2126 * r + 0.7152 * g + 0.0722 * b
                sum += luma
                n++
                if (luma < 12.0) nearBlack++
                x += sampleStep
            }
            y += sampleStep
        }
        val mean = if (n == 0) 0.0 else sum / n
        val blackRatio = if (n == 0) 1.0 else nearBlack.toDouble() / n
        val blocked = mean < 18.0 && blackRatio > 0.92
        return Result(
            blocked = blocked,
            meanLuma = mean,
            sampleCount = n,
            notes =
                if (blocked) {
                    "capture_blocked — black/blank frame (DRM/FLAG_SECURE likely); no green Saved for screenshot"
                } else {
                    "frame has visible content (meanLuma=${"%.1f".format(mean)})"
                },
        )
    }

    fun nearlyIdentical(
        a: Bitmap,
        b: Bitmap,
        sampleStep: Int = 32,
    ): Boolean {
        if (a.width != b.width || a.height != b.height) return false
        var diff = 0L
        var n = 0
        var y = 0
        while (y < a.height) {
            var x = 0
            while (x < a.width) {
                val ca = a.getPixel(x, y)
                val cb = b.getPixel(x, y)
                diff += abs(Color.red(ca) - Color.red(cb)) +
                    abs(Color.green(ca) - Color.green(cb)) +
                    abs(Color.blue(ca) - Color.blue(cb))
                n++
                x += sampleStep
            }
            y += sampleStep
        }
        val meanDiff = if (n == 0) 0.0 else diff.toDouble() / n
        return meanDiff < 4.0
    }
}
