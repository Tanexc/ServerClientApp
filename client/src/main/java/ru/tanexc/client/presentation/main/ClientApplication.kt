package ru.tanexc.client.presentation.main

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.tanexc.client.core.di.datastoreModule
import ru.tanexc.client.core.di.networkModule

class ClientApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                datastoreModule,
                networkModule,
            )
        }
    }
}