package com.likander.newsy.features.core.domain.models

data class Article(
    val id: Int? = null,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val favourite: String? = null,
    val category: String? = null,
    val page: Int? = null,
)
