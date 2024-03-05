package com.likander.newsy.features.headline.presentation.components

import androidx.compose.runtime.Composable
import androidx.paging.LoadState

@Composable
fun PaginationLoadingItem(
    pagingState: LoadState?,
    onError: (e: Throwable) -> Unit,
    onLoading: @Composable () -> Unit,
    onSuccess: @Composable () -> Unit,
) {
    when (pagingState) {
        is LoadState.Error -> {
            val error = pagingState.error
            onError(error)
        }

        is LoadState.Loading -> {
            onLoading()
        }

        else -> {
            onSuccess()
        }
    }
}