package ru.tanexc.server.domain.usecase.servicestate

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import ru.tanexc.server.data.datastore.Keys

class SetPortUseCase : KoinComponent {
    private val dataStore: DataStore<Preferences> by inject(named("server"))

    suspend operator fun invoke(port: Int) {
        dataStore.edit {
            it[Keys.PORT] = port.toString()
        }
    }
}
