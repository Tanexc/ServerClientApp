package ru.tanexc.server.domain.usecase.servicestate

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import ru.tanexc.server.data.datastore.Keys

class GetPortUseCase: KoinComponent {
    private val dataStore: DataStore<Preferences> by inject(named("server"))

    suspend operator fun invoke(): String {
        return dataStore.data.first()[Keys.PORT]?: ""
    }
}