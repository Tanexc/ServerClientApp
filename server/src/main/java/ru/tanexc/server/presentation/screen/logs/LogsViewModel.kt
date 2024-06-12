package ru.tanexc.server.presentation.screen.logs

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.tanexc.server.core.util.DataState
import ru.tanexc.server.core.util.UIState
import ru.tanexc.server.domain.model.SwipeLog
import ru.tanexc.server.domain.usecase.DeleteSwipeLogsByIdUseCase
import ru.tanexc.server.domain.usecase.DeleteSwipeLogsUseCase
import ru.tanexc.server.domain.usecase.GetSwipeLogsUseCase

class LogsViewModel(
    private val getLogsUseCase: GetSwipeLogsUseCase,
    private val deleteLogsUseCase: DeleteSwipeLogsUseCase,
    private val deleteLogsByIdUseCase: DeleteSwipeLogsByIdUseCase
) : ViewModel() {
    private val _data: MutableState<List<SwipeLog>> = mutableStateOf(emptyList())
    val data by _data

    private var page: Int = 0

    val uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loding)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(300)
            getLogsUseCase(offset = 0, limit = 30).collect { state ->
                when (state) {
                    is DataState.Loading -> uiState.value = UIState.Loding
                    is DataState.Error -> uiState.value = UIState.Error
                    is DataState.Success -> {
                        _data.value = state.data
                        uiState.value = UIState.Success
                    }
                }
            }
        }

    }

    fun getNextPage() {
        page++
        viewModelScope.launch(Dispatchers.IO) {
            getLogsUseCase(offset = 30 * page, limit = 30).collect { state ->
                when (state) {
                    is DataState.Loading -> uiState.value = UIState.Loding
                    is DataState.Error -> uiState.value = UIState.Error
                    is DataState.Success -> {
                        _data.value += state.data
                        uiState.value = UIState.Success
                    }
                }
            }
        }
    }

    fun deleteAll() {
        page = 0
        viewModelScope.launch(Dispatchers.IO) {
            _data.value = emptyList()
            deleteLogsUseCase().collect { state ->
                when (state) {
                    is DataState.Loading -> uiState.value = UIState.Loding
                    is DataState.Error -> uiState.value = UIState.Error
                    is DataState.Success -> uiState.value = UIState.Success
                }
            }
        }
    }

    fun deleteById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _data.value = _data.value.filter { it.id != id }
            deleteLogsByIdUseCase(id).collect { state ->
                when (state) {
                    is DataState.Loading -> uiState.value = UIState.Loding
                    is DataState.Error -> uiState.value = UIState.Error
                    is DataState.Success -> uiState.value = UIState.Success
                }
            }
        }
    }

}