package com.likander.newsy.features.headline.domain.repo

import androidx.paging.PagingData
import com.likander.newsy.features.core.domain.models.Article
import kotlinx.coroutines.flow.Flow

interface HeadlineRepo {
    fun fetchHeadlineArticle(
        category: String,
        country: String,
        language: String,
    ): Flow<PagingData<Article>>

    suspend fun updateFavouriteArticle(article: Article)
}