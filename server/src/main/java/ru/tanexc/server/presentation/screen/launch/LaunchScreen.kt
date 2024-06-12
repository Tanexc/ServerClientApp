package ru.tanexc.server.presentation.screen.launch

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import ru.tanexc.server.core.PORT_DEFAULT
import ru.tanexc.server.core.util.ServiceState
import ru.tanexc.server.util.service.Actions
import ru.tanexc.server.util.service.ServerService

@Composable
fun LaunchScreen(modifier: Modifier) {
    val running = remember { mutableStateOf(ServiceState.Stopped) }
    val context = LocalContext.current

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth(0.5f)
                .padding(32.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val port = remember { mutableStateOf("") }
            OutlinedTextField(
                port.value,
                enabled = running.value == ServiceState.Stopped,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    if (it.toIntOrNull() != null || it == "") {
                        port.value = it
                    }
                },
                label = { Text("Port") },
                placeholder = { Text("$PORT_DEFAULT", modifier = Modifier.alpha(0.5f)) }
            )
            Spacer(Modifier.size(8.dp))
            if (running.value == ServiceState.Stopped) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = running.value == ServiceState.Stopped,
                    onClick = {
                        running.value = ServiceState.Runnning
                        Intent(
                            context.applicationContext,
                            ServerService::class.java
                        ).also { service ->
                            service.action = Actions.START.name
                            service.putExtra(
                                "port",
                                port.value.let { if (it == "") PORT_DEFAULT else it.toInt() })
                            context.startService(service)
                        }
                    }) {
                    Text("Start server")
                }
            } else {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = running.value == ServiceState.Runnning,
                    onClick = {
                        running.value = ServiceState.Stopping
                        Intent(
                            context.applicationContext,
                            ServerService::class.java
                        ).also { service ->
                            service.action = Actions.STOP.name
                            context.startService(service)
                        }
                        running.value = ServiceState.Stopped
                    }) {
                    Text("Stop server")
                }
            }
        }
    }
}