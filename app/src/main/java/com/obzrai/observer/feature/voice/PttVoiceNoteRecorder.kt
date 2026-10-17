package com.obzrai.observer.feature.voice

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * FR-040 — push-to-talk spoken idea notes.
 * Records only while held after separate mic opt-in. Transcript may be UNAVAILABLE
 * until on-device ASR (S6) lands; audio file is still linked.
 */
@Singleton
class PttVoiceNoteRecorder @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private var recorder: MediaRecorder? = null
    private var outputFile: File? = null

    val isRecording: Boolean
        get() = recorder != null

    fun hasMicPermission(): Boolean =
        ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) ==
            PackageManager.PERMISSION_GRANTED

    fun start(): File? {
        if (!hasMicPermission()) {
            Log.w(TAG, "PTT start blocked — RECORD_AUDIO missing")
            return null
        }
        stopInternal(discard = true)
        val dir = File(context.filesDir, "library/voice").also { it.mkdirs() }
        val file = File(dir, "ptt_${UUID.randomUUID()}.m4a")
        val mr =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }
        return try {
            mr.setAudioSource(MediaRecorder.AudioSource.MIC)
            mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mr.setOutputFile(file.absolutePath)
            mr.prepare()
            mr.start()
            recorder = mr
            outputFile = file
            Log.i(TAG, "PTT recording started → ${file.absolutePath}")
            file
        } catch (t: Throwable) {
            Log.e(TAG, "PTT start failed", t)
            runCatching { mr.release() }
            null
        }
    }

    fun stop(): File? {
        val file = outputFile
        stopInternal(discard = false)
        return file
    }

    fun cancel() {
        stopInternal(discard = true)
    }

    private fun stopInternal(discard: Boolean) {
        try {
            recorder?.apply {
                runCatching { stop() }
                release()
            }
        } catch (t: Throwable) {
            Log.w(TAG, "PTT stop error", t)
        }
        recorder = null
        if (discard) {
            outputFile?.delete()
            outputFile = null
        }
    }

    companion object {
        private const val TAG = "PttVoice"
    }
}
