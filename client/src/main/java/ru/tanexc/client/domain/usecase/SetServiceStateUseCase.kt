package ru.tanexc.client.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import ru.tanexc.client.core.util.ServiceState
import ru.tanexc.client.data.local.Keys

class SetServiceStateUseCase(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun invoke(state: ServiceState) {
        dataStore.edit {
            it[Keys.SERVICE_STATE] = state.name
        }
    }
}