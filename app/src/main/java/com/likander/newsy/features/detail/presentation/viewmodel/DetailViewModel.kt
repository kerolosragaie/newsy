package com.likander.newsy.features.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likander.newsy.core.common.navigation.graphs.AppDestinations
import com.likander.newsy.core.utils.Constants
import com.likander.newsy.features.detail.domain.usecase.DetailUseCases
import com.likander.newsy.features.detail.presentation.model.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCases: DetailUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val id: Int by lazy { savedStateHandle[Constants.ARTICLE_ID] ?: -1 }
    private val route: String by lazy { savedStateHandle[Constants.SCREEN_TYPE] ?: "unknown" }

    private val _detailUiState by lazy { MutableStateFlow(DetailUiState()) }
    val detailUiState by lazy { _detailUiState.asStateFlow() }

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        _detailUiState.value = when (route) {
            AppDestinations.DiscoverScreen.route ->
                _detailUiState.value.copy(
                    selectedDetailArticle = detailUseCases.getDetailDiscoverArticleUseCase(id),
                    error = false
                )

            AppDestinations.HeadlineScreen.route ->
                _detailUiState.value.copy(
                    selectedDetailArticle = detailUseCases.getDetailHeadlineArticleUseCase(id),
                    error = false
                )

            else -> _detailUiState.value.copy(error = true)
        }
    }
}