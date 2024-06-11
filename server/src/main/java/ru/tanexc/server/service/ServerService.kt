package ru.tanexc.server.service


import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.ext.android.inject
import ru.tanexc.server.server.AppServer
import java.util.concurrent.Executors


class ServerService: Service() {
    private val server: AppServer by inject()
    private val state: MutableState<String> = mutableStateOf("Loading")

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startForeground(1,
            Notification.Builder(applicationContext)
                .setContentText(state.value)
                .build()
        )

        Executors.newSingleThreadExecutor().execute {
            server.start()
            state.value = "Running"
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        server.stop()
    }
}