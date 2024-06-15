package ru.tanexc.client.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import ru.tanexc.client.data.local.Keys

class GetHostUseCase: KoinComponent {
    private val dataStore: DataStore<Preferences> by inject(named("client"))

    suspend operator fun invoke(): String {
        return dataStore.data.first()[Keys.HOST]?: ""
    }
}