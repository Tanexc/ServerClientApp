package ru.tanexc.server.domain.usecase.logs

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tanexc.server.core.util.DataState
import ru.tanexc.server.data.database.SwipeLogDao
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