package com.likander.newsy.features.headline.data.local.data_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.likander.newsy.core.utils.Constants
import com.likander.newsy.features.headline.data.local.dao.HeadlineDao
import com.likander.newsy.features.headline.data.local.database.NewsArticleDatabase
import com.likander.newsy.features.headline.data.local.models.HeadlineEntity
import com.likander.newsy.features.headline.data.mappers.Mapper
import com.likander.newsy.features.headline.data.paging.HeadlineMediator
import com.likander.newsy.features.headline.data.remote.api.HeadlineApi
import com.likander.newsy.features.headline.data.remote.model.ArticleDto
import kotlinx.coroutines.flow.Flow

class HeadlineLocalDataSourceImpl(
    private val headlineDao: HeadlineDao,
    private val newsArticleDatabase: NewsArticleDatabase,
) : HeadlineLocalDataSource {
    @OptIn(ExperimentalPagingApi::class)
    override fun getAllHeadlineArticles(
        articleHeadlineMapper: Mapper<ArticleDto, HeadlineEntity>,
        headlineApi: HeadlineApi,
        category: String,
        country: String,
        language: String,
    ): Flow<PagingData<HeadlineEntity>> = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PAGE_SIZE - 1,
            initialLoadSize = 10,
        ),
        remoteMediator = HeadlineMediator(
            api = headlineApi,
            database = newsArticleDatabase,
            category = category,
            country = country,
            language = language,
            articleHeadlineMapper = articleHeadlineMapper,
        )
    ) {
        newsArticleDatabase.headlineDao().getAllHeadlineArticles()
    }.flow

    override fun getHeadlineArticle(id: Int): Flow<HeadlineEntity> =
        headlineDao.getHeadlineArticle(id)

    override suspend fun insertHeadlineArticles(articles: List<HeadlineEntity>) =
        headlineDao.insertHeadlineArticles(articles)

    override suspend fun removeAllHeadlineArticles() = headlineDao.removeAllHeadlineArticles()

    override suspend fun removeFavouriteArticle(headlineEntity: HeadlineEntity) =
        headlineDao.removeFavouriteArticle(headlineEntity)

    override suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int) =
        headlineDao.updateFavouriteArticle(isFavourite, id)

    override fun getNewsArticleDatabase(): NewsArticleDatabase  = newsArticleDatabase
}