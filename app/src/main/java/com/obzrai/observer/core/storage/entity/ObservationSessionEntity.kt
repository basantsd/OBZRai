package com.obzrai.observer.core.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "observation_sessions")
data class ObservationSessionEntity(
    @PrimaryKey val id: String,
    val startedAtEpochMs: Long,
    val endedAtEpochMs: Long? = null,
    val consentVersion: String,
    val captureRegion: String? = null,
    val stopReason: String? = null,
    val deviceApi: Int,
    val audioCapability: String? = null,
)
