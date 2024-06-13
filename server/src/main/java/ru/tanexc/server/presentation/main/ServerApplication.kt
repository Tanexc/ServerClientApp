package ru.tanexc.server.presentation.main

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.tanexc.server.core.di.databaseModule
import ru.tanexc.server.core.di.datastoreModule
import ru.tanexc.server.core.di.serverModule
import ru.tanexc.server.core.di.usecaseModule
import ru.tanexc.server.core.di.viewModelModule

class ServerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                databaseModule,
                datastoreModule,
                usecaseModule,
                viewModelModule,
                serverModule,
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "server_channel",
                "Server notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }


    }
}