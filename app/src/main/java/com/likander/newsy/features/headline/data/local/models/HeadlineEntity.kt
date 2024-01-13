package com.likander.newsy.features.headline.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.likander.newsy.features.core.data.local.models.LocalContractDto

@Entity("headline_table")
data class HeadlineEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val author: String?,
    override val content: String?,
    override val description: String?,
    @ColumnInfo("published_at")
    override val publishedAt: String?,
    override val source: String?,
    override val title: String?,
    override val url: String?,
    @ColumnInfo("url_to_image")
    override val urlToImage: String?,
    override val favourite: Boolean?,
    override val category: String?,
    override val page: Int
) : LocalContractDto()
