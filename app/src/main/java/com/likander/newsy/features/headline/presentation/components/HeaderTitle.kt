package com.likander.newsy.features.headline.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.likander.newsy.core.theme.NewsyTheme
import com.likander.newsy.core.theme.defaultPadding

@Composable
fun HeaderTitle(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = defaultPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TitleText(text = title)
        Spacer(modifier = Modifier.size(defaultPadding))
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.error,
        )
    }
}

@Composable
private fun TitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
    )
}


@PreviewLightDark
@Composable
private fun PreviewHeaderTitle() {
    NewsyTheme {
        Surface {
            HeaderTitle(
                title = "Title",
                icon = Icons.Default.Home,
            )
        }
    }
}