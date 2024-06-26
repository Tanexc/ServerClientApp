package ru.tanexc.client.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import ru.tanexc.client.core.util.getDeviceName
import ru.tanexc.client.data.local.Keys
import java.util.UUID

class GetClientIdUseCase : KoinComponent {
    private val dataStore: DataStore<Preferences> by inject(named("client"))

    suspend operator fun invoke(): String {
        var data = dataStore.data.first()[Keys.CLIENT_ID]
        if (data == null) {
            data = getDeviceName() + UUID.randomUUID()
            dataStore.edit { pref ->
                pref[Keys.CLIENT_ID] = data
            }
        }
        return data
    }
}
