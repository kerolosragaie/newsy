package com.likander.newsy.features.search.data.remote.model

import com.likander.newsy.core.common.data.model.ArticleDto

data class SearchArticleRemoteDto(
    val articles: List<ArticleDto>? = null,
    val status: String? = null,
    val totalResults: Int? = null
)