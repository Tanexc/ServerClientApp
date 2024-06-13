package ru.tanexc.client.presentation.screen

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel

class LaunchViewModel(
    private val dataStore: DataStore<Preferences>
): ViewModel() {
}