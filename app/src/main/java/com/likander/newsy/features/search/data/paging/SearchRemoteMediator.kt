package com.likander.newsy.features.search.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
import com.likander.newsy.core.utils.Constants
import com.likander.newsy.features.search.data.local.mappers.toSearchEntity
import com.likander.newsy.features.search.data.local.model.SearchEntity
import com.likander.newsy.features.search.data.local.model.SearchRemoteKeyEntity
import com.likander.newsy.features.search.data.remote.SearchApi
import okio.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class SearchMediator(
    private val api: SearchApi,
    private val database: NewsArticleDatabase,
    private val query: String
) : RemoteMediator<Int, SearchEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeOut = TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES)
        val cacheIsTimeOut = System.currentTimeMillis() -
                (database.searchRemoteKeyDao().getCreationTime() ?: 0) < cacheTimeOut
        return if (cacheIsTimeOut)
            InitializeAction.SKIP_INITIAL_REFRESH
        else
            InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyFirstItem(state)
                remoteKey?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
            }

            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                remoteKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
            }
        }

        return try {
            val searchResponse = api.fetchSearchArticle(query, page)
            val searchArticles = searchResponse.articles
            val endOfPaginationReached = searchArticles.isNullOrEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.apply {
                        searchRemoteKeyDao().clearRemoteKeys()
                        searchArticleDao().removeAllArticles()
                    }
                }

                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = searchArticles?.map { article ->
                    SearchRemoteKeyEntity(
                        articleId = article.url.toString(),
                        prevKey = prevKey,
                        currentKey = page,
                        nextKey = nextKey
                    )
                }

                database.apply {
                    remoteKeys?.let {
                        searchRemoteKeyDao().insertAll(it)
                    }
                    val articlesEntity =
                        searchArticles?.map { it.toSearchEntity(page, Constants.SEARCH_CATEGORY) }
                    articlesEntity?.let {
                        searchArticleDao().insertAllSearchArticles(it)
                    }
                }
            }

            MediatorResult.Success(endOfPaginationReached)
        } catch (error: IOException) {
            MediatorResult.Error(error)
        } catch (error: HttpException) {
            MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyFirstItem(state: PagingState<Int, SearchEntity>): SearchRemoteKeyEntity? =
        state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { article ->
            database.searchRemoteKeyDao().getRemoteKeyArticleById(article.url)
        }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, SearchEntity>): SearchRemoteKeyEntity? =
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { id ->
                database.searchRemoteKeyDao().getRemoteKeyArticleById(id)
            }
        }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, SearchEntity>): SearchRemoteKeyEntity? =
        state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { article ->
            database.searchRemoteKeyDao().getRemoteKeyArticleById(article.url)
        }
}