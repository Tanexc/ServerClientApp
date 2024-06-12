package ru.tanexc.client.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import ru.tanexc.client.core.util.getDeviceName
import java.util.UUID

suspend fun DataStore<Preferences>.clientId(): String {
    var data = this.data.first()[Keys.CLIENT_ID]
    if (data == null) {
        data = getDeviceName() + UUID.randomUUID()
        this.edit { pref ->
            pref[Keys.CLIENT_ID] = data
        }
    }
    return data
}