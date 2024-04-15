package com.likander.newsy.core.utils

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val body: T) : UiState<T>()
    data class Error(val error: String) : UiState<Nothing>()
}