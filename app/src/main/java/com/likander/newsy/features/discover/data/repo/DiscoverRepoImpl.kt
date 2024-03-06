package com.likander.newsy.features.discover.data.repo

import androidx.paging.PagingData
import androidx.paging.map
import com.likander.newsy.core.common.data.mappers.Mapper
import com.likander.newsy.features.discover.data.local.data_source.DiscoverLocalDataSource
import com.likander.newsy.features.discover.data.local.model.DiscoverArticleEntity
import com.likander.newsy.features.discover.data.remote.data_source.DiscoverRemoteDataSource
import com.likander.newsy.features.discover.domain.repo.DiscoverRepo
import com.likander.newsy.features.headline.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiscoverRepoImpl(
    private val discoverRemoteDataSource: DiscoverRemoteDataSource,
    private val discoverLocalDataSource: DiscoverLocalDataSource,
    private val mapper: Mapper<DiscoverArticleEntity, Article>
) : DiscoverRepo {
    override fun fetchDiscoverArticles(
        category: String,
        country: String,
        language: String,
    ): Flow<PagingData<Article>> = discoverLocalDataSource.getDiscoverArticles(
        discoverRemoteDataSource.getDiscoverApi(),
        category,
        country,
        language
    ).map { discoverArticleEntityPagingData ->
        discoverArticleEntityPagingData.map {
            mapper.toModel(it)
        }
    }

    override suspend fun updateFavouriteArticle(article: Article) =
        discoverLocalDataSource.updateFavouriteArticle(
            article.favourite,
            article.id
        )
}