package com.likander.newsy.features.headline.data.local.data_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
import com.likander.newsy.core.utils.Constants
import com.likander.newsy.features.headline.data.local.models.HeadlineArticleEntity
import com.likander.newsy.features.headline.data.paging.HeadlineMediator
import com.likander.newsy.features.headline.data.remote.api.HeadlineApi
import kotlinx.coroutines.flow.Flow

class HeadlineLocalDataSourceImpl(private val newsArticleDatabase: NewsArticleDatabase) :
    HeadlineLocalDataSource {
    @OptIn(ExperimentalPagingApi::class)
    override fun getAllHeadlineArticles(
        headlineApi: HeadlineApi,
        category: String,
        country: String,
        language: String,
    ): Flow<PagingData<HeadlineArticleEntity>> = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PAGE_SIZE - 1,
            initialLoadSize = 10,
        ),
        pagingSourceFactory = {
            newsArticleDatabase.headlineDao().getAllHeadlineArticles()
        },
        remoteMediator = HeadlineMediator(
            api = headlineApi,
            database = newsArticleDatabase,
            category = category,
            country = country,
            language = language,
        )
    ).flow

    override fun getHeadlineArticle(id: Int): Flow<HeadlineArticleEntity> =
        newsArticleDatabase.headlineDao().getHeadlineArticle(id)

    override suspend fun insertHeadlineArticles(articles: List<HeadlineArticleEntity>) =
        newsArticleDatabase.headlineDao().insertHeadlineArticles(articles)

    override suspend fun removeAllHeadlineArticles() =
        newsArticleDatabase.headlineDao().removeAllHeadlineArticles()

    override suspend fun removeFavouriteArticle(headlineArticleEntity: HeadlineArticleEntity) =
        newsArticleDatabase.headlineDao().removeFavouriteArticle(headlineArticleEntity)

    override suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int): Int =
        newsArticleDatabase.headlineDao().updateFavouriteArticle(isFavourite, id)

    override fun getNewsArticleDatabase(): NewsArticleDatabase = newsArticleDatabase
}