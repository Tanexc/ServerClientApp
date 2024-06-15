package ru.tanexc.client.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SettingsAccessibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.tanexc.client.R
import ru.tanexc.client.core.HOST_DEFAULT
import ru.tanexc.client.core.PORT_DEFAULT
import ru.tanexc.client.core.util.ServiceState
import ru.tanexc.client.core.util.isAccessibilityEnabled
import ru.tanexc.client.service.ClientService

@Composable
fun LaunchScreen() {
    val viewModel: LaunchViewModel = koinViewModel()
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth(0.6f)
                .padding(32.dp, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    viewModel.host,
                    enabled = viewModel.serviceState == ServiceState.Stopped,
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    onValueChange = {
                        viewModel.setHost(it)
                    },
                    label = { Text("Host") },
                    placeholder = { Text(HOST_DEFAULT, modifier = Modifier.alpha(0.5f)) },
                )
                Spacer(Modifier.size(16.dp))
                OutlinedTextField(
                    viewModel.port,
                    enabled = viewModel.serviceState == ServiceState.Stopped,
                    modifier = Modifier.fillMaxWidth().weight(0.3f),
                    onValueChange = {
                        if (it.toIntOrNull() != null || it == "") {
                            viewModel.setPort(it)
                        }
                    },
                    label = { Text("Port") },
                    placeholder = { Text("$PORT_DEFAULT", modifier = Modifier.alpha(0.5f)) },
                )
            }
            Spacer(Modifier.size(8.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Stop client on reopen app")
                Switch(enabled = true, checked = viewModel.resume, onCheckedChange = {
                    viewModel.setResume(it)
                })
            }
            when (viewModel.serviceState) {
                ServiceState.Running ->
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.setServiceState(ServiceState.Stopping)
                        },
                    ) {
                        Text("Stop")
                    }

                ServiceState.Stopping -> {
                    CircularProgressIndicator(Modifier.size(28.dp))
                }

                else -> {
                    Button(
                        enabled = viewModel.serviceState == ServiceState.Stopped,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (context.isAccessibilityEnabled<ClientService>()) {
                                viewModel.setServiceState(ServiceState.Running)
                            } else {
                                showDialog.value = true
                            }
                        },
                    ) {
                        Text("Start")
                    }
                }
            }
        }

        AnimatedVisibility(showDialog.value) {
            AlertDialog(
                icon = {
                    Icon(Icons.Outlined.SettingsAccessibility, contentDescription = null)
                },
                title = {
                    Text(
                        stringResource(R.string.service_dialog_title),
                        textAlign = TextAlign.Center,
                    )
                },
                text = {
                    Text(stringResource(R.string.service_dialog_text), textAlign = TextAlign.Center)
                },
                onDismissRequest = {
                    showDialog.value = false
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog.value = false
                        },
                    ) {
                        Text("Ok")
                    }
                },
            )
        }
    }
}
