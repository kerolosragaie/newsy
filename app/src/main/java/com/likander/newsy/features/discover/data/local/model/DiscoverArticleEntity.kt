package com.likander.newsy.features.discover.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "discover_articles_table")
data class DiscoverArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    @ColumnInfo("published_at")
    val publishedAt: String? = null,
    val source: String? = null,
    val title: String? = null,
    val url: String? = null,
    @ColumnInfo("url_to_image")
    val urlToImage: String? = null,
    val favourite: Boolean = false,
    val category: String? = null,
    val page: Int
)