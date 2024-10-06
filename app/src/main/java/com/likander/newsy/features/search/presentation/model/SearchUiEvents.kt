package com.likander.newsy.features.search.presentation.model

import com.likander.newsy.features.search.domain.model.SearchArticle

sealed class SearchUiEvents {
    data class OnSearchChange(val query: String) : SearchUiEvents()
    data object OnSubmitSearch : SearchUiEvents()
    data class SaveSearchHistory(val historyQuery: String) : SearchUiEvents()
    data object ClearSearchHistory : SearchUiEvents()
    data class OnFavouriteChange(val searchArticle: SearchArticle) : SearchUiEvents()
}