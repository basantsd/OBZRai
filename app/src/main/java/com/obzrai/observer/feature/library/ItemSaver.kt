package com.obzrai.observer.feature.library

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.obzrai.observer.core.model.AudioStatus
import com.obzrai.observer.core.model.MatchSensitivity
import com.obzrai.observer.core.model.SourceUrlOrigin
import com.obzrai.observer.core.model.TranscriptStatus
import com.obzrai.observer.core.prefs.ObserverPreferences
import com.obzrai.observer.core.storage.LibraryRepository
import com.obzrai.observer.core.storage.entity.InterestTopicEntity
import com.obzrai.observer.core.storage.entity.MediaAssetEntity
import com.obzrai.observer.core.storage.entity.SavedItemEntity
import com.obzrai.observer.feature.capture.BlackFrameDetector
import com.obzrai.observer.feature.match.TopicMatcher
import com.obzrai.observer.feature.ocr.OcrEngine
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemSaver @Inject constructor(
    @ApplicationContext private val context: Context,
    private val libraryRepository: LibraryRepository,
    private val ocrEngine: OcrEngine,
    private val topicMatcher: TopicMatcher,
    private val blackFrameDetector: BlackFrameDetector,
    private val preferences: ObserverPreferences,
) {
    data class SaveRequest(
        val bitmap: Bitmap?,
        val titleHint: String = "",
        val sourceApp: String? = null,
        val sourceUrl: String? = null,
        val sourceUrlOrigin: SourceUrlOrigin = SourceUrlOrigin.NONE,
        val manualOverride: Boolean = false,
        val userNotes: String = "",
        val tags: List<String> = emptyList(),
        val voiceIdeaText: String = "",
        val voiceIdeaAudioPath: String? = null,
    )

    data class SaveOutcome(
        val item: SavedItemEntity?,
        val skipped: Boolean,
        val reason: String,
    )

    suspend fun save(request: SaveRequest): SaveOutcome {
        val topics = libraryRepository.observeTopics().first()
        ensureDefaultTopics(topics)

        val blocked =
            request.bitmap?.let { blackFrameDetector.analyze(it).blocked } ?: false
        val ocr =
            if (request.bitmap != null && !blocked) {
                ocrEngine.recognize(request.bitmap)
            } else {
                ""
            }
        val title =
            request.titleHint.ifBlank {
                ocr.lineSequence().firstOrNull()?.take(80)?.trim().orEmpty()
                    .ifBlank { if (blocked) "Protected screen" else "Saved item" }
            }

        val sensitivity =
            runCatching {
                MatchSensitivity.valueOf(preferences.matchSensitivity.first())
            }.getOrDefault(MatchSensitivity.BALANCED)
        val match =
            topicMatcher.match(
                ocrText = ocr,
                title = title,
                topics = libraryRepository.observeTopics().first(),
                sensitivity = sensitivity,
            )

        if (!request.manualOverride && !match.matched && request.sourceUrlOrigin != SourceUrlOrigin.SHARE_SHEET) {
            return SaveOutcome(null, skipped = true, reason = "no_topic_match")
        }

        val id = UUID.randomUUID().toString()
        val now = System.currentTimeMillis()
        var mediaPath: String? = null
        var width: Int? = null
        var height: Int? = null
        var sha: String? = null
        var bytes: Long? = null

        if (request.bitmap != null && !blocked) {
            val dir = File(context.filesDir, "library/frames").also { it.mkdirs() }
            val file = File(dir, "$id.png")
            FileOutputStream(file).use { out ->
                request.bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            mediaPath = file.absolutePath
            width = request.bitmap.width
            height = request.bitmap.height
            bytes = file.length()
            sha = sha256(file)
        }

        val captureState =
            when {
                blocked -> "capture_blocked"
                else -> "saved"
            }

        val item =
            SavedItemEntity(
                id = id,
                createdAtEpochMs = now,
                title = title,
                sourceApp = request.sourceApp,
                sourceUrl = request.sourceUrl?.takeIf { it.isNotBlank() },
                sourceUrlOrigin = request.sourceUrlOrigin,
                topicIdsCsv = match.matchedTopicIds.joinToString(","),
                tagsCsv = request.tags.joinToString(","),
                captureState = captureState,
                captureBlocked = blocked,
                audioState = AudioStatus.NOT_REQUESTED,
                transcriptState =
                    if (request.voiceIdeaText.isNotBlank()) {
                        TranscriptStatus.COMPLETE
                    } else {
                        TranscriptStatus.UNAVAILABLE
                    },
                userNotes = request.userNotes,
                ocrText = ocr,
                voiceIdeaText = request.voiceIdeaText,
                voiceIdeaAudioPath = request.voiceIdeaAudioPath,
                deviceLabel = android.os.Build.MODEL ?: "Android",
                continuityStatus = "local_only",
            )
        libraryRepository.upsertItem(item)
        if (mediaPath != null) {
            libraryRepository.upsertMedia(
                MediaAssetEntity(
                    id = UUID.randomUUID().toString(),
                    savedItemId = id,
                    type = "screenshot_original",
                    localUri = mediaPath,
                    width = width,
                    height = height,
                    orientation = 0,
                    sha256 = sha,
                    bytes = bytes,
                    capturedAtEpochMs = now,
                ),
            )
        }
        Log.i(TAG, "Saved item $id blocked=$blocked topics=${item.topicIdsCsv}")
        return SaveOutcome(item, skipped = false, reason = captureState)
    }

    private suspend fun ensureDefaultTopics(existing: List<InterestTopicEntity>) {
        if (existing.isNotEmpty()) return
        val defaults =
            listOf(
                InterestTopicEntity(id = "gst", name = "GST Tally tax", enabled = true),
                InterestTopicEntity(id = "android", name = "Android Kotlin Compose", enabled = true),
                InterestTopicEntity(id = "ai", name = "AI ML OCR", enabled = true),
            )
        defaults.forEach { libraryRepository.upsertTopic(it) }
    }

    private fun sha256(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        file.inputStream().use { input ->
            val buf = ByteArray(8192)
            while (true) {
                val n = input.read(buf)
                if (n <= 0) break
                digest.update(buf, 0, n)
            }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }

    companion object {
        private const val TAG = "ItemSaver"
    }
}
