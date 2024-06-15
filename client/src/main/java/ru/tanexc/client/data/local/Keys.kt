package ru.tanexc.client.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Keys {
    val CLIENT_ID = stringPreferencesKey("device_id")
    val SERVICE_STATE = stringPreferencesKey("service_state")
    val PORT = stringPreferencesKey("port")
    val HOST = stringPreferencesKey("host")
    val STOP_ON_RESUME = booleanPreferencesKey("stop_on_resume")
}