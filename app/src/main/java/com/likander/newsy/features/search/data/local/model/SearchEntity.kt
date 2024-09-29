package com.likander.newsy.features.search.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_table")
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String,
    val content: String,
    val description: String,
    @ColumnInfo(name = "published_at")
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    @ColumnInfo(name = "url_to_image")
    val urlToImage: String,
    val favourite: Boolean = false,
    val category: String,
    val page: Int
)