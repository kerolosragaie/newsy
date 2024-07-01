package com.likander.newsy.core.utils

sealed class DataWrapper<out T> {
    data class Success<T>(val body: T) : DataWrapper<T>()
    data class Error(val code: Int, val message: String? = null) : DataWrapper<Nothing>()
}