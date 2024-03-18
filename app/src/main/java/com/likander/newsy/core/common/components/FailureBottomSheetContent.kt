package com.likander.newsy.core.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.likander.newsy.R
import com.likander.newsy.core.theme.NewsyTheme

@Composable
fun FailureBottomSheetContent(
    @StringRes title: Int,
    description: String,
    onOkClick: () -> Unit
) {
    Surface(contentColor = MaterialTheme.colorScheme.onSurface) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .testTag(FAILURE_BOTTOM_SHEET_ICON_ID)
                    .size(80.dp),
                painter = painterResource(R.drawable.ic_error),
                colorFilter = ColorFilter.tint(Color.Red.copy(alpha = 0.6f)),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.testTag(FAILURE_BOTTOM_SHEET_TITLE_ID)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.testTag(FAILURE_BOTTOM_SHEET_DESC_ID),
                text = description,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(FAILURE_BOTTOM_SHEET_OK_BTN_ID),
                onClick = onOkClick
            ) {
                Text(text = stringResource(R.string.ok))
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun FailureBottomSheetContentPreview() {
    NewsyTheme {
        FailureBottomSheetContent(
            title = R.string.failure,
            description = stringResource(id = R.string.something_went_wrong),
            onOkClick = { }
        )
    }
}

internal const val FAILURE_BOTTOM_SHEET_ICON_ID = "failure_sheet_icon"
internal const val FAILURE_BOTTOM_SHEET_TITLE_ID = "failure_sheet_title"
internal const val FAILURE_BOTTOM_SHEET_DESC_ID = "failure_sheet_description"
internal const val FAILURE_BOTTOM_SHEET_OK_BTN_ID = "failure_sheet_ok_button"