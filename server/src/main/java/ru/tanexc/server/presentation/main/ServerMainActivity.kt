package ru.tanexc.server.presentation.main

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.tanexc.server.core.di.serverModule
import ru.tanexc.server.util.service.Actions
import ru.tanexc.server.util.service.ServerService

class ServerMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}
