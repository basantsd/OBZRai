package com.obzrai.observer.core.storage

import com.obzrai.observer.core.storage.dao.InterestTopicDao
import com.obzrai.observer.core.storage.dao.MediaAssetDao
import com.obzrai.observer.core.storage.dao.SavedItemDao
import com.obzrai.observer.core.storage.entity.InterestTopicEntity
import com.obzrai.observer.core.storage.entity.MediaAssetEntity
import com.obzrai.observer.core.storage.entity.SavedItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LibraryRepository @Inject constructor(
    private val savedItemDao: SavedItemDao,
    private val interestTopicDao: InterestTopicDao,
    private val mediaAssetDao: MediaAssetDao,
) {
    fun observeItems(): Flow<List<SavedItemEntity>> = savedItemDao.observeAll()

    fun observeRecent(limit: Int = 5): Flow<List<SavedItemEntity>> =
        savedItemDao.observeRecent(limit)

    fun observeItemCount(): Flow<Int> = savedItemDao.observeCount()

    fun observeTodayCount(dayStartEpochMs: Long): Flow<Int> =
        savedItemDao.observeCountSince(dayStartEpochMs)

    fun observeForDay(dayStart: Long, dayEnd: Long): Flow<List<SavedItemEntity>> =
        savedItemDao.observeForDay(dayStart, dayEnd)

    fun search(query: String): Flow<List<SavedItemEntity>> =
        if (query.isBlank()) observeItems() else savedItemDao.search(query.trim())

    fun observeTopics(): Flow<List<InterestTopicEntity>> = interestTopicDao.observeAll()

    fun observeEnabledTopicCount(): Flow<Int> = interestTopicDao.observeEnabledCount()

    suspend fun getItem(id: String): SavedItemEntity? = savedItemDao.getById(id)

    suspend fun getAllItems(): List<SavedItemEntity> = savedItemDao.getAllOnce()

    suspend fun getAllMedia(): List<MediaAssetEntity> = mediaAssetDao.getAllOnce()

    suspend fun mediaForItem(itemId: String): List<MediaAssetEntity> = mediaAssetDao.forItem(itemId)

    suspend fun upsertItem(item: SavedItemEntity) = savedItemDao.upsert(item)

    suspend fun upsertMedia(asset: MediaAssetEntity) = mediaAssetDao.upsert(asset)

    suspend fun upsertTopic(topic: InterestTopicEntity) = interestTopicDao.upsert(topic)

    suspend fun deleteItem(id: String) {
        mediaAssetDao.deleteForItem(id)
        savedItemDao.deleteById(id)
    }

    suspend fun deleteAllItems() = savedItemDao.deleteAll()
}
