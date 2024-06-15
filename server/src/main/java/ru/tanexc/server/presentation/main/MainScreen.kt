package ru.tanexc.server.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Feed
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.olshevski.navigation.reimagined.NavHost
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.popAll
import dev.olshevski.navigation.reimagined.rememberNavController
import ru.tanexc.server.presentation.screen.Screen
import ru.tanexc.server.presentation.screen.launch.LaunchScreen
import ru.tanexc.server.presentation.screen.logs.LogsScreen
import ru.tanexc.server.presentation.ui.theme.ServerClientAppTheme

@Composable
fun MainScreen() {
    val currentScreen = remember { mutableStateOf(Screen.Launch) }
    val navController = rememberNavController(Screen.Launch)
    LaunchedEffect(currentScreen.value) {
        navController.popAll()
        navController.navigate(currentScreen.value)
    }
    ServerClientAppTheme {
        Scaffold(
            bottomBar = {
                BottomAppBar {
                    NavigationBarItem(
                        alwaysShowLabel = false,
                        label = { Text(Screen.Launch.name) },
                        selected = currentScreen.value == Screen.Launch,
                        icon = { Icon(Icons.Outlined.RocketLaunch, null) },
                        onClick = { currentScreen.value = Screen.Launch },
                    )
                    NavigationBarItem(
                        alwaysShowLabel = false,
                        label = { Text(Screen.Logs.name) },
                        selected = currentScreen.value == Screen.Logs,
                        icon = { Icon(Icons.AutoMirrored.Outlined.Feed, null) },
                        onClick = { currentScreen.value = Screen.Logs },
                    )
                }
            },
        ) { paddings ->
            NavHost(navController) { screen ->
                when (screen) {
                    Screen.Launch -> LaunchScreen(Modifier.padding(paddings))
                    else -> LogsScreen(Modifier.padding(paddings))
                }
            }
        }
    }
}
