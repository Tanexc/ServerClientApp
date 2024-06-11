package ru.tanexc.server.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tanexc.server.core.DataState
import ru.tanexc.server.data.SwipeLogDao
import ru.tanexc.server.domain.model.SwipeLog

class DeleteSwipeLogsUseCase(
    private val swipeLogDao: SwipeLogDao
) {
    operator fun invoke(): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            swipeLogDao.deleteAll()
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error)
        }
    }
}