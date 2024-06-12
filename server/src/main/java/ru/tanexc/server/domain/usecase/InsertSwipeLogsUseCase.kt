package ru.tanexc.server.domain.usecase

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tanexc.server.core.util.DataState
import ru.tanexc.server.data.local.SwipeLogDao
import ru.tanexc.server.domain.model.SwipeLog

class InsertSwipeLogsUseCase(
    private val swipeLogDao: SwipeLogDao
) {
    operator fun invoke(log: SwipeLog): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            swipeLogDao.insert(log)
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            Log.i("cum", e.message?: "xo")
            emit(DataState.Error)
        }
    }
}