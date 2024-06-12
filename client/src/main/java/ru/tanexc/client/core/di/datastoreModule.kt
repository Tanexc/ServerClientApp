package ru.tanexc.client.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val datastoreModule = module {
    single<DataStore<Preferences>> { PreferenceDataStoreFactory.create(
        produceFile = { androidContext().preferencesDataStoreFile("client_store") }
    ) }
}