package ru.tanexc.client.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import ru.tanexc.client.data.local.Keys

class SetStopOnResumeUseCase: KoinComponent {
    private val dataStore: DataStore<Preferences> by inject(named("client"))

    suspend operator fun invoke(stop: Boolean) {
        dataStore.edit {
            it[Keys.STOP_ON_RESUME] = stop
        }
    }
}