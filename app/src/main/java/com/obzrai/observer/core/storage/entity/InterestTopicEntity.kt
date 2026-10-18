package com.obzrai.observer.core.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.obzrai.observer.core.model.MatchSensitivity

@Entity(tableName = "interest_topics")
data class InterestTopicEntity(
    @PrimaryKey val id: String,
    val name: String,
    /** Comma-separated aliases for case-insensitive matching. */
    val aliasesCsv: String = "",
    val enabled: Boolean = true,
    val negativeKeywordsCsv: String = "",
    val sensitivity: MatchSensitivity = MatchSensitivity.CONSERVATIVE,
)
