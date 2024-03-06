package com.likander.newsy.core.common.data.model

import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("articles")
    val articles: List<ArticleDto> = emptyList(),
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("totalResults")
    val totalResults: Int? = null
)