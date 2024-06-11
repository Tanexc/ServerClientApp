package ru.tanexc.server

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.tanexc.server.core.di.serverModule
import ru.tanexc.server.service.ServerService
import ru.tanexc.server.ui.theme.ServerClientAppTheme

class ServerMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(applicationContext)
            modules(serverModule)
        }

        enableEdgeToEdge()
        setContent {
            ServerClientAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            Intent(applicationContext, ServerService::class.java).also { service ->
                                startService(service)
                            }
                        }) {
                            Text("Start server")
                        }

                        OutlinedButton(onClick = {
                            Intent(applicationContext, ServerService::class.java).also { service ->
                                stopService(service)
                            }
                        }) {
                            Text("Stop server")
                        }
                    }
                }
            }
        }
    }
}
