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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.SecureFlagPolicy


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