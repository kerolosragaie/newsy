package com.likander.newsy.features.discover.domain.usecase

data class DiscoverUseCases(
    val fetchDiscoverArticlesUseCase: FetchDiscoverArticlesUseCase,
    val getDiscoverCurrentCategoryUseCase: GetDiscoverCurrentCategoryUseCase,
    val updateCurrentCategoryUseCase: UpdateCurrentCategoryUseCase,
    val updateFavouriteArticleUseCase: UpdateFavouriteArticleUseCase
)
