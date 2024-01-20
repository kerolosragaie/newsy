package com.likander.newsy.features.headline.data.local.data_source

import androidx.paging.PagingData
import com.likander.newsy.features.headline.data.local.database.NewsArticleDatabase
import com.likander.newsy.features.headline.data.local.models.HeadlineEntity
import com.likander.newsy.features.headline.data.mappers.Mapper
import com.likander.newsy.features.headline.data.remote.api.HeadlineApi
import com.likander.newsy.features.headline.data.remote.model.ArticleDto
import com.likander.newsy.features.headline.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface HeadlineLocalDataSource {

    fun getAllHeadlineArticles(
        articleHeadlineMapper: Mapper<ArticleDto, HeadlineEntity>,
        headlineApi:HeadlineApi,
        category: String,
        country: String,
        language: String,
    ): Flow<PagingData<HeadlineEntity>>

    fun getHeadlineArticle(id: Int): Flow<HeadlineEntity>

    suspend fun insertHeadlineArticles(articles: List<HeadlineEntity>)

    suspend fun removeAllHeadlineArticles()

    suspend fun removeFavouriteArticle(headlineEntity: HeadlineEntity)

    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int)

    fun getNewsArticleDatabase(): NewsArticleDatabase
}