package com.likander.newsy.features.discover.data.remote.data_source

import com.likander.newsy.core.common.data.model.NewsDto
import com.likander.newsy.features.discover.data.remote.api.DiscoverApi

class DiscoverRemoteDataSourceImpl(
    private val discoverApi: DiscoverApi
) : DiscoverRemoteDataSource {
    override suspend fun getDiscoverHeadlines(
        category: String, country: String, language: String, page: Int, pageSize: Int
    ): NewsDto = discoverApi.getDiscoverHeadlines(
        category = category,
        country = country,
        language = language,
        page = page,
        pageSize = pageSize
    )

    override fun getDiscoverApi(): DiscoverApi = discoverApi
}