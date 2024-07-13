package com.likander.newsy.core.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.likander.newsy.core.theme.NewsyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIconContent: @Composable () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        navigationIcon = navigationIconContent,
        scrollBehavior = scrollBehavior,
        title = {
            Row {
                Text(
                    text = "Published in: $title",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun PrevTopAppBar() {
    NewsyTheme {
        TopAppBar(title = "www.likander.com") {}
    }
}