package com.likander.newsy.core.common.components

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.likander.newsy.R
import com.likander.newsy.core.theme.NewsyTheme

@Composable
fun SuccessBottomSheetContent(
    @DrawableRes icon: Int = R.drawable.ic_check,
    title: String = stringResource(R.string.success),
    description: String,
    button: String = stringResource(R.string.done),
    onOkClick: () -> Unit
) {
    Surface(contentColor = MaterialTheme.colorScheme.onSurface) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .testTag(SUCCESS_BOTTOM_SHEET_ICON_ID)
                    .size(80.dp),
                painter = painterResource(icon),
                colorFilter = ColorFilter.tint(Color.Green.copy(0.7f)),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.testTag(SUCCESS_BOTTOM_SHEET_TITLE_ID),
                text = title,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.testTag(SUCCESS_BOTTOM_SHEET_DESC_ID),
                text = description,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(SUCCESS_BOTTOM_SHEET_OK_BTN_ID),
                onClick = onOkClick
            ) {
                Text(text = button)
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewSuccessBottomSheetContent() {
    NewsyTheme {
        SuccessBottomSheetContent(
            description = stringResource(R.string.success),
            onOkClick = {}
        )
    }
}

internal const val SUCCESS_BOTTOM_SHEET_ICON_ID = "success_sheet_icon"
internal const val SUCCESS_BOTTOM_SHEET_TITLE_ID = "success_sheet_title"
internal const val SUCCESS_BOTTOM_SHEET_DESC_ID = "success_sheet_description"
internal const val SUCCESS_BOTTOM_SHEET_OK_BTN_ID = "success_sheet_ok_button"