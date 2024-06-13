package ru.tanexc.server.domain.usecase.servicestate

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import ru.tanexc.server.core.util.ServiceState
import ru.tanexc.server.data.datastore.Keys

class SetServiceStateUseCase(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun invoke(state: ServiceState) {
        dataStore.edit {
            it[Keys.SERVICE_STATE] = state.name
        }
    }
}