package ru.tanexc.client.presentation.main

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.tanexc.client.core.di.datastoreModule
import ru.tanexc.client.core.di.networkModule
import ru.tanexc.client.core.di.usecaseModule

class ClientApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                datastoreModule,
                networkModule,
                usecaseModule
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "client_channel",
                "Client notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}