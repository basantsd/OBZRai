package com.obzrai.observer.core.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.obzrai.observer.core.storage.entity.SavedItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedItemDao {
    @Query("SELECT * FROM saved_items ORDER BY createdAtEpochMs DESC")
    fun observeAll(): Flow<List<SavedItemEntity>>

    @Query("SELECT * FROM saved_items ORDER BY createdAtEpochMs DESC LIMIT :limit")
    fun observeRecent(limit: Int): Flow<List<SavedItemEntity>>

    @Query("SELECT COUNT(*) FROM saved_items")
    fun observeCount(): Flow<Int>

    @Query(
        """
        SELECT COUNT(*) FROM saved_items
        WHERE createdAtEpochMs >= :dayStartEpochMs
        """,
    )
    fun observeCountSince(dayStartEpochMs: Long): Flow<Int>

    @Query("SELECT * FROM saved_items WHERE id = :id")
    suspend fun getById(id: String): SavedItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: SavedItemEntity)

    @Query("DELETE FROM saved_items WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM saved_items")
    suspend fun deleteAll()
}
