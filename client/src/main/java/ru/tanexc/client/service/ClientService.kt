package ru.tanexc.client.service

import android.accessibilityservice.AccessibilityService
import android.app.ActivityManager
import android.content.Intent
import android.net.Uri
import android.view.accessibility.AccessibilityEvent
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.tanexc.client.core.util.DataState
import ru.tanexc.client.core.HOST_DEFAULT
import ru.tanexc.client.core.PORT_DEFAULT
import ru.tanexc.client.data.ConnectionController
import ru.tanexc.serverclientapp.R

class ClientService : AccessibilityService() {
    private val controller: ConnectionController by inject()
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob())
    private var started: Boolean = false
    private var port = 0
    private var host = ""

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        when(event?.eventType) {
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                started = false
            }
        }
    }

    override fun onInterrupt() {}

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.name -> {
                if (!started) {
                    notification()
                    launchChrome()
                    port = intent.getIntExtra("port", PORT_DEFAULT)
                    host = intent.getStringExtra("host") ?: HOST_DEFAULT
                    scope.launch(Dispatchers.IO) {
                        delay(3000)
                        started = true
                        controller.connect(
                            host,
                            port,
                        ).collect { state ->
                            when (state) {
                                is DataState.Success -> {
                                    val data = state.data
                                    if (started) {
                                        gesture(
                                            data.dx,
                                            data.dy,
                                            data.duration
                                        )
                                        controller.send(
                                            "Gesture done",
                                            data.dx,
                                            data.dy,
                                            data.duration,
                                            true
                                        )
                                    }

                                }

                                else -> {}

                            }
                        }
                    }
                }
            }

            Actions.STOP.name -> {

                stopSelf()
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
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.parse("https://www.google.com")
        intent.`package` = "com.android.chrome"
        applicationContext.startActivity(intent)
        val m = applicationContext.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        m.appTasks
        return true
    }
}