package com.likander.newsy.features.headline.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.likander.newsy.core.utils.Utils
import com.likander.newsy.features.discover.domain.usecase.DiscoverUseCases
import com.likander.newsy.features.headline.domain.usecase.HeadlineUseCases
import com.likander.newsy.features.headline.presentation.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val headlineUseCases: HeadlineUseCases,
    private val discoverUseCases: DiscoverUseCases,
) : ViewModel() {

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadArticles()
    }

    fun loadArticles() {
        updateHeadlineArticles()
        updateDiscoverArticles()
    }

    fun onHomeUiEvents(homeUiEvents: HomeUiEvents) {
        when (homeUiEvents) {
            is HomeUiEvents.ArticleClicked -> TODO()
            is HomeUiEvents.CategoryChange -> {
                updateCategory(homeUiEvents)
                updateDiscoverArticles()
            }

            is HomeUiEvents.OnHeadLineFavouriteChange -> onHeadLineFavouriteChange(homeUiEvents)
            is HomeUiEvents.OnDiscoverFavouriteChange -> onDiscoverFavouriteChange(homeUiEvents)
            is HomeUiEvents.PreferencePanelToggle -> TODO()
            is HomeUiEvents.ViewMoreClicked -> TODO()
        }
    }

    private fun updateCategory(homeUiEvents: HomeUiEvents.CategoryChange) {
        homeUiState = homeUiState.copy(
            selectedDiscoverCategory = homeUiEvents.category
        )
    }

    private fun updateHeadlineArticles() {
        homeUiState = homeUiState.copy(
            headlineArticles = headlineUseCases.fetchHeadlineArticleUseCase(
                category = homeUiState.selectedHeadlineCategory.category,
                country = Utils.countryCodeList[0].code,
                language = Utils.languageCodeList[0].code,
            ).cachedIn(viewModelScope)
        )
    }

    private fun updateDiscoverArticles() {
        homeUiState = homeUiState.copy(
            discoverArticles = discoverUseCases.fetchDiscoverArticlesUseCase(
                category = homeUiState.selectedDiscoverCategory.category,
                country = Utils.countryCodeList[0].code,
                language = Utils.languageCodeList[0].code,
            ).cachedIn(viewModelScope)
        )
    }

    private fun onHeadLineFavouriteChange(homeUiEvents: HomeUiEvents.OnHeadLineFavouriteChange) =
        viewModelScope.launch {
            val isFavourite = homeUiEvents.article.favourite
            val updatedArticle = homeUiEvents.article.copy(
                favourite = !isFavourite
            )
            headlineUseCases.updateHeadlineFavouriteUseCase(updatedArticle)
        }

    private fun onDiscoverFavouriteChange(homeUiEvents: HomeUiEvents.OnDiscoverFavouriteChange) =
        viewModelScope.launch {
            val isFavourite = homeUiEvents.article.favourite
            val updatedArticle = homeUiEvents.article.copy(
                favourite = !isFavourite
            )
            discoverUseCases.updateFavouriteArticleUseCase(updatedArticle)
        }
}