package com.likander.newsy.features.headline.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.likander.newsy.features.core.data.local.models.LocalContractDto

@Entity("headline_table")
data class HeadlineEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val author: String? = null,
    override val content: String? = null,
    override val description: String? = null,
    @ColumnInfo("published_at")
    override val publishedAt: String? = null,
    override val source: String? = null,
    override val title: String? = null,
    override val url: String? = null,
    @ColumnInfo("url_to_image")
    override val urlToImage: String? = null,
    override val favourite: Boolean? = null,
    override val category: String? = null,
    override val page: Int
) : LocalContractDto()
