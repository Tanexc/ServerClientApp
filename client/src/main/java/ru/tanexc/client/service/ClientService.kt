package ru.tanexc.client.service

import android.app.ActivityManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.tanexc.client.core.DataState
import ru.tanexc.client.core.util.HOST_DEFAULT
import ru.tanexc.client.core.util.PORT_DEFAULT
import ru.tanexc.client.data.ConnectionController
import ru.tanexc.serverclientapp.R

class ClientService(
    private val controller: ConnectionController
) : Service() {
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob())
    private val activityManager =
        applicationContext.getSystemService(ACTIVITY_SERVICE) as ActivityManager

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notification()
        launchChrome()
        val port = intent?.getIntExtra("port", PORT_DEFAULT) ?: PORT_DEFAULT
        val host = intent?.getStringExtra("host") ?: HOST_DEFAULT
        scope.launch(Dispatchers.IO) {
            controller.connect(host, port).collect { state ->

                when (state) {
                    is DataState.Success -> {
                        val data = state.data
                        val chromeOpened =
                            activityManager.getRunningTasks(1)[0].topActivity?.packageName == "com.android.Chrome"
                        if (chromeOpened) {
                            applicationContext.gesture(
                                data.dx,
                                data.dy,
                                data.duration
                            )
                        }
                        controller.send(
                            "Chrome ${if (chromeOpened) "opened" else "closed"}",
                            data.dx,
                            data.dy,
                            data.duration,
                            chromeOpened
                        )
                    }

                    else -> {}
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun notification() {
        startForeground(
            1,
            NotificationCompat.Builder(this, "client_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("running")
                .setContentTitle("Client")
                .build()
        )
    }

    private fun launchChrome(): Boolean {
        val intent: Intent =
            applicationContext.packageManager.getLaunchIntentForPackage("com.android.chrome")
                ?: return false
        applicationContext.startActivity(intent)
        return true
    }
}