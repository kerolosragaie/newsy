package com.likander.newsy.features.search.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_key_table")
data class SearchRemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("article_id")
    val articleId: String,
    val prevKey: Int?,
    val currentKey: Int,
    val nextKey: Int?,
    @ColumnInfo("created_at")
    val createdAtd: Long = System.currentTimeMillis(),
)