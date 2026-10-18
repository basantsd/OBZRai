package com.obzrai.observer.core.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.obzrai.observer.core.model.AudioStatus
import com.obzrai.observer.core.model.SourceUrlOrigin
import com.obzrai.observer.core.model.TranscriptStatus

@Entity(tableName = "saved_items")
data class SavedItemEntity(
    @PrimaryKey val id: String,
    val createdAtEpochMs: Long,
    val title: String,
    val sourceApp: String? = null,
    /** Exact URL only when genuinely available; never fabricate. */
    val sourceUrl: String? = null,
    val sourceUrlOrigin: SourceUrlOrigin = SourceUrlOrigin.NONE,
    /** Comma-separated topic ids. */
    val topicIdsCsv: String = "",
    /** Comma-separated user tags (FR-036). */
    val tagsCsv: String = "",
    val captureState: String = "saved",
    /** FR-052: true when frame is black/blocked — never show false Saved green for screenshot. */
    val captureBlocked: Boolean = false,
    val audioState: AudioStatus = AudioStatus.NOT_REQUESTED,
    val transcriptState: TranscriptStatus = TranscriptStatus.UNAVAILABLE,
    val durationMs: Long? = null,
    val userNotes: String = "",
    /** On-device OCR text (FR-008); empty if unreadable — never fabricated. */
    val ocrText: String = "",
    /** PTT spoken idea transcript text (FR-040). */
    val voiceIdeaText: String = "",
    val voiceIdeaAudioPath: String? = null,
    /** Device label for provenance (FR-038). */
    val deviceLabel: String = "Android",
    /** Continuity: local-only until Phase 2 thin sync (FR-106). */
    val continuityStatus: String = "local_only",
)
