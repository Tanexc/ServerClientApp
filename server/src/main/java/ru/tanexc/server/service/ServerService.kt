package ru.tanexc.server.service


import android.app.Notification
import android.app.Notification.Action
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.ext.android.inject
import ru.tanexc.server.R
import ru.tanexc.server.server.AppServer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class ServerService: Service() {
    private val server: AppServer by inject()
    private val executor: ExecutorService by inject()
    private val state: MutableState<String> = mutableStateOf("Loading")

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> {
                notification()
                executor.execute {
                    server.start(intent.getIntExtra("port", 8081))
                }
            }

            Actions.STOP.toString() -> {
                if (!executor.isTerminated) {
                    executor.awaitTermination(2000L, TimeUnit.MILLISECONDS)
                    executor.execute {
                        server.stop()
                    }
                }
                stopSelf()
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        server.stop()
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