package com.obzrai.observer.core.storage.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "media_assets",
    foreignKeys = [
        ForeignKey(
            entity = SavedItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["savedItemId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("savedItemId")],
)
data class MediaAssetEntity(
    @PrimaryKey val id: String,
    val savedItemId: String,
    /** screenshot_original | screenshot_preview | playback_audio */
    val type: String,
    val localUri: String,
    val width: Int? = null,
    val height: Int? = null,
    val orientation: Int? = null,
    val sha256: String? = null,
    val bytes: Long? = null,
    val capturedAtEpochMs: Long,
)
