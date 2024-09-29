package com.likander.newsy.features.search.data.remote

import com.likander.newsy.core.utils.Constants
import com.likander.newsy.features.search.data.remote.model.SearchArticleRemoteDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    private companion object {
        const val END_POINT = "/v2/everything"
    }

    @GET(END_POINT)
    suspend fun fetchSearchArticle(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("apiKey") key: String = Constants.API_KEY
    ): SearchArticleRemoteDto
}