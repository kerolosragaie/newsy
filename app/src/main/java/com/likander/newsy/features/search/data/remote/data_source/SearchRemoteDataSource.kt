package com.likander.newsy.features.search.data.remote.data_source

import com.likander.newsy.features.search.data.remote.SearchApi
import com.likander.newsy.features.search.data.remote.model.SearchArticleRemoteDto

interface SearchRemoteDataSource {
    suspend fun getSearchArticles(query: String, page: Int): SearchArticleRemoteDto

    fun getSearchApi(): SearchApi
}