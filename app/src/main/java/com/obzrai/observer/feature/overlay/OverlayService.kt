package com.obzrai.observer.feature.overlay

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.obzrai.observer.core.ui.ObserverRing
import com.obzrai.observer.core.ui.theme.AIObserverTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs

/**
 * Floating edge-clipped ring overlay (spike S2).
 *
 * - TYPE_APPLICATION_OVERLAY
 * - FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCH_MODAL → host remains tappable
 * - Drag + edge snap with partial off-screen clip
 * - Compose placeholder until `assets/approved_overlay_reference.png` arrives
 */
@AndroidEntryPoint
class OverlayService : Service(), LifecycleOwner, SavedStateRegistryOwner {
    @Inject
    lateinit var overlayController: OverlayController

    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    private var windowManager: WindowManager? = null
    private var overlayView: ComposeView? = null
    private var layoutParams: WindowManager.LayoutParams? = null

    private var dragStartX = 0
    private var dragStartY = 0
    private var touchStartRawX = 0f
    private var touchStartRawY = 0f
    private var moved = false

    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_SHOW -> attachOverlay()
            ACTION_HIDE -> detachOverlay()
            else -> Unit
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        detachOverlay()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        super.onDestroy()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun attachOverlay() {
        if (overlayView != null) return
        val wm = windowManager ?: return
        val metrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        wm.defaultDisplay.getRealMetrics(metrics)
        val report = OverlayGeometry.densityReport(metrics)
        Log.i(TAG, "Attaching overlay dens=${report.densityBucket} ringPx=${report.ringPx}")

        val size = OverlayGeometry.windowSizePx(metrics)
        val clip = OverlayGeometry.edgeClipOffsetPx(metrics)
        val params =
            WindowManager.LayoutParams(
                size,
                size,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT,
            ).apply {
                gravity = Gravity.TOP or Gravity.START
                x = -clip
                y = (metrics.heightPixels * 0.35f).toInt()
            }

        val view =
            ComposeView(this).apply {
                setViewTreeLifecycleOwner(this@OverlayService)
                setViewTreeSavedStateRegistryOwner(this@OverlayService)
                setContent {
                    AIObserverTheme {
                        OverlayRingContent()
                    }
                }
                setOnTouchListener { _, event ->
                    handleTouch(event, metrics)
                }
            }

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        wm.addView(view, params)
        overlayView = view
        layoutParams = params
    }

    private fun handleTouch(
        event: MotionEvent,
        metrics: DisplayMetrics,
    ): Boolean {
        val params = layoutParams ?: return false
        val wm = windowManager ?: return false
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                dragStartX = params.x
                dragStartY = params.y
                touchStartRawX = event.rawX
                touchStartRawY = event.rawY
                moved = false
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (event.rawX - touchStartRawX).toInt()
                val dy = (event.rawY - touchStartRawY).toInt()
                if (abs(dx) > 4 || abs(dy) > 4) moved = true
                params.x = dragStartX + dx
                params.y = dragStartY + dy
                wm.updateViewLayout(overlayView, params)
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (moved) {
                    val clip = OverlayGeometry.edgeClipOffsetPx(metrics)
                    params.x =
                        OverlayGeometry.snapX(
                            rawX = params.x,
                            windowWidth = params.width,
                            screenWidth = metrics.widthPixels,
                            clipPx = clip,
                        )
                    params.y = params.y.coerceIn(0, metrics.heightPixels - params.height)
                    wm.updateViewLayout(overlayView, params)
                    Log.i(TAG, "Snapped overlay to x=${params.x} y=${params.y}")
                }
                return true
            }
        }
        return false
    }

    private fun detachOverlay() {
        val view = overlayView ?: return
        try {
            windowManager?.removeView(view)
        } catch (t: Throwable) {
            Log.w(TAG, "removeView failed", t)
        }
        overlayView = null
        layoutParams = null
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        (overlayController as? DefaultOverlayController)?.markHiddenFromService()
    }

    companion object {
        private const val TAG = "OverlayService"
        const val ACTION_SHOW = "com.obzrai.observer.overlay.SHOW"
        const val ACTION_HIDE = "com.obzrai.observer.overlay.HIDE"
    }
}

@Composable
private fun OverlayRingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        ObserverRing(
            size = OverlayGeometry.RING_DP.dp,
            strokeWidth = OverlayGeometry.STROKE_DP.dp,
            showGlyph = true,
            glyphSize = OverlayGeometry.GLYPH_DP.dp,
        )
    }
}
