package com.likander.newsy.core.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.likander.newsy.R
import com.likander.newsy.core.theme.NewsyTheme

@Composable
fun ConfirmBottomSheetContent(
    @StringRes title: Int = R.string.confirm,
    @StringRes description: Int,
    @StringRes button: Int = R.string.done,
    onOkClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Surface(contentColor = MaterialTheme.colorScheme.onSurface) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(title),
                    modifier = Modifier
                        .weight(1.0f)
                        .testTag(CONFIRM_BOTTOM_SHEET_TITLE_ID),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = onCancelClick)
                        .testTag(CONFIRMATION_BOTTOM_SHEET_HEADER_ICON_ID)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.testTag(CONFIRM_BOTTOM_SHEET_DESC_ID).fillMaxWidth(),
                text = stringResource(description),
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(CONFIRM_BOTTOM_SHEET_OK_BTN_ID),
                onClick = onOkClick
            ) {
                Text(text = stringResource(button))
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(CONFIRMATION_BOTTOM_SHEET_CANCEL_BTN_ID),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                onClick = onCancelClick,
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewConfirmBottomSheetContent() {
    NewsyTheme {
        ConfirmBottomSheetContent(
            description = R.string.confirm,
            onOkClick = {},
            onCancelClick = {}
        )
    }
}

internal const val CONFIRMATION_BOTTOM_SHEET_HEADER_ICON_ID = "CONFIRMATION_BOTTOM_SHEET_HEADER_ICON_ID"
internal const val CONFIRM_BOTTOM_SHEET_TITLE_ID = "confirm_sheet_title"
internal const val CONFIRM_BOTTOM_SHEET_DESC_ID = "confirm_sheet_description"
internal const val CONFIRM_BOTTOM_SHEET_OK_BTN_ID = "confirm_sheet_ok_button"
internal const val CONFIRMATION_BOTTOM_SHEET_CANCEL_BTN_ID = "confirm_sheet_cancel_button"