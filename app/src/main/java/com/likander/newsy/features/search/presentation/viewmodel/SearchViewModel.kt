package com.likander.newsy.features.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.likander.newsy.features.search.domain.usecase.SearchUseCases
import com.likander.newsy.features.search.presentation.model.SearchUiEvents
import com.likander.newsy.features.search.presentation.model.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCases: SearchUseCases) : ViewModel() {
    private val _uiState: MutableStateFlow<SearchUiState> by lazy { MutableStateFlow(SearchUiState()) }
    val uiState: StateFlow<SearchUiState> by lazy { _uiState }

    fun onUiEvent(event: SearchUiEvents) {
        when (event) {
            is SearchUiEvents.OnSearchChange -> _uiState.update { it.copy(query = event.query) }
            is SearchUiEvents.OnFavouriteChange -> {
                event.searchArticle.copy(
                    favourite = !event.searchArticle.favourite
                ).also {
                    viewModelScope.launch {
                        useCases.updateSearchFavourite(it)
                    }
                }
            }

            is SearchUiEvents.OnSubmitSearch -> {
                _uiState.update {
                    it.copy(
                        searchArticles = useCases.fetchSearchArticle(_uiState.value.query.toString())
                            .cachedIn(viewModelScope)
                    )
                }
            }

            is SearchUiEvents.SaveSearchHistory -> _uiState.update {
                it.copy(searchHistory = _uiState.value.searchHistory + listOf(event.historyQuery))
            }

            is SearchUiEvents.ClearSearchHistory -> _uiState.update { it.copy(searchHistory = emptyList()) }
        }
    }
}