package com.likander.newsy.features.headline.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.likander.newsy.core.utils.Utils
import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.domain.usecase.HeadlineUseCases
import com.likander.newsy.features.headline.presentation.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HeadlineViewModel @Inject constructor(
    private val headlineUseCases: HeadlineUseCases,
) : ViewModel() {
    var headlineState by mutableStateOf(HomeUiState())
        private set

    init {
        initHeadlineArticleData()
    }

    private fun initHeadlineArticleData() {
        val currentCountryCode = Utils.countryCodeList[0].code
        val currentLanguageCode = Utils.languageCodeList[0].code

        headlineState = headlineState.copy(
            headlineArticles = headlineUseCases
                .fetchHeadlineArticleUseCase(
                    headlineState.selectedHeadlineCategory.category,
                    currentCountryCode,
                    currentLanguageCode
                ).cachedIn(viewModelScope)
        )
    }

    fun onFavouriteChange(article: Article) {
        viewModelScope.launch {
            val isFavourite = article.favourite
            article.copy(
                favourite = !isFavourite
            ).also {
                headlineUseCases.updateHeadlineFavouriteUseCase(it)
            }
        }
    }
}