package ru.tanexc.client.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.tanexc.client.core.util.ServiceState
import ru.tanexc.client.domain.usecase.GetStopOnResumeUseCase
import ru.tanexc.client.domain.usecase.SetServiceStateUseCase
import ru.tanexc.client.presentation.screen.LaunchScreen
import ru.tanexc.client.presentation.ui.theme.ServerClientAppTheme

class MainActivity : ComponentActivity() {
    private val getStopOnResume: GetStopOnResumeUseCase by inject()
    private val setServiceStateUseCase: SetServiceStateUseCase by inject()

    init {
        CoroutineScope(SupervisorJob()).launch(Dispatchers.Main) {
            lifecycle.currentStateFlow.collect {
                if (it == Lifecycle.State.RESUMED && getStopOnResume()) {
                    setServiceStateUseCase(ServiceState.Stopping)
                }
            }
        }
    }

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
