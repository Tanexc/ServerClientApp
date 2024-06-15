package ru.tanexc.server.util.server

import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import io.ktor.server.websocket.receiveDeserialized
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.close
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.tanexc.server.core.util.DataState
import ru.tanexc.server.domain.model.ClientMessage
import ru.tanexc.server.domain.usecase.logs.InsertSwipeLogsUseCase
import ru.tanexc.server.util.server.util.config
import ru.tanexc.server.util.server.util.getServerGestureMessage

class AppServerImpl(
    private val insertSwipeLogsUseCase: InsertSwipeLogsUseCase,
) : AppServer {
    private lateinit var engine: ApplicationEngine

    override fun stop() {
        if (::engine.isInitialized) {
            engine.stop()
        }
    }

    override fun start(port: Int) {
        engine =
            embeddedServer(Netty, port) {
                config()
                routing {
                    webSocket("/gestures") {
                        sendSerialized(getServerGestureMessage())
                        while (true) {
                            val message = receiveDeserialized<ClientMessage>()
                            delay(1700)
                            launch(Dispatchers.IO) {
                                val log = message.asSwipeLog()

                                insertSwipeLogsUseCase(log).collect { state ->
                                    when (state) {
                                        is DataState.Success -> {
                                            val responseMessage = getServerGestureMessage()
                                            sendSerialized(responseMessage)
                                        }

                                        is DataState.Loading -> {}
                                        else -> {
                                            close()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        engine.start()
    }
}
