package ru.tanexc.server.domain.usecase.servicestate

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tanexc.server.core.util.ServiceState
import ru.tanexc.server.data.datastore.Keys

class GetServiceStateUseCase(
    private val dataStore: DataStore<Preferences>
) {
    operator fun invoke(): Flow<ServiceState> = flow {
        dataStore.data.collect {
            emit(ServiceState.valueOf(it[Keys.SERVICE_STATE]?: ServiceState.Stopped.name))
        }
    }
}