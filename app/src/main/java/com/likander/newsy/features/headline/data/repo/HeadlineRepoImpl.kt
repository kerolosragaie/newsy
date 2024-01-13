package com.likander.newsy.features.headline.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.likander.newsy.core.utils.Constants
import com.likander.newsy.features.headline.data.local.database.NewsArticleDatabase
import com.likander.newsy.features.headline.data.mappers.Mapper
import com.likander.newsy.features.headline.data.remote.model.ArticleDto
import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.data.local.models.HeadlineEntity
import com.likander.newsy.features.headline.data.paging.HeadlineMediator
import com.likander.newsy.features.headline.data.remote.api.HeadlineApi
import com.likander.newsy.features.headline.domain.repo.HeadlineRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HeadlineRepoImpl(
     val headlineApi: HeadlineApi,
     val database: NewsArticleDatabase,
     val mapper: Mapper<HeadlineEntity, Article>,
     val articleHeadlineMapper: Mapper<ArticleDto, HeadlineEntity>,
) : HeadlineRepo {
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchHeadlineArticle(
        category: String,
        country: String,
        language: String,
    ): Flow<PagingData<Article>> =
        Pager(
            PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                prefetchDistance = Constants.PAGE_SIZE - 1,
                initialLoadSize = 10,
            ),
            remoteMediator = HeadlineMediator(
                api = headlineApi,
                database = database,
                category = category,
                country = country,
                language = language,
                articleHeadlineMapper = articleHeadlineMapper,
            )
        ) {
            database.headlineDao().getAllHeadlineArticles()
        }.flow
            .map { entityPagingData ->
                entityPagingData.map { entity ->
                    mapper.toModel(entity)
                }
            }

    override suspend fun updateFavouriteArticle(article: Article) {
        database.headlineDao().updateFavouriteArticle(
            id = article.id,
            isFavourite = article.favourite,
        )
    }
}