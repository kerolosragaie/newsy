package com.likander.newsy.features.discover.data.remote.data_source

import com.likander.newsy.core.common.data.model.NewsDto
import com.likander.newsy.features.discover.data.remote.api.DiscoverApi

interface DiscoverRemoteDataSource {
    suspend fun getDiscoverHeadlines(
        category: String,
        country: String,
        language: String,
        page: Int,
        pageSize: Int
    ): NewsDto

    fun getDiscoverApi(): DiscoverApi
}