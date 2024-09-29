package com.likander.newsy.features.search.data.repo

import androidx.paging.PagingData
import androidx.paging.map
import com.likander.newsy.features.search.data.local.data_source.SearchLocalDataSource
import com.likander.newsy.features.search.data.local.mappers.toDomainModel
import com.likander.newsy.features.search.data.remote.data_source.SearchRemoteDataSource
import com.likander.newsy.features.search.domain.model.SearchArticle
import com.likander.newsy.features.search.domain.repo.SearchRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepoImpl(
    private val localDataSource: SearchLocalDataSource,
    private val remoteDataSource: SearchRemoteDataSource
) : SearchRepo {
    override fun fetchSearchArticles(query: String): Flow<PagingData<SearchArticle>> =
        localDataSource.getAllSearchArticles(
            remoteDataSource.getSearchApi(),
            query
        ).map { entityPagingData ->
            entityPagingData.map { entity ->
                entity.toDomainModel()
            }
        }

    override suspend fun updateFavouriteArticle(article: SearchArticle) =
        localDataSource.updateFavouriteArticle(
            isFavourite = article.favourite,
            id = article.id
        )
}