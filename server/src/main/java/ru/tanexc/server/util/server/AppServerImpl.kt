package ru.tanexc.server.util.server

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.jackson.JacksonWebsocketContentConverter
import io.ktor.serialization.jackson.jackson
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.receiveDeserialized
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.close
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.tanexc.server.core.util.DataState
import ru.tanexc.server.domain.model.ClientMessage
import ru.tanexc.server.domain.model.SwipeLog
import ru.tanexc.server.domain.usecase.logs.InsertSwipeLogsUseCase

class AppServerImpl(
    private val insertSwipeLogsUseCase: InsertSwipeLogsUseCase
): AppServer {
    private lateinit var engine: ApplicationEngine

    override fun stop() {
        engine.stop()
    }

    override fun start(port: Int) {
        engine = embeddedServer(Netty, port) {
            install(ContentNegotiation) {
                jackson {
                    enable(SerializationFeature.INDENT_OUTPUT)
                }
                json()
            }
            install(WebSockets) {
                contentConverter = JacksonWebsocketContentConverter()
            }
            routing {
                webSocket("/gestures") {
                    sendSerialized(getServerGestureMessage())
                    while(true) {
                        val message = receiveDeserialized<ClientMessage>()
                        delay(2000)
                        launch(Dispatchers.IO) {
                            val log = SwipeLog(
                                client = message.client,
                                info = message.message,
                                dx = message.dx,
                                dy = message.dy,
                                duration = message.duration
                            )
                            insertSwipeLogsUseCase(log).collect { state ->
                                when(state) {
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

                get("/") {
                    call.respond("Testing")
                }
            }
        }
        engine.start()
    }
}