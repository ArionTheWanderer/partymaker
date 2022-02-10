package com.example.partymaker.domain.common

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val error: String) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}
