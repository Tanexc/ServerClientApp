package ru.tanexc.client.presentation.service

import android.accessibilityservice.AccessibilityService
import android.app.NotificationManager
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.tanexc.client.R
import ru.tanexc.client.core.util.DataState
import ru.tanexc.client.core.util.ServiceState
import ru.tanexc.client.data.ConnectionController
import ru.tanexc.client.domain.usecase.GetHostUseCase
import ru.tanexc.client.domain.usecase.GetPortUseCase
import ru.tanexc.client.domain.usecase.GetServiceStateUseCase
import ru.tanexc.client.domain.usecase.SetServiceStateUseCase
import ru.tanexc.client.presentation.service.util.gesture
import ru.tanexc.client.presentation.service.util.launchActivity

@OptIn(ExperimentalStdlibApi::class)
class ClientService : AccessibilityService() {
    private val controller: ConnectionController by inject()

    private val getServiceStateUseCase: GetServiceStateUseCase by inject()
    private val setServiceStateUseCase: SetServiceStateUseCase by inject()
    private val getHostUseCase: GetHostUseCase by inject()
    private val getPortUseCase: GetPortUseCase by inject()

    private val job = SupervisorJob()
    private val scope: CoroutineScope = CoroutineScope(job)

    init {
        scope.launch(Dispatchers.IO) {
            getServiceStateUseCase().collect {
                when (it) {
                    ServiceState.Running -> {
                        launch {
                            try {
                                val flow =
                                    controller.connect(
                                        getHostUseCase(),
                                        getPortUseCase().let { port -> if (port.isEmpty()) 8081 else port.toInt() },
                                        onSuccess = {
                                            applicationContext
                                                .launchActivity(
                                                    "com.android.chrome",
                                                    Uri.parse("https://www.google.com"),
                                                    Intent.FLAG_ACTIVITY_NEW_TASK,
                                                )
                                        },
                                        onFailure = {
                                            setServiceStateUseCase(ServiceState.Stopping)
                                        },
                                    )
                                delay(500)
                                flow.collect { state ->
                                    when (state) {
                                        is DataState.Success -> {
                                            val data = state.data
                                            gesture(data.dx, data.dy, data.duration)
                                            controller.send(
                                                System.currentTimeMillis().toHexString(),
                                                data.dx,
                                                data.dy,
                                                data.duration,
                                                true,
                                            )
                                        }

                                        else -> {}
                                    }
                                }
                            } catch (e: Exception) {
                                setServiceStateUseCase(ServiceState.Stopping)
                            }
                        }
                    }

                    ServiceState.Stopping -> {
                        controller.disconnect()
                        setServiceStateUseCase(ServiceState.Stopped)
                    }

                    ServiceState.Stopped -> {
                        stopSelf()
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        notification()
        super.onStartCommand(intent, flags, startId)
        return START_NOT_STICKY
    }

    private fun notification() {
        startForeground(
            1,
            NotificationCompat
                .Builder(this, "client_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("running")
                .setContentTitle("Client")
                .build(),
        )
    }
}
