package com.likander.newsy.features.search.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.likander.newsy.features.search.data.local.model.SearchRemoteKeyEntity

@Dao
interface SearchRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<SearchRemoteKeyEntity>)

    @Query("SELECT * FROM search_key_table WHERE article_id = :id")
    suspend fun getRemoteKeyArticleById(id: String): SearchRemoteKeyEntity?

    @Query("SELECT created_at FROM search_key_table ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

    @Query("DELETE FROM search_key_table")
    suspend fun clearRemoteKeys()
}