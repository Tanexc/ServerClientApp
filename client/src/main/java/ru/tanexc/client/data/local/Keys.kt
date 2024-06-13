package ru.tanexc.client.data.local

import androidx.datastore.preferences.core.stringPreferencesKey

object Keys {
    val CLIENT_ID = stringPreferencesKey("device_id")
    val SERVICE_STATE = stringPreferencesKey("service_state")
}