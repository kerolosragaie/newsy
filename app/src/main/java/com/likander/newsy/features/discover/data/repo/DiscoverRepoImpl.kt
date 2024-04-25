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
        discoverApi = discoverRemoteDataSource.getDiscoverApi(),
        category = category,
        country = country,
        language = language
    ).map { discoverArticleEntityPagingData ->
        discoverArticleEntityPagingData.map {
            mapper.toModel(it)
        }
    }

    override suspend fun getDiscoverArticleCurrentCategory(): String =
        discoverLocalDataSource.getNewsArticleDatabase().discoverRemoteKeyDao().getCurrentCategory()

    override suspend fun getAllAvailableCategories(): List<String> =
        discoverLocalDataSource.getNewsArticleDatabase().discoverRemoteKeyDao()
            .getAllAvailableCategories()

    override suspend fun updateCategory(category: String) {
        discoverLocalDataSource.getNewsArticleDatabase().discoverRemoteKeyDao()
            .updateCategory(category)
    }

    override suspend fun updateFavouriteArticle(article: Article) =
        discoverLocalDataSource.updateFavouriteArticle(
            id = article.id,
            isFavourite = article.favourite
        )
}