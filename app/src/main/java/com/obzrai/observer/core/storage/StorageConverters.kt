package com.obzrai.observer.core.storage

import androidx.room.TypeConverter
import com.obzrai.observer.core.model.AudioStatus
import com.obzrai.observer.core.model.MatchSensitivity
import com.obzrai.observer.core.model.SourceUrlOrigin
import com.obzrai.observer.core.model.TranscriptStatus

class StorageConverters {
    @TypeConverter
    fun fromAudioStatus(value: AudioStatus): String = value.name

    @TypeConverter
    fun toAudioStatus(value: String): AudioStatus = AudioStatus.valueOf(value)

    @TypeConverter
    fun fromTranscriptStatus(value: TranscriptStatus): String = value.name

    @TypeConverter
    fun toTranscriptStatus(value: String): TranscriptStatus = TranscriptStatus.valueOf(value)

    @TypeConverter
    fun fromSourceUrlOrigin(value: SourceUrlOrigin): String = value.name

    @TypeConverter
    fun toSourceUrlOrigin(value: String): SourceUrlOrigin = SourceUrlOrigin.valueOf(value)

    @TypeConverter
    fun fromMatchSensitivity(value: MatchSensitivity): String = value.name

    @TypeConverter
    fun toMatchSensitivity(value: String): MatchSensitivity = MatchSensitivity.valueOf(value)
}
