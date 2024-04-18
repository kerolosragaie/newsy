package com.likander.newsy.features.discover.domain.usecase

import com.likander.newsy.features.discover.domain.repo.DiscoverRepo
import com.likander.newsy.features.headline.domain.model.Article

class UpdateFavouriteArticleUseCase(private val discoverRepo: DiscoverRepo) {
    suspend operator fun invoke(article: Article): Int =
        discoverRepo.updateFavouriteArticle(article)
}