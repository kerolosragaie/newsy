package com.likander.newsy.features.detail.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likander.newsy.core.common.components.TopAppBar
import com.likander.newsy.core.utils.UiState
import com.likander.newsy.features.detail.domain.model.DetailArticle
import com.likander.newsy.features.detail.presentation.components.ArticleContent
import com.likander.newsy.features.detail.presentation.viewmodel.DetailViewModel

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.detailUiState.collectAsStateWithLifecycle()
    val selectedDetailArticle
            by uiState.selectedDetailArticle.collectAsStateWithLifecycle(initialValue = UiState.Loading)

    when (selectedDetailArticle) {
        is UiState.Loading -> CircularProgressIndicator()

        is UiState.Error ->
            Toast.makeText(
                LocalContext.current,
                (selectedDetailArticle as UiState.Error<DetailArticle>).error?.message,
                Toast.LENGTH_SHORT
            ).show()

        is UiState.Success -> {
            ScreenContent(
                article = (selectedDetailArticle as UiState.Success<DetailArticle>).body,
                onBackPressed = onBackPressed
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    article: DetailArticle,
    lazyListState: LazyListState = rememberLazyListState(),
    onBackPressed: () -> Unit,
) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopAppBar(
                title = article.articleMetaData.source,
                scrollBehavior = scrollBehaviour,
                navigationIconContent = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Navigate up",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
            )
        }
    ) {
        ArticleContent(
            modifier = Modifier
                .nestedScroll(scrollBehaviour.nestedScrollConnection)
                .padding(it),
            article = article,
            state = lazyListState
        )
    }
}