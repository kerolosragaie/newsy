package com.likander.newsy.features.headline.data.remote.data_source

import com.likander.newsy.core.utils.Constants
import com.likander.newsy.features.headline.data.remote.api.HeadlineApi
import com.likander.newsy.features.headline.data.remote.model.NewsDto

interface HeadlineRemoteDataSource {
    suspend fun getHeadLines(
        key: String = Constants.API_KEY,
        category: String,
        country: String,
        language: String,
        page: Int,
        pageSize: Int,
    ): NewsDto

    fun getHeadlineApi(): HeadlineApi
}