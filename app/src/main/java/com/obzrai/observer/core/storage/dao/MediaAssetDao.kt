package com.obzrai.observer.core.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.obzrai.observer.core.storage.entity.MediaAssetEntity

@Dao
interface MediaAssetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(asset: MediaAssetEntity)

    @Query("SELECT * FROM media_assets WHERE savedItemId = :itemId")
    suspend fun forItem(itemId: String): List<MediaAssetEntity>

    @Query("SELECT * FROM media_assets")
    suspend fun getAllOnce(): List<MediaAssetEntity>

    @Query("DELETE FROM media_assets WHERE savedItemId = :itemId")
    suspend fun deleteForItem(itemId: String)
}
