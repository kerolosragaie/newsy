package com.likander.newsy.features.search.data.local.data_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
import com.likander.newsy.core.utils.Constants
import com.likander.newsy.features.search.data.local.model.SearchEntity
import com.likander.newsy.features.search.data.paging.SearchMediator
import com.likander.newsy.features.search.data.remote.SearchApi
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class SearchLocalDataSourceImpl(
    private val newsArticleDatabase: NewsArticleDatabase
) : SearchLocalDataSource {
    override fun getAllSearchArticles(
        searchApi: SearchApi,
        query: String
    ): Flow<PagingData<SearchEntity>> =
        Pager(
            PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                prefetchDistance = Constants.PAGE_SIZE - 1,
                initialLoadSize = 10,
            ),
            pagingSourceFactory = {
                newsArticleDatabase.searchArticleDao().getAllSearchArticles()
            },
            remoteMediator = SearchMediator(
                api = searchApi,
                database = newsArticleDatabase,
                query = query
            )
        ).flow

    override suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int) =
        newsArticleDatabase.searchArticleDao().updateFavouriteArticle(isFavourite, id)
}