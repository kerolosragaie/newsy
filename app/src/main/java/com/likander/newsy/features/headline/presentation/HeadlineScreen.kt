package com.likander.newsy.features.headline.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.likander.newsy.core.common.components.PaginationLoadingItem
import com.likander.newsy.core.theme.ITEM_SPACING
import com.likander.newsy.features.discover.presentation.components.ArticleItem
import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.presentation.components.HeaderTitle
import com.likander.newsy.features.headline.presentation.viewmodel.HeadlineViewModel

@Composable
fun HeadlineScreen(
    headlineViewModel: HeadlineViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
) {
    val headlineState = headlineViewModel.headlineState
    val headlineArticles = headlineState.headlineArticles.collectAsLazyPagingItems()

    Surface {
        ScreenContent(
            articles = headlineArticles,
            onItemClick = onItemClick,
            onFavouriteChange = headlineViewModel::onFavouriteChange
        )
    }
}

@Composable
private fun ScreenContent(
    articles: LazyPagingItems<Article>,
    onItemClick: (Int) -> Unit,
    onFavouriteChange: (Article) -> Unit,
) {
    LazyColumn {
        item {
            HeaderTitle(
                title = "Hot news",
                icon = Icons.Default.LocalFireDepartment
            )
            Spacer(Modifier.size(ITEM_SPACING))
        }
        items(
            count = articles.itemCount,
            key = { index -> articles[index]?.id ?: index }
        ) { value ->
            articles[value]?.let {
                ArticleItem(
                    article = it,
                    onClick = { clickedItem ->
                        onItemClick(clickedItem.id)
                    },
                    onFavouriteChange = { article ->
                        onFavouriteChange(article)
                    }
                )
            }
        }
        item {
            val context = LocalContext.current
            PaginationLoadingItem(
                pagingState = articles.loadState.mediator?.append,
                onError = { e ->
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                },
                onLoading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .wrapContentWidth(
                                align = Alignment.CenterHorizontally
                            )
                    )
                },
                onSuccess = {}
            )
        }
    }
}