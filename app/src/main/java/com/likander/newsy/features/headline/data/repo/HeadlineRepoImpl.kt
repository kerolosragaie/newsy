package com.likander.newsy.features.headline.data.repo

import androidx.paging.PagingData
import androidx.paging.map
import com.likander.newsy.features.headline.data.local.data_source.HeadlineLocalDataSource
import com.likander.newsy.features.headline.data.mappers.Mapper
import com.likander.newsy.features.headline.data.remote.model.ArticleDto
import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.data.local.models.HeadlineEntity
import com.likander.newsy.features.headline.data.remote.data_source.HeadlineRemoteDataSource
import com.likander.newsy.features.headline.domain.repo.HeadlineRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HeadlineRepoImpl(
    private val headlineRemoteDataSource: HeadlineRemoteDataSource,
    private val headlineLocalDataSource: HeadlineLocalDataSource,
    val mapper: Mapper<HeadlineEntity, Article>,
    val articleHeadlineMapper: Mapper<ArticleDto, HeadlineEntity>,
) : HeadlineRepo {
    override fun fetchHeadlineArticle(
        category: String,
        country: String,
        language: String,
    ): Flow<PagingData<Article>> = headlineLocalDataSource.getAllHeadlineArticles(
        articleHeadlineMapper,
        headlineRemoteDataSource.getHeadlineApi(),
        category,
        country,
        language,
    ).map { entityPagingData ->
        entityPagingData.map { entity ->
            mapper.toModel(entity)
        }
    }


    override suspend fun updateFavouriteArticle(article: Article) {
        headlineLocalDataSource.updateFavouriteArticle(
            id = article.id,
            isFavourite = article.favourite,
        )
    }
}