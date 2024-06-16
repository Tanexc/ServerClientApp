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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.tanexc.server.core.PORT_DEFAULT
import ru.tanexc.server.core.util.ServiceState
import ru.tanexc.server.presentation.util.service.Actions
import ru.tanexc.server.presentation.util.service.ServerService

@Composable
fun LaunchScreen(modifier: Modifier) {
    val viewModel: LaunchViewModel = koinViewModel()
    val context = LocalContext.current

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth(0.5f)
                .padding(32.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            OutlinedTextField(
                viewModel.port,
                enabled = viewModel.serviceState == ServiceState.Stopped,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    if (it.toIntOrNull() != null || it == "") {
                        viewModel.setPort(it)
                    }
                },
                label = { Text("Port") },
                placeholder = { Text("$PORT_DEFAULT", modifier = Modifier.alpha(0.5f)) },
            )
            Spacer(Modifier.size(8.dp))
            when (viewModel.serviceState) {
                ServiceState.Running -> {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = viewModel.serviceState == ServiceState.Running,
                        onClick = {
                            Intent(
                                context.applicationContext,
                                ServerService::class.java,
                            ).also {
                                it.action = Actions.STOP.name
                                context.applicationContext.startService(it)
                            }
                        },
                    ) {
                        Text("Stop server")
                    }
                }

                ServiceState.Stopping -> {
                    CircularProgressIndicator(Modifier.size(28.dp))
                }

                else -> {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = viewModel.serviceState == ServiceState.Stopped,
                        onClick = {
                            if (viewModel.serviceState != ServiceState.Running) {
                                Intent(
                                    context.applicationContext,
                                    ServerService::class.java,
                                ).also {
                                    it.action = Actions.START.name
                                    context.applicationContext.startService(it)
                                }
                            }
                        },
                    ) {
                        Text("Start server")
                    }
                }
            }
        }
    }
}
