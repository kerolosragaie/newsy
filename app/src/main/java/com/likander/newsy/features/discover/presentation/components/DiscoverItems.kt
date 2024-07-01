package com.likander.newsy.features.discover.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.likander.newsy.R
import com.likander.newsy.core.common.components.ErrorContent
import com.likander.newsy.core.common.components.LoadingContent
import com.likander.newsy.core.theme.ITEM_SPACING
import com.likander.newsy.core.utils.ArticleCategory
import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.presentation.components.HeaderTitle
import com.likander.newsy.features.headline.presentation.model.HomeUiState

fun LazyListScope.discoverItems(
    homeUiState: HomeUiState,
    categories: List<ArticleCategory>,
    discoverArticles: LazyPagingItems<Article>,
    onItemClick: (Article) -> Unit,
    onCategoryChange: (articleCategory: ArticleCategory) -> Unit,
    onFavouriteArticleChange: (article: Article) -> Unit,
) {
    item {
        HeaderTitle(
            title = stringResource(R.string.discover_news),
            icon = Icons.Default.Newspaper,
        )
        Spacer(modifier = Modifier.size(ITEM_SPACING))
        DiscoverChips(
            selectedCategory = homeUiState.selectedDiscoverCategory,
            categories = categories,
            onCategoryChange = onCategoryChange
        )
    }

    when {
        discoverArticles.loadState.refresh is LoadState.Loading ->
            item {
                LoadingContent(modifier = Modifier.height(100.dp))
            }

        discoverArticles.loadState.refresh is LoadState.Error ->
            item {
                ErrorContent(
                    message = stringResource(R.string.something_went_wrong)
                )
            }

        discoverArticles.loadState.refresh is LoadState.NotLoading && discoverArticles.itemCount > 0 -> {
            items(
                count = discoverArticles.itemCount,
                key = { index -> index }
            ) { index ->
                discoverArticles[index]?.let { article ->
                    ArticleItem(
                        article = article,
                        onClick = onItemClick,
                        onFavouriteChange = onFavouriteArticleChange
                    )
                }
            }
        }

        discoverArticles.loadState.append.endOfPaginationReached && discoverArticles.itemCount == 0 ->
            item {
                ErrorContent(
                    title = "",
                    message = stringResource(R.string.no_data_found)
                )
            }

        else -> Unit
    }
}