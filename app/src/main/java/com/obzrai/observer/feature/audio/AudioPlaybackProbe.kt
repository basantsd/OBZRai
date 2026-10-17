package com.obzrai.observer.feature.audio

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioPlaybackCaptureConfiguration
import android.media.AudioRecord
import android.media.projection.MediaProjection
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Spike S4 — AudioPlaybackCapture honesty probe.
 *
 * Pass: positive energy on a controlled ALLOW_CAPTURE_BY_ALL app.
 * Third-party Instagram / YouTube / Netflix often opt out → log honestly;
 * **third-party fail ≠ V1 no-go** (ship visual + OCR + Sharesheet for those apps).
 */
@Singleton
class AudioPlaybackProbe @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    data class ProbeResult(
        val targetLabel: String,
        val status: Status,
        val peakAmplitude: Int,
        val rms: Double,
        val samplesRead: Int,
        val notes: String,
    )

    enum class Status {
        PASS_CONTROLLED,
        SILENT_OR_BLOCKED,
        NO_PERMISSION,
        NO_PROJECTION,
        UNSUPPORTED_API,
        ERROR,
    }

    suspend fun probe(
        mediaProjection: MediaProjection?,
        targetLabel: String,
        durationMs: Long = 1500L,
    ): ProbeResult =
        withContext(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                return@withContext ProbeResult(
                    targetLabel = targetLabel,
                    status = Status.UNSUPPORTED_API,
                    peakAmplitude = 0,
                    rms = 0.0,
                    samplesRead = 0,
                    notes = "AudioPlaybackCapture requires API 29+",
                )
            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return@withContext ProbeResult(
                    targetLabel = targetLabel,
                    status = Status.NO_PERMISSION,
                    peakAmplitude = 0,
                    rms = 0.0,
                    samplesRead = 0,
                    notes = "RECORD_AUDIO not granted",
                )
            }
            if (mediaProjection == null) {
                return@withContext ProbeResult(
                    targetLabel = targetLabel,
                    status = Status.NO_PROJECTION,
                    peakAmplitude = 0,
                    rms = 0.0,
                    samplesRead = 0,
                    notes = "MediaProjection required for AudioPlaybackCaptureConfiguration",
                )
            }

            try {
                val config =
                    AudioPlaybackCaptureConfiguration.Builder(mediaProjection)
                        .addMatchingUsage(AudioAttributes.USAGE_MEDIA)
                        .addMatchingUsage(AudioAttributes.USAGE_GAME)
                        .addMatchingUsage(AudioAttributes.USAGE_UNKNOWN)
                        .build()

                val sampleRate = 16_000
                val channelConfig = AudioFormat.CHANNEL_IN_MONO
                val encoding = AudioFormat.ENCODING_PCM_16BIT
                val minBuf =
                    AudioRecord.getMinBufferSize(sampleRate, channelConfig, encoding)
                val format =
                    AudioFormat.Builder()
                        .setEncoding(encoding)
                        .setSampleRate(sampleRate)
                        .setChannelMask(channelConfig)
                        .build()

                val record =
                    AudioRecord.Builder()
                        .setAudioFormat(format)
                        .setBufferSizeInBytes(minBuf * 2)
                        .setAudioPlaybackCaptureConfig(config)
                        .build()

                if (record.state != AudioRecord.STATE_INITIALIZED) {
                    record.release()
                    return@withContext ProbeResult(
                        targetLabel = targetLabel,
                        status = Status.ERROR,
                        peakAmplitude = 0,
                        rms = 0.0,
                        samplesRead = 0,
                        notes = "AudioRecord failed to initialize for playback capture",
                    )
                }

                val buffer = ShortArray(minBuf)
                var peak = 0
                var sumSquares = 0.0
                var totalSamples = 0
                record.startRecording()
                val deadline = System.currentTimeMillis() + durationMs
                while (System.currentTimeMillis() < deadline) {
                    val read = record.read(buffer, 0, buffer.size)
                    if (read > 0) {
                        totalSamples += read
                        for (i in 0 until read) {
                            val v = abs(buffer[i].toInt())
                            if (v > peak) peak = v
                            sumSquares += buffer[i].toDouble() * buffer[i].toDouble()
                        }
                    }
                }
                record.stop()
                record.release()

                val rms = if (totalSamples > 0) sqrt(sumSquares / totalSamples) else 0.0
                val blocked = peak < SILENCE_PEAK_THRESHOLD && rms < SILENCE_RMS_THRESHOLD
                val status =
                    if (blocked) Status.SILENT_OR_BLOCKED else Status.PASS_CONTROLLED
                val notes =
                    when {
                        blocked && isKnownOptOut(targetLabel) ->
                            "Honest fail: $targetLabel likely ALLOW_CAPTURE_BY_SYSTEM/NONE — " +
                                "expected; V1 continues with screenshot+OCR+Sharesheet"
                        blocked ->
                            "Silent capture — treat as blocked or no audible playback; do not " +
                                "claim success"
                        else ->
                            "Non-silent PCM observed — capture policy appears permissive for this target"
                    }
                Log.i(
                    TAG,
                    "S4 probe target=$targetLabel status=$status peak=$peak rms=$rms samples=$totalSamples",
                )
                ProbeResult(
                    targetLabel = targetLabel,
                    status = status,
                    peakAmplitude = peak,
                    rms = rms,
                    samplesRead = totalSamples,
                    notes = notes,
                )
            } catch (t: Throwable) {
                Log.e(TAG, "S4 probe error for $targetLabel", t)
                ProbeResult(
                    targetLabel = targetLabel,
                    status = Status.ERROR,
                    peakAmplitude = 0,
                    rms = 0.0,
                    samplesRead = 0,
                    notes = "Exception: ${t.javaClass.simpleName}: ${t.message}",
                )
            }
        }

    /**
     * Documented third-party expectations without needing those apps installed in CI.
     * Device runs should still call [probe] with a live projection when available.
     */
    fun expectedThirdPartyHonestyLog(): List<String> =
        listOf(
            "Instagram: EXPECT blocked (ALLOW_CAPTURE_BY_SYSTEM/NONE typical) — not a V1 no-go",
            "YouTube: EXPECT often blocked depending on player/policy — log honestly",
            "Netflix / DRM: EXPECT blocked + FLAG_SECURE black frames — label capture_blocked",
            "Controlled test app (USAGE_MEDIA + ALLOW_CAPTURE_BY_ALL): EXPECT PASS_CONTROLLED",
        )

    private fun isKnownOptOut(label: String): Boolean {
        val n = label.lowercase()
        return n.contains("instagram") ||
            n.contains("youtube") ||
            n.contains("netflix") ||
            n.contains("spotify")
    }

    companion object {
        private const val TAG = "AudioProbeS4"
        private const val SILENCE_PEAK_THRESHOLD = 200
        private const val SILENCE_RMS_THRESHOLD = 50.0
    }
}
