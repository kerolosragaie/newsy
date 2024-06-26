package com.likander.newsy.features.discover.data.remote.api

import com.likander.newsy.core.common.data.model.NewsDto
import com.likander.newsy.core.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {
    companion object {
        private const val DISCOVER_END_POINT = "/v2/top-headlines"
    }

    @GET(DISCOVER_END_POINT)
    suspend fun getDiscoverHeadlines(
        @Query("apiKey") key: String = Constants.API_KEY,
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsDto
}