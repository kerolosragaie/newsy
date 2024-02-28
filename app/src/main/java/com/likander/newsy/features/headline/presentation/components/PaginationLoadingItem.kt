package com.likander.newsy.features.home.components

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.PagingState

@Composable
fun PaginationLoadingItem(
    pagingState: LoadState?,
    onError: (e: Throwable) -> Unit,
    onLoading: @Composable () -> Unit,
) {
    when (pagingState) {
        is LoadState.Error -> {
            val error = pagingState.error
            onError(error)
        }

        is LoadState.Loading -> {
            onLoading()
        }

        else -> {}
    }
}