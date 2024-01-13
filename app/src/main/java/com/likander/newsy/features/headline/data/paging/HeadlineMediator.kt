package com.likander.newsy.features.headline.data.paging

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.likander.newsy.features.core.data.local.database.NewsArticleDatabase
import com.likander.newsy.features.core.data.mappers.Mapper
import com.likander.newsy.features.core.data.remote.models.ArticleDto
import com.likander.newsy.features.headline.data.local.models.HeadlineEntity
import com.likander.newsy.features.headline.data.local.models.HeadlineRemoteKeyEntity
import com.likander.newsy.features.headline.data.remote.HeadlineApi
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class HeadlineMediator(
    private val api: HeadlineApi,
    private val database: NewsArticleDatabase,
    private val articleHeadlineMapper: Mapper<ArticleDto, HeadlineEntity>,
    private val category: String = "",
    private val country: String = "",
    private val language: String = "",
) : RemoteMediator<Int, HeadlineEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(20, TimeUnit.MINUTES)
        return if (System.currentTimeMillis() - (database.headlineRemoteKeyDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, HeadlineEntity>
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
            val headlineApiResponse = api.getHeadLines(
                pageSize = state.config.pageSize,
                category = category,
                page = page,
                country = country,
                language = language,
            )

            val headlineArticles = headlineApiResponse.articles

            val endOfPaginationReached = headlineArticles.isEmpty()

            database.apply {
                if (loadType == LoadType.REFRESH) {
                    database.apply {
                        headlineRemoteKeyDao().clearRemoteKeys()
                        headlineDao().removeAllHeadlineArticles()
                    }
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = headlineArticles.map {
                    HeadlineRemoteKeyEntity(
                        articleId = it.url!!,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        currentPage = page,
                    )
                }
                database.apply {
                    headlineRemoteKeyDao().insertAll(remoteKeys)
                    headlineDao().insertHeadlineArticles(
                        articles = headlineArticles.map {
                            articleHeadlineMapper.toModel(it)
                        },
                    )
                }
            }

            MediatorResult.Success(endOfPaginationReached)
        } catch (error: IOException) {
            MediatorResult.Error(error)
        } catch (error: HttpException) {
            MediatorResult.Error(error)
        }
    }


    private suspend fun getRemoteKeyFirstItem(
        state: PagingState<Int, HeadlineEntity>
    ): HeadlineRemoteKeyEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { article ->
            database.headlineRemoteKeyDao().getRemoteKeyByArticleId(article.url!!)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, HeadlineEntity>,
    ): HeadlineRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { id ->
                database.headlineRemoteKeyDao().getRemoteKeyByArticleId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, HeadlineEntity>,
    ): HeadlineRemoteKeyEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { article ->
            database.headlineRemoteKeyDao().getRemoteKeyByArticleId(article.url!!)
        }
    }


}