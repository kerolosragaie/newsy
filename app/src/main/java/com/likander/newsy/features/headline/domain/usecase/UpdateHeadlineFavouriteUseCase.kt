package com.likander.newsy.features.headline.domain.usecase

import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.domain.repo.HeadlineRepo

class UpdateHeadlineFavouriteUseCase(private val headlineRepo: HeadlineRepo) {
    suspend operator fun invoke(article: Article): Int =
        headlineRepo.updateFavouriteArticle(article)
}