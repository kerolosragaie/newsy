package com.likander.newsy.core.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsIgnoringVisibility
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.window.SecureFlagPolicy
import com.likander.newsy.R
import com.likander.newsy.core.theme.NewsyTheme

private sealed class BottomSheetType {
    data object Confirm : BottomSheetType()
    data object Loading : BottomSheetType()
    data object Success : BottomSheetType()
    data object Failure : BottomSheetType()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    sheetContent: @Composable ColumnScope.() -> Unit,
    onDismissRequest: (() -> Unit)? = null,
    dragHandle: @Composable (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Box {
        content()

        AnimatedVisibility(sheetState.isVisible) {
            ModalBottomSheet(
                modifier = modifier,
                sheetState = sheetState,
                onDismissRequest = { onDismissRequest?.invoke() },
                properties = ModalBottomSheetProperties(
                    shouldDismissOnBackPress = false,
                    isFocusable = true,
                    securePolicy = SecureFlagPolicy.SecureOn,
                ),
                dragHandle = dragHandle,
                content = {
                    sheetContent()
                    Spacer(
                        Modifier.windowInsetsBottomHeight(
                            WindowInsets.navigationBarsIgnoringVisibility
                        )
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
private fun PrevBottomSheet(
    @PreviewParameter(PreviewParameterBottomSheetType::class) sheetType: BottomSheetType
) {
    val sheetState = rememberModalBottomSheetState(confirmValueChange = { false })

    LaunchedEffect(Unit) { sheetState.expand() }

    NewsyTheme {
        BottomSheet(
            sheetState = sheetState,
            sheetContent = {
                when (sheetType) {
                    BottomSheetType.Confirm ->
                        ConfirmBottomSheetContent(
                            description = stringResource(R.string.confirm),
                            onOkClick = {},
                            onCancelClick = {}
                        )

                    BottomSheetType.Failure ->
                        FailureBottomSheetContent(
                            title = stringResource(R.string.failure),
                            description = stringResource(R.string.something_went_wrong),
                            onOkClick = {}
                        )

                    BottomSheetType.Success ->
                        SuccessBottomSheetContent(
                            description = stringResource(R.string.success),
                            onOkClick = {}
                        )

                    BottomSheetType.Loading -> LoadingContent()
                }
            },
            content = {}
        )
    }
}

private class PreviewParameterBottomSheetType : PreviewParameterProvider<BottomSheetType> {
    override val values: Sequence<BottomSheetType>
        get() = sequenceOf(
            BottomSheetType.Confirm,
            BottomSheetType.Loading,
            BottomSheetType.Success,
            BottomSheetType.Failure
        )
}