package com.likander.newsy.features.discover.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
import com.likander.newsy.features.discover.data.local.mappers.toDiscoverArticleEntity
import com.likander.newsy.features.discover.data.local.model.DiscoverArticleEntity
import com.likander.newsy.features.discover.data.local.model.DiscoverRemoteArticleKeyEntity
import com.likander.newsy.features.discover.data.remote.api.DiscoverApi
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class DiscoverMediator(
    private val discoverApi: DiscoverApi,
    private val database: NewsArticleDatabase,
    private val category: String = "",
    private val country: String = "",
    private val language: String = "",
) : RemoteMediator<Int, DiscoverArticleEntity>() {
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(20, TimeUnit.MINUTES)
        val isCacheTimeOut =
            System.currentTimeMillis() - (database.discoverRemoteKeyDao().getCreationTime()
                ?: 0) < cacheTimeout

        return if (isCacheTimeOut) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DiscoverArticleEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyFirstItem(state)
                val prevKey = remoteKey?.prevKey
                prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
            }

            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey
                nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
            }
        }

        return try {
            val discoverArticlesApiResponse = discoverApi.getDiscoverHeadlines(
                category = category,
                country = country,
                language = language,
                page = page,
                pageSize = state.config.pageSize
            )
            val discoverArticles = discoverArticlesApiResponse.articles
            val endOfPaginationReached = discoverArticles.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.discoverRemoteKeyDao().clearRemoteKey(category)
                    database.discoverDao().removeAllDiscoverArticles(category)
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = discoverArticles.map {
                    DiscoverRemoteArticleKeyEntity(
                        articleId = it.url.toString(),
                        prevKey = prevKey,
                        nextKey = nextKey,
                        currentPage = page,
                        currentCategory = category
                    )
                }
                database.discoverRemoteKeyDao().insertAll(remoteKeys)
                database.discoverDao().insertAllArticles(
                    discoverArticles.map {
                        it.toDiscoverArticleEntity(page, category)
                    }
                )

            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyFirstItem(
        state: PagingState<Int, DiscoverArticleEntity>
    ): DiscoverRemoteArticleKeyEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { article ->
            database.discoverRemoteKeyDao().getRemoteKeyByDiscoverArticleId(article.url!!)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, DiscoverArticleEntity>,
    ): DiscoverRemoteArticleKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { id ->
                database.discoverRemoteKeyDao().getRemoteKeyByDiscoverArticleId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, DiscoverArticleEntity>,
    ): DiscoverRemoteArticleKeyEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { article ->
            database.discoverRemoteKeyDao().getRemoteKeyByDiscoverArticleId(article.url!!)
        }
    }
}