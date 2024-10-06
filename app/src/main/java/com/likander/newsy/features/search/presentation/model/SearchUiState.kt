package com.likander.newsy.features.search.presentation.model

import androidx.paging.PagingData
import com.likander.newsy.features.search.domain.model.SearchArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchUiState(
    val searchArticles: Flow<PagingData<SearchArticle>> = emptyFlow(),
    val query: String? = null,
    val searchHistory: List<String> = emptyList()
)