package ru.tanexc.server.presentation.screen.logs

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tanexc.server.core.util.DataState
import ru.tanexc.server.domain.model.SwipeLog
import ru.tanexc.server.domain.usecase.logs.DeleteSwipeLogsByIdUseCase
import ru.tanexc.server.domain.usecase.logs.DeleteSwipeLogsUseCase
import ru.tanexc.server.domain.usecase.logs.GetSwipeLogsUseCase

class LogsViewModel(
    private val getLogsUseCase: GetSwipeLogsUseCase,
    private val deleteLogsUseCase: DeleteSwipeLogsUseCase,
    private val deleteLogsByIdUseCase: DeleteSwipeLogsByIdUseCase,
) : ViewModel() {
    private val _data: MutableState<List<SwipeLog>> = mutableStateOf(emptyList())
    val data by _data

    private var page: Int = 0

    init {
        viewModelScope.launch(Dispatchers.Main) {
            getLogsUseCase(offset = 0, limit = 30).collect { state ->
                when (state) {
                    is DataState.Success -> {
                        _data.value = state.data
                    }

                    else -> {}
                }
            }
        }
    }

    fun getNextPage() {
        page++
        viewModelScope.launch(Dispatchers.IO) {
            getLogsUseCase(offset = 30 * page, limit = 30).collect { state ->
                when (state) {
                    is DataState.Success -> {
                        _data.value += state.data
                    }

                    else -> {}
                }
            }
        }
    }

    fun deleteAll() {
        page = 0
        viewModelScope.launch(Dispatchers.IO) {
            deleteLogsUseCase().collect { state ->
                when (state) {
                    is DataState.Success -> {
                        _data.value = emptyList()
                    }

                    else -> {}
                }
            }
        }
    }

    fun deleteById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteLogsByIdUseCase(id).collect { state ->
                when (state) {
                    is DataState.Success -> {
                        _data.value = _data.value.filter { it.id != id }
                    }

                    else -> {}
                }
            }
        }
    }
}
