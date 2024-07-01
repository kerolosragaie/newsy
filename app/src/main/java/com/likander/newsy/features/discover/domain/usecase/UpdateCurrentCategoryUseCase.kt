package com.likander.newsy.features.discover.domain.usecase

import com.likander.newsy.features.discover.domain.repo.DiscoverRepo

class UpdateCurrentCategoryUseCase(private val discoverRepo: DiscoverRepo) {
    suspend operator fun invoke(category: String) {
        discoverRepo.updateCategory(category)
    }
}