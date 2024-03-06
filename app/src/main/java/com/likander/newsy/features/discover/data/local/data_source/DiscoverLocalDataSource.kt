package com.likander.newsy.features.discover.data.local.data_source

import androidx.paging.PagingData
import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
import com.likander.newsy.features.discover.data.local.model.DiscoverArticleEntity
import com.likander.newsy.features.discover.data.remote.api.DiscoverApi
import kotlinx.coroutines.flow.Flow

interface DiscoverLocalDataSource {
    fun getDiscoverArticles(
        discoverApi: DiscoverApi,
        category: String = "",
        country: String = "",
        language: String = ""
    ): Flow<PagingData<DiscoverArticleEntity>>

    fun getDiscoverArticle(id: Int): Flow<DiscoverArticleEntity>
    suspend fun removeAllDiscoverArticles(category: String)
    suspend fun removeFavouriteArticle(discoverArticleEntity: DiscoverArticleEntity)
    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int): Int
    fun getNewsArticleDatabase(): NewsArticleDatabase
}