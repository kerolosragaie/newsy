package com.likander.newsy.features.search.data.remote.data_source

import com.likander.newsy.features.search.data.remote.SearchApi
import com.likander.newsy.features.search.data.remote.model.SearchArticleRemoteDto

class SearchRemoteDataSourceImpl(private val api: SearchApi) : SearchRemoteDataSource {
    override suspend fun getSearchArticles(query: String, page: Int): SearchArticleRemoteDto =
        api.fetchSearchArticle(query = query, page = page)

    override fun getSearchApi(): SearchApi = api
}