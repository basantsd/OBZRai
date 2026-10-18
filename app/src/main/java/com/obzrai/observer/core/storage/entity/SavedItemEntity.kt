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
    val captureState: String = "saved",
    val audioState: AudioStatus = AudioStatus.NOT_REQUESTED,
    val transcriptState: TranscriptStatus = TranscriptStatus.UNAVAILABLE,
    val durationMs: Long? = null,
    val userNotes: String = "",
)
