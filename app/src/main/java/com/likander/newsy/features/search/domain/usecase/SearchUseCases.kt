package com.likander.newsy.features.search.domain.usecase

data class SearchUseCases(
    val fetchSearchArticle: FetchSearchArticleUseCase,
    val updateSearchFavourite: UpdateFavouriteSearchArticleUseCase
)
