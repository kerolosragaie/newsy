package com.likander.newsy.features.discover.domain.repo

import androidx.paging.PagingData
import com.likander.newsy.features.headline.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface DiscoverRepo {
    fun fetchDiscoverArticles(
        category: String,
        country: String,
        language: String,
    ): Flow<PagingData<Article>>

    suspend fun getDiscoverArticleCurrentCategory(): String
    suspend fun getAllAvailableCategories(): List<String>
    suspend fun updateCategory(category: String)
    suspend fun updateFavouriteArticle(article: Article): Int
}