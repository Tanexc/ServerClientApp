package ru.tanexc.server.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tanexc.server.core.DataState
import ru.tanexc.server.data.SwipeLogDao

class DeleteSwipeLogsByIdUseCase(
    private val swipeLogDao: SwipeLogDao
) {
    operator fun invoke(id: Long): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            swipeLogDao.deleteById(id)
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error)
        }
    }
}