package ru.tanexc.client.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val datastoreModule =
    module {
        single<DataStore<Preferences>>(qualifier = named("client")) {
            PreferenceDataStoreFactory.create(
                produceFile = { androidContext().preferencesDataStoreFile("client_store") },
            )
        }

        single<DataStore<Preferences>>(qualifier = named("service")) {
            PreferenceDataStoreFactory.create(
                produceFile = { androidContext().preferencesDataStoreFile("service_store") },
            )
        }
    }
