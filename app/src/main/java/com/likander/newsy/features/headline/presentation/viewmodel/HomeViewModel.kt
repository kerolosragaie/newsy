package com.likander.newsy.features.headline.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.likander.newsy.core.utils.Utils
import com.likander.newsy.features.headline.domain.usecase.HeadlineUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val headlineUseCases: HeadlineUseCases,
) : ViewModel() {

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadArticles()
    }

     fun loadArticles() {
        homeUiState = homeUiState.copy(
            headlineArticles = headlineUseCases.fetchHeadlineArticleUseCase(
                category = homeUiState.selectedHeadlineCategory.category,
                country = Utils.countryCodeList[0].code,
                language = Utils.languageCodeList[0].code,
            ).cachedIn(viewModelScope)
        )
    }

    fun onHomeUiEvents(homeUiEvents: HomeUiEvents) {
        when (homeUiEvents) {
            is HomeUiEvents.ArticleClicked -> TODO()
            is HomeUiEvents.CategoryChange -> TODO()
            is HomeUiEvents.OnHeadLineFavouriteChange -> viewModelScope.launch {
                val isFavourite = homeUiEvents.article.favourite
                val updatedArticle = homeUiEvents.article.copy(
                    favourite = !isFavourite
                )
                headlineUseCases.updateHeadlineFavouriteUseCase(
                    updatedArticle
                )
            }

            is HomeUiEvents.PreferencePanelToggle -> TODO()
            HomeUiEvents.ViewMoreClicked -> TODO()
        }
    }

}