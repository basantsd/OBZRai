package com.obzrai.observer.feature.ocr

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

/**
 * FR-008 — on-device OCR. Empty/unreadable frames return empty string (never fabricate).
 */
@Singleton
class OcrEngine @Inject constructor() {
    private val recognizer =
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    suspend fun recognize(bitmap: Bitmap): String =
        suspendCancellableCoroutine { cont ->
            val image = InputImage.fromBitmap(bitmap, 0)
            recognizer
                .process(image)
                .addOnSuccessListener { text ->
                    cont.resume(text.text?.trim().orEmpty())
                }.addOnFailureListener {
                    cont.resume("")
                }
        }
}
