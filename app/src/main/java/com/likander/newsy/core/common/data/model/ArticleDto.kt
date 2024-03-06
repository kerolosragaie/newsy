package com.likander.newsy.core.common.data.model

import com.google.gson.annotations.SerializedName
import com.likander.newsy.features.headline.data.remote.model.SourceDto

data class ArticleDto(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("publishedAt")
    val publishedAt: String? = null,
    @SerializedName("source")
    val sourceDto: SourceDto? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("urlToImage")
    val urlToImage: String? = null
)