package com.likander.newsy.features.headline.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.likander.newsy.R
import com.likander.newsy.core.common.components.BottomSheet
import com.likander.newsy.core.common.components.FailureBottomSheetContent
import com.likander.newsy.core.common.components.LoadingContent
import com.likander.newsy.core.common.components.PaginationLoadingItem
import com.likander.newsy.core.theme.ITEM_SPACING
import com.likander.newsy.core.theme.NewsyTheme
import com.likander.newsy.core.utils.ArticleCategory
import com.likander.newsy.features.discover.presentation.components.discoverItems
import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.presentation.components.HeaderTitle
import com.likander.newsy.features.headline.presentation.components.HeadlineItems
import com.likander.newsy.features.headline.presentation.components.HomeTopAppBar
import com.likander.newsy.features.headline.presentation.components.fakeArticles
import com.likander.newsy.features.headline.presentation.viewmodel.HomeUiEvents
import com.likander.newsy.features.headline.presentation.viewmodel.HomeUiState
import com.likander.newsy.features.headline.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (Article) -> Unit,
    onDiscoverItemClick: (Article) -> Unit,
    openDrawer: () -> Unit,
    onSearch: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { false }
    )
    var failureMessage: String? by remember { mutableStateOf(null) }
    val closeBottomSheet = {
        coroutineScope.launch { sheetState.hide() }
    }

    val homeState = viewModel.homeUiState
    val headlineArticles = homeState.headlineArticles.collectAsLazyPagingItems()
    val discoverArticles = homeState.discoverArticles.collectAsLazyPagingItems()

    LaunchedEffect(headlineArticles.loadState.mediator?.refresh) {
        when (headlineArticles.loadState.mediator?.refresh) {
            is LoadState.Error -> sheetState.show()
            else -> sheetState.hide()
        }
    }

    BottomSheet(
        sheetState = sheetState,
        sheetContent = {
            FailureBottomSheetContent(
                title = stringResource(R.string.failure),
                description = failureMessage ?: stringResource(R.string.something_went_wrong),
                onOkClick = {
                    closeBottomSheet.invoke()
                    viewModel.loadArticles()
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                HomeTopAppBar(
                    openDrawer = openDrawer,
                    onSearch = onSearch,
                )
            },
        ) { _ ->
            ScreenContent(
                homeUiState = viewModel.homeUiState,
                headlineArticles = headlineArticles,
                discoverArticles = discoverArticles,
                showFailureBottomSheet = {
                    failureMessage = it
                    coroutineScope.launch {
                        sheetState.show()
                    }
                },
                onViewMoreClick = onViewMoreClick,
                onHeadlineItemClick = onHeadlineItemClick,
                onDiscoverItemClick = onDiscoverItemClick,
                onFavouriteHeadlineChange = { article ->
                    viewModel.onHomeUiEvents(
                        HomeUiEvents.OnHeadLineFavouriteChange(article)
                    )
                },
                onFavouriteDiscoverChange = { article ->
                    viewModel.onHomeUiEvents(
                        HomeUiEvents.OnDiscoverFavouriteChange(article)
                    )
                },
                onDiscoverCategoryChange = {
                    viewModel.onHomeUiEvents(
                        HomeUiEvents.CategoryChange(it)
                    )
                }
            )
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    headlineArticles: LazyPagingItems<Article>,
    discoverArticles: LazyPagingItems<Article>,
    showFailureBottomSheet: (error: String) -> Unit,
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (Article) -> Unit,
    onDiscoverItemClick: (Article) -> Unit,
    onFavouriteHeadlineChange: (Article) -> Unit,
    onFavouriteDiscoverChange: (Article) -> Unit,
    onDiscoverCategoryChange: (ArticleCategory) -> Unit
) {
    val categories = ArticleCategory.entries
    val headlineArticlesList = headlineArticles.itemSnapshotList.items

    LazyColumn(modifier = modifier) {
        item {
            HeaderTitle(
                title = stringResource(R.string.hot_news),
                icon = Icons.Default.LocalFireDepartment,
            )
            Spacer(modifier = Modifier.size(ITEM_SPACING))
        }

        item {
            PaginationLoadingItem(
                pagingState = headlineArticles.loadState.mediator?.refresh,
                onError = { e ->
                    showFailureBottomSheet.invoke(
                        e.message ?: stringResource(R.string.unknown_error)
                    )
                },
                onLoading = { LoadingContent() },
                onSuccess = {
                    HeadlineItems(
                        articles = headlineArticlesList,
                        onCardClick = onHeadlineItemClick,
                        onViewMoreClick = onViewMoreClick,
                        onFavouriteChange = { onFavouriteHeadlineChange.invoke(it) },
                    )
                }
            )
        }

        discoverItems(
            homeUiState = homeUiState,
            categories = categories,
            discoverArticles = discoverArticles,
            onItemClick = onDiscoverItemClick,
            onCategoryChange = onDiscoverCategoryChange,
            onFavouriteArticleChange = onFavouriteDiscoverChange,
        )
    }
}

@PreviewLightDark
@Composable
private fun PrevHomeScreen() {
    val headlineArticles: Flow<PagingData<Article>> = flow { fakeArticles }

    NewsyTheme {
        Surface {
            ScreenContent(
                headlineArticles = headlineArticles.collectAsLazyPagingItems(),
                onViewMoreClick = {},
                onHeadlineItemClick = {},
                onDiscoverItemClick = {},
                onFavouriteHeadlineChange = {},
                showFailureBottomSheet = {},
                discoverArticles = headlineArticles.collectAsLazyPagingItems(),
                homeUiState = HomeUiState(),
                onDiscoverCategoryChange = {},
                onFavouriteDiscoverChange = {}
            )
        }
    }
}