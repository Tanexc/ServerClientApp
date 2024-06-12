package ru.tanexc.server.util.service


import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import org.koin.android.ext.android.inject
import ru.tanexc.server.R
import ru.tanexc.server.core.PORT_DEFAULT
import ru.tanexc.server.util.server.AppServer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class ServerService: Service() {
    private val server: AppServer by inject()
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> {
                notification()
                executor.execute {
                    server.start(intent.getIntExtra("port", PORT_DEFAULT))
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