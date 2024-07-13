package com.likander.newsy.core.utils

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val body: T) : UiState<T>()
    data class Error<T>(val error: Throwable?, val data: T? = null) : UiState<T>()
}