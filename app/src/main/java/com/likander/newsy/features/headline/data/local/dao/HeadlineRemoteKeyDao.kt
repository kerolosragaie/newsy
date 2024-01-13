package com.likander.newsy.features.headline.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.likander.newsy.features.headline.data.local.models.HeadlineRemoteKeyEntity

@Dao
interface HeadlineRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<HeadlineRemoteKeyEntity>)

    @Query("SELECT * FROM  headline_key_table WHERE article_id= :id")
    suspend fun getRemoteKeyByArticleId(id: String): HeadlineRemoteKeyEntity?

    @Query("DELETE FROM headline_key_table")
    suspend fun clearRemoteKeys()

    @Query("SELECT created_at FROM headline_key_table ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}