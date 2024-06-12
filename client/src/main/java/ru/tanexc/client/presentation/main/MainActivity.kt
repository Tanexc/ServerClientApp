package ru.tanexc.client.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject
import ru.tanexc.client.core.DataState
import ru.tanexc.client.data.ConnectionController
import ru.tanexc.client.presentation.ui.theme.ServerClientAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ServerClientAppTheme {
                Surface {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(Modifier.fillMaxWidth()) {
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
                        }
                    }
                }
            }
        }
    }
}
