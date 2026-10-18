package com.obzrai.observer.feature.capture

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

/**
 * Real MediaProjection + ImageReader pipeline for Phase 0 spike S1.
 *
 * Pass criteria exercised here:
 * - Fresh OS consent per session (no token reuse)
 * - Repeated full-res frames with portrait bounds preserved
 * - [MediaProjection.Callback.onStop] for chip / lock stop on 15 QPR1+
 */
@Singleton
class DefaultMediaProjectionGateway @Inject constructor() : MediaProjectionGateway {
    private val _isSessionActive = MutableStateFlow(false)
    override val isSessionActive: StateFlow<Boolean> = _isSessionActive.asStateFlow()

    private val _lastStopReason = MutableStateFlow<String?>(null)
    override val lastStopReason: StateFlow<String?> = _lastStopReason.asStateFlow()

    private val _frameCount = MutableStateFlow(0)
    override val frameCount: StateFlow<Int> = _frameCount.asStateFlow()

    private val _lastFrameInfo = MutableStateFlow<MediaProjectionGateway.FrameInfo?>(null)
    override val lastFrameInfo: StateFlow<MediaProjectionGateway.FrameInfo?> =
        _lastFrameInfo.asStateFlow()

    @Volatile
    private var consentResultCode: Int = Activity.RESULT_CANCELED

    @Volatile
    private var consentData: Intent? = null

    private var mediaProjection: MediaProjection? = null
    private var virtualDisplay: VirtualDisplay? = null
    private var imageReader: ImageReader? = null
    private var captureThread: HandlerThread? = null
    private var captureHandler: Handler? = null

    private val framesWritten = AtomicInteger(0)

    /** Active projection for S4 AudioPlaybackCapture; null when session inactive. */
    fun currentMediaProjection(): MediaProjection? = mediaProjection

    override fun hasValidConsent(): Boolean =
        consentResultCode == Activity.RESULT_OK && consentData != null

    override fun createScreenCaptureIntent(context: Context): Intent {
        val manager =
            context.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        return manager.createScreenCaptureIntent()
    }

    override fun onConsentResult(resultCode: Int, data: Intent?): Boolean {
        if (resultCode != Activity.RESULT_OK || data == null) {
            clearConsent()
            Log.w(TAG, "MediaProjection consent denied or empty")
            return false
        }
        consentResultCode = resultCode
        consentData = data
        Log.i(TAG, "MediaProjection consent stored (one-shot; not cached across sessions)")
        return true
    }

    override fun clearConsent() {
        consentResultCode = Activity.RESULT_CANCELED
        consentData = null
    }

    override fun startCaptureService(context: Context) {
        if (!hasValidConsent()) {
            Log.e(TAG, "startCaptureService without consent")
            return
        }
        val intent =
            Intent(context, ObservationCaptureService::class.java).apply {
                action = ObservationCaptureService.ACTION_START
                putExtra(ObservationCaptureService.EXTRA_RESULT_CODE, consentResultCode)
                putExtra(ObservationCaptureService.EXTRA_RESULT_DATA, consentData)
            }
        context.startForegroundService(intent)
    }

    /**
     * Called from [ObservationCaptureService] after startForeground.
     * Consumes the one-shot consent token.
     */
    fun attachProjection(
        context: Context,
        resultCode: Int,
        data: Intent,
    ): Boolean {
        releasePipeline(reason = null)
        val manager =
            context.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        val projection =
            try {
                manager.getMediaProjection(resultCode, data)
            } catch (e: SecurityException) {
                Log.e(TAG, "getMediaProjection failed (token reuse / invalid)", e)
                clearConsent()
                return false
            } ?: return false

        // Consent is one-shot — clear stored intent so it cannot be reused.
        clearConsent()

        val callback =
            object : MediaProjection.Callback() {
                override fun onStop() {
                    Log.i(TAG, "MediaProjection.Callback.onStop (chip / lock / revoke)")
                    stopSession("projection_callback_on_stop")
                }
            }
        projection.registerCallback(callback, Handler(Looper.getMainLooper()))

        val metrics = displayMetrics(context)
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        val density = metrics.densityDpi

        val thread = HandlerThread("obzrai-capture").also { it.start() }
        val handler = Handler(thread.looper)
        captureThread = thread
        captureHandler = handler

        val reader =
            ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, IMAGE_READER_MAX_IMAGES)
        imageReader = reader

        reader.setOnImageAvailableListener(
            { r ->
                val image = r.acquireLatestImage() ?: return@setOnImageAvailableListener
                try {
                    handleImage(context, image, width, height)
                } finally {
                    image.close()
                }
            },
            handler,
        )

        virtualDisplay =
            projection.createVirtualDisplay(
                "obzrai-s1",
                width,
                height,
                density,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                reader.surface,
                null,
                handler,
            )
        mediaProjection = projection
        _isSessionActive.value = true
        _lastStopReason.value = null
        _frameCount.value = 0
        framesWritten.set(0)
        Log.i(TAG, "VirtualDisplay started ${width}x${height} @ ${density}dpi")
        return true
    }

    override fun stopSession(reason: String) {
        Log.i(TAG, "stopSession: $reason")
        _lastStopReason.value = reason
        releasePipeline(reason)
        clearConsent()
    }

    override suspend fun captureLatestFrame(): Bitmap? =
        suspendCancellableCoroutine { cont ->
            val reader = imageReader
            if (reader == null || !_isSessionActive.value) {
                cont.resume(null)
                return@suspendCancellableCoroutine
            }
            val handler = captureHandler ?: Handler(Looper.getMainLooper())
            handler.post {
                val image = reader.acquireLatestImage()
                if (image == null) {
                    cont.resume(null)
                    return@post
                }
                try {
                    val bitmap = imageToBitmap(image)
                    cont.resume(bitmap)
                } catch (t: Throwable) {
                    Log.e(TAG, "captureLatestFrame failed", t)
                    cont.resume(null)
                } finally {
                    image.close()
                }
            }
        }

    private fun handleImage(
        context: Context,
        image: Image,
        width: Int,
        height: Int,
    ) {
        val count = _frameCount.value + 1
        _frameCount.value = count
        // Persist a sample every N frames for spike evidence (cap disk use).
        if (count == 1 || count % SAMPLE_EVERY_N == 0) {
            if (framesWritten.get() >= MAX_SAVED_FRAMES) return
            val bitmap = imageToBitmap(image) ?: return
            val dir = File(context.filesDir, SPIKE_FRAMES_DIR).also { it.mkdirs() }
            val file = File(dir, "frame_${count}_${width}x${height}.png")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            framesWritten.incrementAndGet()
            _lastFrameInfo.value =
                MediaProjectionGateway.FrameInfo(
                    width = width,
                    height = height,
                    path = file.absolutePath,
                    capturedAtEpochMs = System.currentTimeMillis(),
                )
            Log.i(TAG, "Saved spike frame #$count → ${file.absolutePath}")
            bitmap.recycle()
        } else {
            _lastFrameInfo.value =
                MediaProjectionGateway.FrameInfo(
                    width = width,
                    height = height,
                    path = _lastFrameInfo.value?.path,
                    capturedAtEpochMs = System.currentTimeMillis(),
                )
        }
    }

    private fun imageToBitmap(image: Image): Bitmap? {
        val plane = image.planes.firstOrNull() ?: return null
        val buffer: ByteBuffer = plane.buffer
        val pixelStride = plane.pixelStride
        val rowStride = plane.rowStride
        val rowPadding = rowStride - pixelStride * image.width
        val bitmap =
            Bitmap.createBitmap(
                image.width + rowPadding / pixelStride,
                image.height,
                Bitmap.Config.ARGB_8888,
            )
        bitmap.copyPixelsFromBuffer(buffer)
        return if (rowPadding == 0) {
            bitmap
        } else {
            Bitmap.createBitmap(bitmap, 0, 0, image.width, image.height).also {
                if (it !== bitmap) bitmap.recycle()
            }
        }
    }

    private fun releasePipeline(reason: String?) {
        try {
            virtualDisplay?.release()
        } catch (_: Throwable) {
        }
        virtualDisplay = null
        try {
            imageReader?.close()
        } catch (_: Throwable) {
        }
        imageReader = null
        try {
            mediaProjection?.stop()
        } catch (_: Throwable) {
        }
        mediaProjection = null
        captureThread?.quitSafely()
        captureThread = null
        captureHandler = null
        _isSessionActive.value = false
        if (reason != null) {
            Log.i(TAG, "Pipeline released: $reason")
        }
    }

    private fun displayMetrics(context: Context): DisplayMetrics {
        val metrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        @Suppress("DEPRECATION")
        wm.defaultDisplay.getRealMetrics(metrics)
        return metrics
    }

    companion object {
        private const val TAG = "MediaProjectionS1"
        private const val IMAGE_READER_MAX_IMAGES = 2
        private const val SAMPLE_EVERY_N = 30
        private const val MAX_SAVED_FRAMES = 8
        const val SPIKE_FRAMES_DIR = "phase0/s1_frames"
    }
}
