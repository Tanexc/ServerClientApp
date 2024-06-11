package ru.tanexc.server.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tanexc.server.core.DataState
import ru.tanexc.server.data.SwipeLogDao
import ru.tanexc.server.domain.model.SwipeLog

class GetSwipeLogsUseCase(
    private val swipeLogDao: SwipeLogDao
) {
    operator fun invoke(): Flow<DataState<List<SwipeLog>>> = flow {
        emit(DataState.Loading)
        try {
            val data = swipeLogDao.getAll()
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error)
        }
    }
}