package com.obzrai.observer.feature.capture

import android.util.Log
import com.obzrai.observer.core.model.SourceUrlOrigin
import com.obzrai.observer.feature.library.ItemSaver
import com.obzrai.observer.feature.overlay.OverlayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * FR-010 manual Save/Skip from overlay (and Home). Uses clean-frame capture when projection active.
 */
@Singleton
class ManualCaptureController @Inject constructor(
    private val cleanFrameCapture: CleanFrameCapture,
    private val itemSaver: ItemSaver,
    private val overlayController: OverlayController,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _events = MutableSharedFlow<String>(extraBufferCapacity = 8)
    val events: SharedFlow<String> = _events.asSharedFlow()

    fun saveManual(sourceApp: String? = null) {
        scope.launch(Dispatchers.IO) {
            val clean = cleanFrameCapture.captureCleanFrame()
            val outcome =
                itemSaver.save(
                    ItemSaver.SaveRequest(
                        bitmap = clean.bitmap,
                        sourceApp = sourceApp,
                        sourceUrlOrigin = SourceUrlOrigin.NONE,
                        manualOverride = true,
                    ),
                )
            clean.bitmap?.recycle()
            val msg =
                when {
                    outcome.skipped -> "Skip path: ${outcome.reason}"
                    outcome.item?.captureBlocked == true ->
                        "capture_blocked labelled — no false Saved green for screenshot"
                    outcome.item != null -> "Saved (manual)"
                    else -> "Save failed"
                }
            Log.i(TAG, msg)
            _events.emit(msg)
        }
    }

    fun skip() {
        scope.launch {
            // Reject current candidate without blacklisting (FR-010).
            _events.emit("Skipped current candidate")
            Log.i(TAG, "Manual skip")
        }
    }

    fun openSettingsViaOverlayHide() {
        // Settings UI lives in-app; overlay simply stays visible / user returns to app.
        overlayController.hide()
        scope.launch { _events.emit("Open Settings in OBZRAi") }
    }

    companion object {
        private const val TAG = "ManualCapture"
    }
}
