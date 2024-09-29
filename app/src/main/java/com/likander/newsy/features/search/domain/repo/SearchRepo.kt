package com.likander.newsy.features.search.domain.repo

import androidx.paging.PagingData
import com.likander.newsy.features.search.domain.model.SearchArticle
import kotlinx.coroutines.flow.Flow

interface SearchRepo {
    fun fetchSearchArticles(query: String): Flow<PagingData<SearchArticle>>
    suspend fun updateFavouriteArticle(article: SearchArticle)
}