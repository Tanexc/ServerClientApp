package ru.tanexc.client.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import ru.tanexc.client.core.util.ServiceState
import ru.tanexc.client.data.local.Keys

class SetServiceStateUseCase : KoinComponent {
    private val dataStore: DataStore<Preferences> by inject(named("service"))

    suspend operator fun invoke(state: ServiceState) {
        dataStore.edit {
            it[Keys.SERVICE_STATE] = state.name
        }
    }
}
