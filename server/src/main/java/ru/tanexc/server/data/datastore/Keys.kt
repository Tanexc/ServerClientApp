package ru.tanexc.server.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object Keys {
    val SERVICE_STATE = stringPreferencesKey("service_state")
    val PORT = stringPreferencesKey("port")
}