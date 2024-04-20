package com.likander.newsy.features.discover.data.local.data_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
import com.likander.newsy.core.utils.Constants
import com.likander.newsy.features.discover.data.local.model.DiscoverArticleEntity
import com.likander.newsy.features.discover.data.paging.DiscoverMediator
import com.likander.newsy.features.discover.data.remote.api.DiscoverApi
import kotlinx.coroutines.flow.Flow

class DiscoverLocalDataSourceImpl(private val newsArticleDatabase: NewsArticleDatabase) :
    DiscoverLocalDataSource {
    @OptIn(ExperimentalPagingApi::class)
    override fun getDiscoverArticles(
        discoverApi: DiscoverApi,
        category: String,
        country: String,
        language: String
    ): Flow<PagingData<DiscoverArticleEntity>> = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PAGE_SIZE - 1,
            initialLoadSize = 10,
        ),
        pagingSourceFactory = {
            newsArticleDatabase.discoverDao().getDiscoverArticles(category)
        },
        remoteMediator = DiscoverMediator(
            discoverApi = discoverApi,
            database = newsArticleDatabase,
            category = category,
            country = country,
            language = language,
        )
    ).flow

    override fun getDiscoverArticle(id: Int): Flow<DiscoverArticleEntity> =
        newsArticleDatabase.discoverDao().getDiscoverArticle(id)

    override suspend fun removeAllDiscoverArticles(category: String) =
        newsArticleDatabase.discoverDao().removeAllDiscoverArticles(category)

    override suspend fun removeFavouriteArticle(discoverArticleEntity: DiscoverArticleEntity) =
        newsArticleDatabase.discoverDao().removeFavouriteArticle(discoverArticleEntity)

    override suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int): Int =
        newsArticleDatabase.discoverDao().updateFavouriteArticle(isFavourite, id)

    override fun getNewsArticleDatabase(): NewsArticleDatabase = newsArticleDatabase
}