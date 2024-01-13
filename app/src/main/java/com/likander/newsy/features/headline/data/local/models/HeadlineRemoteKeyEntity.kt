package com.likander.newsy.features.headline.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("headline_key_table")
data class HeadlineRemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("article_id")
    val articleId: String,
    val prevKey: Int?,
    val currentPage: Int?,
    val nextKey: Int?,
    @ColumnInfo("created_at")
    val createdAt: Long = System.currentTimeMillis(),
)
