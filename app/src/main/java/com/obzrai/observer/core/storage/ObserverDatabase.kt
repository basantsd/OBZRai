package com.obzrai.observer.core.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.obzrai.observer.core.storage.dao.InterestTopicDao
import com.obzrai.observer.core.storage.dao.MediaAssetDao
import com.obzrai.observer.core.storage.dao.SavedItemDao
import com.obzrai.observer.core.storage.entity.InterestTopicEntity
import com.obzrai.observer.core.storage.entity.MediaAssetEntity
import com.obzrai.observer.core.storage.entity.ObservationSessionEntity
import com.obzrai.observer.core.storage.entity.SavedItemEntity

@Database(
    entities = [
        SavedItemEntity::class,
        InterestTopicEntity::class,
        MediaAssetEntity::class,
        ObservationSessionEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
@TypeConverters(StorageConverters::class)
abstract class ObserverDatabase : RoomDatabase() {
    abstract fun savedItemDao(): SavedItemDao

    abstract fun interestTopicDao(): InterestTopicDao

    abstract fun mediaAssetDao(): MediaAssetDao
}
