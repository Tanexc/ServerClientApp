package ru.tanexc.client.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.tanexc.client.presentation.screen.LaunchScreen
import ru.tanexc.client.presentation.ui.theme.ServerClientAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ServerClientAppTheme {
                LaunchScreen()
            }
        }
    }
}
