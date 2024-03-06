package com.likander.newsy.features.headline.data.remote.api

import com.likander.newsy.core.utils.Constants
import com.likander.newsy.core.common.data.model.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HeadlineApi {
    companion object {
        private const val HEADLINE_END_POINT = "/v2/top-headlines"
    }

    @GET(HEADLINE_END_POINT)
    suspend fun getHeadLines(
        @Query("apiKey") key: String = Constants.API_KEY,
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): NewsDto
}