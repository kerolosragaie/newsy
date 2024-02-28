package com.likander.newsy.features.headline.domain.model

import androidx.annotation.DrawableRes

data class Article(
    val id: Int,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val favourite: Boolean = false,
    val category: String? = null,
    val page: Int,
)

data class Country(
    val code: String,
    val name: String,
    @DrawableRes val icResId: Int,
)

data class Language(
    val code: String,
    val name: String,
)

data class Setting(
    val preferredCountryIndex: Int,
    val preferredLanguageIndex: Int,
)

