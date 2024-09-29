package com.likander.newsy.features.search.data.local.data_source

import androidx.paging.PagingData
import com.likander.newsy.features.search.data.local.model.SearchEntity
import com.likander.newsy.features.search.data.remote.SearchApi
import kotlinx.coroutines.flow.Flow

interface SearchLocalDataSource {
    fun getAllSearchArticles(
        searchApi: SearchApi,
        query: String
    ): Flow<PagingData<SearchEntity>>

    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int)
}