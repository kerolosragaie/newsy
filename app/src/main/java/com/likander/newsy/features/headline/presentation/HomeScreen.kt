package com.likander.newsy.features.headline.presentation

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
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
import com.likander.newsy.core.theme.NewsyTheme
import com.likander.newsy.core.theme.itemSpacing
import com.likander.newsy.core.utils.ArticleCategory
import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.presentation.components.HeadlineItem
import com.likander.newsy.features.headline.presentation.components.fakeArticles
import com.likander.newsy.features.headline.presentation.viewmodel.HomeUiEvents
import com.likander.newsy.features.headline.presentation.viewmodel.HomeViewModel
import com.likander.newsy.features.home.components.HeaderTitle
import com.likander.newsy.features.home.components.HomeTopAppBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (id: Int) -> Unit,
    openDrawer: () -> Unit,
    onSearch: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { false }
    )
    var failureMessage: String? by remember { mutableStateOf(null) }
    val closeBottomSheet = {
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    val homeState = viewModel.homeUiState
    val headlineArticles = homeState.headlineArticles.collectAsLazyPagingItems()
    val categories = ArticleCategory.values()

    LaunchedEffect(headlineArticles.loadState.mediator?.refresh) {
        when (headlineArticles.loadState.mediator?.refresh) {
            is LoadState.Error -> sheetState.show()
            is LoadState.Loading -> sheetState.hide()
            is LoadState.NotLoading -> sheetState.hide()
            else -> sheetState.hide()
        }
    }

    BottomSheet(
        sheetState = sheetState,
        sheetContent = {
            FailureBottomSheetContent(
                title = R.string.failure,
                description = failureMessage ?: stringResource(id = R.string.something_went_wrong),
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
        ) { innerPadding ->
            HeadlinesList(
                contentPadding = innerPadding,
                headlineArticles = headlineArticles,
                showFailureBottomSheet = {
                    failureMessage = it
                    coroutineScope.launch {
                        sheetState.show()
                    }
                },
                onViewMoreClick = onViewMoreClick,
                onHeadlineItemClick = onHeadlineItemClick,
                onFavouriteHeadlineChange = { article ->
                    viewModel.onHomeUiEvents(
                        HomeUiEvents.OnHeadLineFavouriteChange(
                            article
                        )
                    )
                },
            )
        }
    }
}

@Composable
private fun HeadlinesList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    headlineArticles: LazyPagingItems<Article>,
    showFailureBottomSheet: (error: String) -> Unit,
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (id: Int) -> Unit,
    onFavouriteHeadlineChange: (Article) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        item {
            HeaderTitle(
                title = "Hot News",
                icon = Icons.Default.LocalFireDepartment,
            )
            Spacer(modifier = Modifier.size(itemSpacing))
        }

        item {
            HeadlineItems(
                headlineArticles = headlineArticles,
                showFailureBottomSheet = showFailureBottomSheet,
                onViewMoreClick = onViewMoreClick,
                onHeadlineItemClick = onHeadlineItemClick,
                onFavouriteHeadlineChange = onFavouriteHeadlineChange
            )
        }
    }
}

@Composable
private fun HeadlineItems(
    headlineArticles: LazyPagingItems<Article>,
    showFailureBottomSheet: (error: String) -> Unit,
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (id: Int) -> Unit,
    onFavouriteHeadlineChange: (Article) -> Unit,
) {
    val articlesList = headlineArticles.itemSnapshotList.items

    PaginationLoadingItem(
        pagingState = headlineArticles.loadState.mediator?.refresh,
        onError = { e ->
            showFailureBottomSheet.invoke(e.message ?: "unknown error")
        },
        onLoading = {
            LoadingContent()
        },
        onSuccess = {
            HeadlineItem(
                articles = articlesList,
                articleCount = articlesList.size,
                onCardClick = {
                    onHeadlineItemClick.invoke(it.id)
                },
                onViewMoreClick = onViewMoreClick,
                onFavouriteChange = onFavouriteHeadlineChange,
            )
        }
    )
}


@PreviewLightDark
@Composable
private fun PrevHomeScreen() {
    val headlineArticles: Flow<PagingData<Article>> = flow { fakeArticles }

    NewsyTheme {
        Surface {
            HeadlinesList(
                headlineArticles = headlineArticles.collectAsLazyPagingItems(),
                onViewMoreClick = {},
                onHeadlineItemClick = {},
                onFavouriteHeadlineChange = {},
                showFailureBottomSheet = {}
            )
        }
    }
}