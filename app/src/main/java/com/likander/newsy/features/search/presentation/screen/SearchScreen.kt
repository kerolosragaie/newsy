package com.likander.newsy.features.search.presentation.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.likander.newsy.R
import com.likander.newsy.core.common.components.NetworkImage
import com.likander.newsy.core.common.components.PaginationLoadingItem
import com.likander.newsy.core.theme.DEFAULT_PADDING
import com.likander.newsy.core.theme.ITEM_PADDING
import com.likander.newsy.core.theme.NewsyTheme
import com.likander.newsy.core.utils.Utils
import com.likander.newsy.features.search.domain.model.SearchArticle
import com.likander.newsy.features.search.presentation.model.SearchUiEvents
import com.likander.newsy.features.search.presentation.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onSearchItemClick: (SearchArticle) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ScreenContent(
        searchArticles = state.searchArticles.collectAsLazyPagingItems(),
        query = state.query.orEmpty(),
        searchHistory = state.searchHistory,
        onQueryChange = { viewModel.onUiEvent(SearchUiEvents.OnSearchChange(it)) },
        onSubmitSearch = { viewModel.onUiEvent(SearchUiEvents.OnSubmitSearch) },
        onSearchHistoryChange = { viewModel.onUiEvent(SearchUiEvents.SaveSearchHistory(it)) },
        onFavouriteChange = { viewModel.onUiEvent(SearchUiEvents.OnFavouriteChange(it)) },
        onClearSearchHistory = { viewModel.onUiEvent(SearchUiEvents.ClearSearchHistory) },
        onSearchItemClick = onSearchItemClick,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    searchArticles: LazyPagingItems<SearchArticle>,
    query: String,
    searchHistory: List<String>,
    onQueryChange: (String) -> Unit,
    onSubmitSearch: () -> Unit,
    onSearchHistoryChange: (String) -> Unit,
    onSearchItemClick: (SearchArticle) -> Unit,
    onFavouriteChange: (SearchArticle) -> Unit,
    onClearSearchHistory: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val context = LocalContext.current
    var active by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.explore))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "navigate back",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchBar(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = {
                    onSubmitSearch()
                    onSearchHistoryChange(it)
                    active = false
                },
                onActiveChange = {
                    active = it
                },
                active = active,
                placeholder = { Text(stringResource(R.string.search_articles_title)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, null) },
                modifier = Modifier.padding(horizontal = DEFAULT_PADDING, vertical = 24.dp)
            ) {
                searchHistory.forEach {
                    if (it.isNotEmpty()) {
                        Row(modifier = Modifier.padding(14.dp)) {
                            Icon(imageVector = Icons.Default.History, null)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(it)
                        }
                    }
                }
                HorizontalDivider()
                Text(
                    modifier = Modifier
                        .padding(14.dp)
                        .fillMaxWidth()
                        .clickable {
                            onClearSearchHistory()
                        },
                    text = stringResource(R.string.clear_all_history),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            HorizontalDivider()
            LazyColumn {
                item {
                    PaginationLoadingItem(
                        pagingState = searchArticles.loadState.mediator?.refresh,
                        onLoading = {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .wrapContentWidth(
                                        align = Alignment.CenterHorizontally
                                    )
                            )
                        },
                        onError = {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        },
                        onSuccess = {}
                    )
                }
                items(count = searchArticles.itemCount) { index ->
                    searchArticles[index]?.let { article ->
                        ArticleItem(
                            article = article,
                            onClick = onSearchItemClick,
                            onFavouriteChange = onFavouriteChange
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArticleItem(
    article: SearchArticle,
    onClick: (SearchArticle) -> Unit,
    onFavouriteChange: (SearchArticle) -> Unit,
) {
    Card(
        modifier = Modifier
            .height(250.dp)
            .padding(ITEM_PADDING)
            .clickable { onClick(article) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NetworkImage(
                modifier = Modifier.weight(2f),
                url = article.urlToImage
            )
            Spacer(modifier = Modifier.weight(0.01f))
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxHeight()
                    .weight(2f),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = article.description,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = article.source,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = Utils.formatPublishedAtDate(article.publishedAt),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    IconButton(
                        onClick = { onFavouriteChange(article) }
                    ) {
                        AnimatedContent(targetState = article.favourite, label = "") {
                            when (it) {
                                true ->
                                    Icon(
                                        imageVector = Icons.Default.BookmarkAdded,
                                        tint = MaterialTheme.colorScheme.primary,
                                        contentDescription = "favourite Icon Btn"
                                    )

                                false ->
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        tint = LocalContentColor.current,
                                        contentDescription = "favourite Icon Btn"
                                    )
                            }
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ArticleItemPreview() {
    NewsyTheme {
        ArticleItem(
            article = SearchArticle(
                id = 1,
                author = "John Doe",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                description = "Sample article description. Sample article description. Sample article description. Sample article description. Sample article description.",
                publishedAt = "2024-02-27T12:34:56Z",
                source = "Fake News Network",
                title = "Sample Article Title, Fake News Network Title",
                url = "https://oilprice.com/Energy/Energy-General/Supply-Chain-Woes-Could-Derail-Bidens-Electric-Vehicle-Agenda.html",
                urlToImage = "https://d32r1sh890xpii.cloudfront.net/article/718x300/2024-02-27_zharv8scwu.jpg",
                favourite = true,
                category = "Technology",
                page = 1
            ),
            onClick = {},
            onFavouriteChange = {}
        )
    }
}