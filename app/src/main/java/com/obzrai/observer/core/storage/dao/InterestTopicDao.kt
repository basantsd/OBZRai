package com.obzrai.observer.core.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.obzrai.observer.core.storage.entity.InterestTopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InterestTopicDao {
    @Query("SELECT * FROM interest_topics ORDER BY name ASC")
    fun observeAll(): Flow<List<InterestTopicEntity>>

    @Query("SELECT * FROM interest_topics WHERE enabled = 1 ORDER BY name ASC")
    fun observeEnabled(): Flow<List<InterestTopicEntity>>

    @Query("SELECT COUNT(*) FROM interest_topics WHERE enabled = 1")
    fun observeEnabledCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(topic: InterestTopicEntity)

    @Query("DELETE FROM interest_topics WHERE id = :id")
    suspend fun deleteById(id: String)
}
