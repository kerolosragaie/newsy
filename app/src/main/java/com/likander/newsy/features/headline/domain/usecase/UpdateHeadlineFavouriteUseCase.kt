package com.likander.newsy.features.headline.domain.usecase

import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.domain.repo.HeadlineRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateHeadlineFavouriteUseCase(private val headlineRepo: HeadlineRepo) {
    suspend operator fun invoke(article: Article) = withContext(Dispatchers.IO) {
        headlineRepo.updateFavouriteArticle(article)
    }
}