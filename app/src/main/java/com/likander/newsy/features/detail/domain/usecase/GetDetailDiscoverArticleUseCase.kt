package com.likander.newsy.features.detail.domain.usecase

import com.likander.newsy.core.utils.UiState
import com.likander.newsy.features.detail.domain.model.DetailArticle
import com.likander.newsy.features.detail.domain.repo.DetailRepo
import kotlinx.coroutines.flow.Flow

class GetDetailDiscoverArticleUseCase(private val detailRepo: DetailRepo) {
    suspend operator fun invoke(id: Int): Flow<UiState<DetailArticle>> =
        detailRepo.getDiscoverArticle(id)
}