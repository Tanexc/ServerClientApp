package ru.tanexc.server.util.service


import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.tanexc.server.R
import ru.tanexc.server.core.PORT_DEFAULT
import ru.tanexc.server.core.util.ServiceState
import ru.tanexc.server.domain.usecase.servicestate.GetPortUseCase
import ru.tanexc.server.domain.usecase.servicestate.GetServiceStateUseCase
import ru.tanexc.server.domain.usecase.servicestate.SetServiceStateUseCase
import ru.tanexc.server.util.server.AppServer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class ServerService: Service() {
    private val server: AppServer by inject()
    private val scope = CoroutineScope(SupervisorJob())

    private val getPortUseCase: GetPortUseCase by inject()
    private val getServiceStateUseCase: GetServiceStateUseCase by inject()
    private val setServiceStateUseCase: SetServiceStateUseCase by inject()

    override fun onBind(intent: Intent?): IBinder? = null

    init {
        scope.launch(Dispatchers.Main) {
            getServiceStateUseCase().collect {
                when(it) {
                    ServiceState.Running -> {
                        server.start(getPortUseCase().toIntOrNull()?: 0)
                    }
                    ServiceState.Stopping -> {
                        server.stop()
                        setServiceStateUseCase(ServiceState.Stopped)
                    }
                    ServiceState.Stopped -> {
                        onDestroy()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> {
                notification()
                scope.launch(Dispatchers.IO) {
                    setServiceStateUseCase(ServiceState.Running)
                }
            }

            Actions.STOP.toString() -> {
                scope.launch(Dispatchers.IO) {
                    setServiceStateUseCase(ServiceState.Stopping)
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        server.stop()
        super.onDestroy()
    }

    private fun notification() {
        startForeground(1,
            NotificationCompat.Builder(this, "server_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("running")
                .setContentTitle("Server")
                .build()
        )
    }
}