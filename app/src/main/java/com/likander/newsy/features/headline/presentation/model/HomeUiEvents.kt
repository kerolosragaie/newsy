package com.likander.newsy.features.headline.presentation.model

import com.likander.newsy.core.utils.ArticleCategory
import com.likander.newsy.features.headline.domain.model.Article

sealed class HomeUiEvents {
    data object ViewMoreClicked : HomeUiEvents()
    data class ArticleClicked(val url: String) : HomeUiEvents()
    data class CategoryChange(val category: ArticleCategory) : HomeUiEvents()
    data class PreferencePanelToggle(val isOpen: Boolean) : HomeUiEvents()
    data class OnHeadLineFavouriteChange(val article: Article) : HomeUiEvents()
    data class OnDiscoverFavouriteChange(val article: Article) : HomeUiEvents()
}