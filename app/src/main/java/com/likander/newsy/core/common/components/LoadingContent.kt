package com.likander.newsy.core.common.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.likander.newsy.core.theme.NewsyTheme

@Composable
fun LoadingContent(
    modifier: Modifier = Modifier,
    isOverlay: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(if (isOverlay) Color(0xCC000000) else Color.Transparent)
            .testTag(LOADING_CONTAINER_ID)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag(LOADING_INDICATOR_ID),
        )
    }
}

@Preview("Light", showSystemUi = true, showBackground = true)
@Preview(
    "Dark",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewLoadingContent() {
    NewsyTheme {
        LoadingContent(isOverlay = true)
    }
}

internal const val LOADING_CONTAINER_ID = "overlay_loading_container"
internal const val LOADING_INDICATOR_ID = "overlay_loading_indicator"