package com.likander.newsy.features.headline.data.local.data_source

import androidx.paging.PagingData
import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
import com.likander.newsy.features.headline.data.local.models.HeadlineArticleEntity
import com.likander.newsy.features.headline.data.remote.api.HeadlineApi
import kotlinx.coroutines.flow.Flow

interface HeadlineLocalDataSource {

    fun getAllHeadlineArticles(
        headlineApi: HeadlineApi,
        category: String,
        country: String,
        language: String,
    ): Flow<PagingData<HeadlineArticleEntity>>

    fun getHeadlineArticle(id: Int): Flow<HeadlineArticleEntity>

    suspend fun insertHeadlineArticles(articles: List<HeadlineArticleEntity>)

    suspend fun removeAllHeadlineArticles()

    suspend fun removeFavouriteArticle(headlineArticleEntity: HeadlineArticleEntity)

    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int): Int

    fun getNewsArticleDatabase(): NewsArticleDatabase
}