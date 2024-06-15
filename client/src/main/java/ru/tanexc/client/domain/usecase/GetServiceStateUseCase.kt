package ru.tanexc.client.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import ru.tanexc.client.core.util.ServiceState
import ru.tanexc.client.data.local.Keys

class GetServiceStateUseCase : KoinComponent {
    private val dataStore: DataStore<Preferences> by inject(named("service"))

    operator fun invoke(): Flow<ServiceState> =
        flow {
            dataStore.data.collect {
                emit(ServiceState.valueOf(it[Keys.SERVICE_STATE] ?: ServiceState.Stopped.name))
            }
        }
}
