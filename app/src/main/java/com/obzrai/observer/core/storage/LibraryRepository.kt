package com.obzrai.observer.core.storage

import com.obzrai.observer.core.storage.dao.InterestTopicDao
import com.obzrai.observer.core.storage.dao.SavedItemDao
import com.obzrai.observer.core.storage.entity.InterestTopicEntity
import com.obzrai.observer.core.storage.entity.SavedItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LibraryRepository @Inject constructor(
    private val savedItemDao: SavedItemDao,
    private val interestTopicDao: InterestTopicDao,
) {
    fun observeItems(): Flow<List<SavedItemEntity>> = savedItemDao.observeAll()

    fun observeRecent(limit: Int = 5): Flow<List<SavedItemEntity>> =
        savedItemDao.observeRecent(limit)

    fun observeItemCount(): Flow<Int> = savedItemDao.observeCount()

    fun observeTodayCount(dayStartEpochMs: Long): Flow<Int> =
        savedItemDao.observeCountSince(dayStartEpochMs)

    fun observeTopics(): Flow<List<InterestTopicEntity>> = interestTopicDao.observeAll()

    fun observeEnabledTopicCount(): Flow<Int> = interestTopicDao.observeEnabledCount()

    suspend fun upsertItem(item: SavedItemEntity) = savedItemDao.upsert(item)

    suspend fun upsertTopic(topic: InterestTopicEntity) = interestTopicDao.upsert(topic)

    suspend fun deleteAllItems() = savedItemDao.deleteAll()
}
