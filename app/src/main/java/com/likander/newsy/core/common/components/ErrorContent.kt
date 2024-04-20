package com.likander.newsy.core.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.likander.newsy.R
import com.likander.newsy.core.theme.DEFAULT_PADDING
import com.likander.newsy.core.theme.NewsyTheme

@Composable
fun ErrorContent(
    modifier: Modifier=Modifier,
    title: String? = null,
    message: String?,
    retryFunc: (() -> Unit)? = null,
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(DEFAULT_PADDING),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title ?: stringResource(R.string.failure),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = message ?: stringResource(R.string.unknown_error),
                textAlign = TextAlign.Center
            )
            retryFunc?.let {
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { retryFunc() }
                ) {
                    Text(
                        text = stringResource(R.string.retry)
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun ErrorScreenPreview() {
    NewsyTheme() {
        ErrorContent(
            message = "Error",
        )
    }
}