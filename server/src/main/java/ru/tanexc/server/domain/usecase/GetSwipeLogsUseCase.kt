package ru.tanexc.server.domain.usecase

import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tanexc.server.core.DataState
import ru.tanexc.server.data.SwipeLogDao
import ru.tanexc.server.domain.model.SwipeLog

class GetSwipeLogsUseCase(
    private val swipeLogDao: SwipeLogDao
) {
    operator fun invoke(limit: Int = 20, offset: Int): Flow<DataState<List<SwipeLog>>> = flow {
        emit(DataState.Loading)
        val data = swipeLogDao.getAll(offset = offset, limit = limit)
        emit(DataState.Success(data))
    }
}