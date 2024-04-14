package com.likander.newsy.core.common.components

import androidx.compose.runtime.Composable
import androidx.paging.LoadState

@Composable
fun PaginationLoadingItem(
    pagingState: LoadState?,
    onError: @Composable (e: Throwable) -> Unit,
    onLoading: @Composable () -> Unit,
    onSuccess: @Composable () -> Unit,
) {
    when (pagingState) {
        is LoadState.Error -> onError(pagingState.error)

        is LoadState.Loading -> onLoading()

        else -> onSuccess()
    }
}