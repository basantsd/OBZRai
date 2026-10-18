package com.obzrai.observer.feature.home

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obzrai.observer.core.model.SessionState
import com.obzrai.observer.core.storage.LibraryRepository
import com.obzrai.observer.core.storage.entity.SavedItemEntity
import com.obzrai.observer.feature.audio.AudioPlaybackProbe
import com.obzrai.observer.feature.capture.CleanFrameCapture
import com.obzrai.observer.feature.capture.DefaultMediaProjectionGateway
import com.obzrai.observer.feature.capture.MediaProjectionGateway
import com.obzrai.observer.feature.overlay.OverlayController
import com.obzrai.observer.feature.overlay.OverlayGeometry
import com.obzrai.observer.feature.session.ObservationSessionController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar
import javax.inject.Inject

data class HomeUiState(
    val sessionState: SessionState = SessionState.STOPPED,
    val savedCount: Int = 0,
    val todayCount: Int = 0,
    val topicCount: Int = 0,
    val recentItems: List<SavedItemEntity> = emptyList(),
    val frameCount: Int = 0,
    val lastFrameInfo: MediaProjectionGateway.FrameInfo? = null,
    val lastStopReason: String? = null,
    val overlayShowing: Boolean = false,
    val densityReport: String = "",
    val s3Result: String? = null,
    val s4Results: List<String> = emptyList(),
    val spikeLog: String = "",
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionController: ObservationSessionController,
    private val mediaProjectionGateway: MediaProjectionGateway,
    private val overlayController: OverlayController,
    private val cleanFrameCapture: CleanFrameCapture,
    private val audioPlaybackProbe: AudioPlaybackProbe,
    libraryRepository: LibraryRepository,
) : ViewModel() {
    private val dayStart: Long =
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

    private val _s3Result = MutableStateFlow<String?>(null)
    private val _s4Results = MutableStateFlow<List<String>>(emptyList())
    private val _spikeLog = MutableStateFlow("")
    private val _densityReport = MutableStateFlow(buildDensityReport())

    val needsProjectionConsent: StateFlow<Boolean> = sessionController.needsProjectionConsent
    val needsOverlayPermission: StateFlow<Boolean> = sessionController.needsOverlayPermission

    val uiState: StateFlow<HomeUiState> =
        combine(
            combine(
                sessionController.state,
                libraryRepository.observeItemCount(),
                libraryRepository.observeTodayCount(dayStart),
                libraryRepository.observeEnabledTopicCount(),
                libraryRepository.observeRecent(5),
            ) { session, saved, today, topics, recent ->
                Quintuple(session, saved, today, topics, recent)
            },
            combine(
                mediaProjectionGateway.frameCount,
                mediaProjectionGateway.lastFrameInfo,
                mediaProjectionGateway.lastStopReason,
                overlayController.isShowing,
                _densityReport,
            ) { frames, info, stop, overlay, density ->
                Quintuple(frames, info, stop, overlay, density)
            },
            combine(_s3Result, _s4Results, _spikeLog) { s3, s4, log ->
                Triple(s3, s4, log)
            },
        ) { base, capture, spikes ->
            HomeUiState(
                sessionState = base.first,
                savedCount = base.second,
                todayCount = base.third,
                topicCount = base.fourth,
                recentItems = base.fifth,
                frameCount = capture.first,
                lastFrameInfo = capture.second,
                lastStopReason = capture.third,
                overlayShowing = capture.fourth,
                densityReport = capture.fifth,
                s3Result = spikes.first,
                s4Results = spikes.second,
                spikeLog = spikes.third,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(densityReport = buildDensityReport()),
        )

    fun onToggleObserve() {
        when (sessionController.state.value) {
            SessionState.ACTIVE -> sessionController.stop("user_home")
            SessionState.PAUSED -> sessionController.requestStart()
            SessionState.STOPPED,
            SessionState.NEEDS_PERMISSION,
            -> sessionController.requestStart()
            SessionState.STARTING,
            SessionState.STOPPING,
            -> Unit
        }
    }

    fun clearOverlayPermissionNeed() {
        // no-op marker; MainActivity re-triggers start after grant
    }

    fun runS3CleanFrame() {
        viewModelScope.launch {
            val result = cleanFrameCapture.captureCleanFrame()
            val path =
                result.bitmap?.let { bmp ->
                    val dir = File(context.filesDir, "phase0/s3_frames").also { it.mkdirs() }
                    val file = File(dir, "clean_${System.currentTimeMillis()}.png")
                    FileOutputStream(file).use { out ->
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
                    }
                    bmp.recycle()
                    file.absolutePath
                }
            _s3Result.value =
                "strategy=${result.strategy} settleMs=${result.settleMs} " +
                    "overlayWasVisible=${result.overlayWasVisible} path=$path notes=${result.notes}"
            appendLog("S3: ${_s3Result.value}")
        }
    }

    fun runS4AudioProbes() {
        viewModelScope.launch {
            val projection =
                (mediaProjectionGateway as? DefaultMediaProjectionGateway)
                    ?.currentMediaProjection()
            val lines = mutableListOf<String>()
            lines.addAll(audioPlaybackProbe.expectedThirdPartyHonestyLog())
            val controlled =
                audioPlaybackProbe.probe(
                    mediaProjection = projection,
                    targetLabel = "controlled_or_current_playback",
                )
            lines.add(
                "LIVE probe: ${controlled.targetLabel} → ${controlled.status} " +
                    "peak=${controlled.peakAmplitude} rms=${"%.1f".format(controlled.rms)} " +
                    "| ${controlled.notes}",
            )
            // Document expected Instagram/YouTube outcomes without requiring those apps.
            lines.add(
                "Instagram (documented): SILENT_OR_BLOCKED expected — not a V1 no-go",
            )
            lines.add(
                "YouTube (documented): often SILENT_OR_BLOCKED — log honestly; visual path remains",
            )
            _s4Results.value = lines
            appendLog("S4: ${lines.joinToString(" | ")}")
        }
    }

    private fun appendLog(line: String) {
        _spikeLog.value = (_spikeLog.value + "\n" + line).trim()
    }

    private fun buildDensityReport(): String {
        val metrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        @Suppress("DEPRECATION")
        wm.defaultDisplay.getRealMetrics(metrics)
        val r = OverlayGeometry.densityReport(metrics)
        return "${r.densityBucket} dens=${r.density} ringPx=${r.ringPx} " +
            "glyphPx=${r.glyphPx} winPx=${r.windowPx} clipPx=${r.clipPx}"
    }
}

private data class Quintuple<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
)
