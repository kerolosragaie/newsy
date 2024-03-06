package com.likander.newsy.features.headline.data.remote.data_source

import com.likander.newsy.core.common.data.model.NewsDto
import com.likander.newsy.core.utils.Constants
import com.likander.newsy.features.headline.data.remote.api.HeadlineApi

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