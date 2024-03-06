package com.likander.newsy.features.headline.data.remote.data_source

import com.likander.newsy.core.common.data.model.NewsDto
import com.likander.newsy.features.headline.data.remote.api.HeadlineApi

class HeadlineRemoteDataSourceImpl(
    private val headlineApi: HeadlineApi
) : HeadlineRemoteDataSource {
    override suspend fun getHeadLines(
        key: String,
        category: String,
        country: String,
        language: String,
        page: Int,
        pageSize: Int
    ): NewsDto = headlineApi.getHeadLines(
        key = key,
        category = category,
        country = country,
        language = language,
        page = page,
        pageSize = pageSize
    )

    override fun getHeadlineApi(): HeadlineApi = headlineApi
}