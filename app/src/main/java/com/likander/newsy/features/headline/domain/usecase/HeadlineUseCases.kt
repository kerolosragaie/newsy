package com.likander.newsy.features.headline.domain.usecase

data class HeadlineUseCases(
    val fetchHeadlineArticleUseCase: FetchHeadlineArticleUseCase,
    val updateHeadlineFavouriteUseCase: UpdateHeadlineFavouriteUseCase,
)
