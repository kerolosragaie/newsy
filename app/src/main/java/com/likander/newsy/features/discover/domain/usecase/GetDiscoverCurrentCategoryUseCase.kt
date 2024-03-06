package com.likander.newsy.features.discover.domain.usecase

import com.likander.newsy.features.discover.domain.repo.DiscoverRepo

class GetDiscoverCurrentCategoryUseCase(
    private val discoverRepo: DiscoverRepo
) {
    suspend operator fun invoke(): String = discoverRepo.getDiscoverArticleCurrentCategory()
}