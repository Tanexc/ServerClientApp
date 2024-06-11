package ru.tanexc.server.core

sealed class DataState<out T> {
    data object Loading: DataState<Nothing>()

    data object Error: DataState<Nothing>()

    class Success<T>(val data: T): DataState<T>()
}