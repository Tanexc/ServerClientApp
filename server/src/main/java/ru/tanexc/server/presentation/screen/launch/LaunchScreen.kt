package ru.tanexc.server.presentation.screen.launch

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ru.tanexc.server.service.Actions
import ru.tanexc.server.service.ServerService

@Composable
fun LaunchScreen(modifier: Modifier) {
    val context = LocalContext.current

    Surface(modifier = modifier.fillMaxSize()) {
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
                        context,
                        ServerService::class.java
                    ).also { service ->
                        service.action = Actions.START.name
                        service.putExtra(
                            "port",
                            port.value.let { if (it == "") 8081 else it.toInt() })
                        context.startService(service)
                    }
                }) {
                    Text("Start server")
                }

                Spacer(Modifier.size(8.dp))

                OutlinedButton(onClick = {
                    Intent(
                        context,
                        ServerService::class.java
                    ).also { service ->
                        service.action = Actions.STOP.name
                        context.startService(service)
                    }
                }) {
                    Text("Stop server")
                }
            }

        }
    }
}