package com.likander.newsy.features.headline.presentation.viewmodel

import androidx.paging.PagingData
import com.likander.newsy.core.utils.ArticleCategory
import com.likander.newsy.features.headline.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeUiState(
    val headlineArticles: Flow<PagingData<Article>> = emptyFlow(),
    val discoverArticles: Flow<PagingData<Article>> = emptyFlow(),
    val selectedHeadlineCategory: ArticleCategory = ArticleCategory.BUSINESS,
    val selectedDiscoverCategory: ArticleCategory = ArticleCategory.SPORTS,
)
