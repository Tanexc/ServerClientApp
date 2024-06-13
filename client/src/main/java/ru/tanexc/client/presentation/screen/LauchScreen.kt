package ru.tanexc.client.presentation.screen

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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ru.tanexc.client.core.HOST_DEFAULT
import ru.tanexc.client.core.PORT_DEFAULT
import ru.tanexc.client.service.Actions
import ru.tanexc.client.service.ClientService

@Composable
fun LaunchScreen() {
    val context = LocalContext.current
    val port = remember { mutableStateOf("") }
    val host = remember { mutableStateOf("") }
    val running = remember { mutableStateOf(false) }
    Surface(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth(0.6f)
                .padding(32.dp, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    host.value,
                    enabled = !running.value,
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    onValueChange = {
                        host.value = it
                    },
                    label = { Text("Host") },
                    placeholder = { Text(HOST_DEFAULT, modifier = Modifier.alpha(0.5f)) }
                )
                Spacer(Modifier.size(16.dp))
                OutlinedTextField(
                    port.value,
                    enabled = !running.value,
                    modifier = Modifier.fillMaxWidth().weight(0.3f),
                    onValueChange = {
                        if (it.toIntOrNull() != null || it == "") {
                            port.value = it
                        }
                    },
                    label = { Text("Port") },
                    placeholder = { Text("$PORT_DEFAULT", modifier = Modifier.alpha(0.5f)) }
                )
            }
            Spacer(Modifier.size(8.dp))
            if (!running.value) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        Intent(
                            context.applicationContext,
                            ClientService::class.java
                        ).also { service ->
                            service.action = Actions.START.name
                            service.putExtra(
                                "port",
                                port.value.let { if (it == "") PORT_DEFAULT else it.toInt() })
                            service.putExtra(
                                "host",
                                host.value.let { if (it == "") HOST_DEFAULT else it })
                            context.startService(service)
                            running.value = true
                        }
                    }
                ) {
                    Text("Start")
                }
            } else {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        Intent(
                            context.applicationContext,
                            ClientService::class.java
                        ).also { service ->
                            service.action = Actions.STOP.name
                            service.putExtra(
                                "port",
                                port.value.let { if (it == "") PORT_DEFAULT else it.toInt() })
                            service.putExtra(
                                "host",
                                host.value.let { if (it == "") HOST_DEFAULT else it })
                            context.startService(service)
                            running.value = false
                        }
                    }
                ) {
                    Text("Stop")
                }
            }
        }
    }
}