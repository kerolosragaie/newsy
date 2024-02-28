package com.likander.newsy.features.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.likander.newsy.core.theme.NewsyTheme
import com.likander.newsy.core.theme.itemSpacing
import com.likander.newsy.core.utils.ArticleCategory
import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.home.components.HeaderTitle
import com.likander.newsy.features.home.components.HeadlineItem
import com.likander.newsy.features.home.components.HomeTopAppBar
import com.likander.newsy.features.home.components.PaginationLoadingItem
import com.likander.newsy.features.headline.presentation.viewmodel.HomeUiEvents
import com.likander.newsy.features.headline.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
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
    val homeState = viewModel.homeUiState
    val headlineArticles = homeState.headlineArticles.collectAsLazyPagingItems()
    val categories = ArticleCategory.values()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(snackbarHost = {
        SnackbarHost(snackbarHostState)
    }, topBar = {
        HomeTopAppBar(
            openDrawer = openDrawer,
            onSearch = onSearch,
        )
    }) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
        ) {
            headlineItems(
                headlineArticles = headlineArticles,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
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


private fun LazyListScope.headlineItems(
    headlineArticles: LazyPagingItems<Article>,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (id: Int) -> Unit,
    onFavouriteHeadlineChange: (Article) -> Unit,
) {
    val articlesList = headlineArticles.itemSnapshotList.items

    item {
        HeaderTitle(
            title = "Hot News",
            icon = Icons.Default.LocalFireDepartment,
        )
        Spacer(modifier = Modifier.size(itemSpacing))
    }

    item {
        PaginationLoadingItem(
            pagingState = headlineArticles.loadState.mediator?.refresh,
            onError = { e ->
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = e.message ?: "unknown error",

                        )
                }
            },
            onLoading = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                )
            },
        )
    }

    item {
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
}


@PreviewLightDark
@Composable
fun PrevHomeScreen() {
    NewsyTheme {
        HomeScreen(
            onViewMoreClick = {},
            openDrawer = {},
            onSearch = {},
            onHeadlineItemClick = {},
        )
    }
}