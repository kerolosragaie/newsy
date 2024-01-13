package com.likander.newsy.features.headline.domain.usecase

import androidx.paging.PagingData
import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.domain.repo.HeadlineRepo
import kotlinx.coroutines.flow.Flow

class FetchHeadlineArticleUseCase(private val headlineRepo: HeadlineRepo) {
    operator fun invoke(
        category: String,
        country: String,
        language: String,
    ): Flow<PagingData<Article>> =
        headlineRepo.fetchHeadlineArticle(
            category,
            country,
            language,
        )

}