package com.likander.newsy.features.search.domain.model

data class SearchArticle(
    val id: Int = 0,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val favourite: Boolean = false,
    val category: String,
    val page: Int
)