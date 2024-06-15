package ru.tanexc.client.core.util

sealed class DataState<out T> {
    data object Loading : DataState<Nothing>()

    data object Error : DataState<Nothing>()

    class Success<out T>(
        val data: T,
    ) : DataState<T>()
}
