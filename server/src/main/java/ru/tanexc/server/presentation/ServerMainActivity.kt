package ru.tanexc.server.presentation

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.tanexc.server.core.di.serverModule
import ru.tanexc.server.service.Actions
import ru.tanexc.server.service.ServerService
import ru.tanexc.server.presentation.ui.theme.ServerClientAppTheme

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

        startKoin {
            androidContext(applicationContext)
            modules(serverModule)
        }

        enableEdgeToEdge()
        setContent {
            ServerClientAppTheme {
                Scaffold(
                    bottomBar = BottomAppBar {
                        NavigationBarItem(

                        )
                        NavigationBarItem(

                        )
                    }
                ) {
                    Surface(modifier = Modifier.fillMaxSize().padding(it)) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(32.dp, 0.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            val port = remember { mutableStateOf("8081") }
                            OutlinedTextField(
                                port.value,
                                onValueChange = {
                                    if (it.toIntOrNull() != null || it == "") {
                                        port.value = it
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Port") }
                            )
                            Spacer(Modifier.size(8.dp))
                            Row {
                                Button(onClick = {
                                    Intent(
                                        applicationContext,
                                        ServerService::class.java
                                    ).also { service ->
                                        service.action = Actions.START.name
                                        service.putExtra(
                                            "port",
                                            port.value.let { if (it == "") 8081 else it.toInt() })
                                        startService(service)
                                    }
                                }) {
                                    Text("Start server")
                                }

                                Spacer(Modifier.size(8.dp))

                                OutlinedButton(onClick = {
                                    Intent(
                                        applicationContext,
                                        ServerService::class.java
                                    ).also { service ->
                                        service.action = Actions.STOP.name
                                        startService(service)
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
    }
}
