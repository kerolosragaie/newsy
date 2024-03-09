package com.likander.newsy.features.discover.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.likander.newsy.core.common.components.LoadingContent
import com.likander.newsy.core.common.components.PaginationLoadingItem
import com.likander.newsy.core.theme.itemSpacing
import com.likander.newsy.core.utils.ArticleCategory
import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.presentation.viewmodel.HomeUiState
import com.likander.newsy.features.home.components.HeaderTitle

@Composable
fun LazyListScope.discoverItems(
    homeUiState: HomeUiState,
    categories: List<ArticleCategory>,
    discoverArticles: LazyPagingItems<Article>,
    onItemClick: (id: Int) -> Unit,
    onCategoryChange: (articleCategory: ArticleCategory) -> Unit,
    onFavouriteArticleChange: (article: Article) -> Unit,
    showFailureBottomSheet: (error: String) -> Unit,
) {
    item {
        HeaderTitle(
            title = "Discover News",
            icon = Icons.Default.Newspaper,
        )
        Spacer(modifier = Modifier.size(itemSpacing))
        DiscoverChips(
            selectedCategory = homeUiState.selectedDiscoverCategory,
            categories = categories,
            onCategoryChange = onCategoryChange
        )
    }

    PaginationLoadingItem(
        pagingState = discoverArticles.loadState.mediator?.refresh,
        onError = { e ->
            showFailureBottomSheet.invoke(e.message ?: "unknown error")
        },
        onLoading = { LoadingContent() },
        onSuccess = {

        }
    )
}