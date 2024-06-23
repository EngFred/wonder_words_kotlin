package com.kotlin.wonderwords.core.network

sealed class DataState<out T> {
    data class Success<Q>(val data: Q) : DataState<Q>()
    data class Error(val error: String) : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
}