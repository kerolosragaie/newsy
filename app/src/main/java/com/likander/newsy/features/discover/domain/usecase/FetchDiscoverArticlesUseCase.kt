package com.likander.newsy.features.discover.domain.usecase

import androidx.paging.PagingData
import com.likander.newsy.features.discover.domain.repo.DiscoverRepo
import com.likander.newsy.features.headline.domain.model.Article
import kotlinx.coroutines.flow.Flow

class FetchDiscoverArticlesUseCase(
    private val discoverRepo: DiscoverRepo
) {
    operator fun invoke(
        category: String,
        country: String,
        language: String,
    ): Flow<PagingData<Article>> = discoverRepo.fetchDiscoverArticles(
        category = category,
        country = country,
        language = language
    )
}