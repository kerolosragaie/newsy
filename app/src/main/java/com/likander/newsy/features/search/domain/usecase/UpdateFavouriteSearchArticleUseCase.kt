package com.likander.newsy.features.search.domain.usecase

import com.likander.newsy.features.search.domain.model.SearchArticle
import com.likander.newsy.features.search.domain.repo.SearchRepo

class UpdateFavouriteSearchArticleUseCase(private val searchRepo: SearchRepo) {
    suspend operator fun invoke(article: SearchArticle) =
        searchRepo.updateFavouriteArticle(article)
}