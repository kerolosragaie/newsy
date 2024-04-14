package com.likander.newsy.core.common.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.likander.newsy.R
import com.likander.newsy.core.theme.NewsyTheme

@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    url: String,
    placeholder: Painter = painterResource(R.drawable.news_place_holder),
    contentScale: ContentScale = ContentScale.Crop,
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(url)
        .crossfade(true)
        .build()

    AsyncImage(
        modifier = modifier,
        model = imageRequest,
        contentDescription = NETWORK_IMAGE_ID,
        placeholder = placeholder,
        error = placeholder,
        contentScale = contentScale
    )
}

@PreviewLightDark
@Composable
private fun PreviewNetworkImage() {
    NewsyTheme {
        NetworkImage(url = "")
    }
}

internal const val NETWORK_IMAGE_ID = "network_image_id"