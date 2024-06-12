package ru.tanexc.server.util.server

import android.provider.ContactsContract.Data
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.jackson.JacksonWebsocketContentConverter
import io.ktor.serialization.jackson.jackson
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
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
import io.ktor.util.Digest
import io.ktor.websocket.close
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import ru.tanexc.server.core.util.DataState
import ru.tanexc.server.domain.model.ClientMessage
import ru.tanexc.server.domain.model.ServerMessage
import ru.tanexc.server.domain.model.SwipeLog
import ru.tanexc.server.domain.usecase.InsertSwipeLogsUseCase

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
                webSocket("/test") {
                    sendSerialized(getServerGestureMessage())
                    while(true) {
                        val message = receiveDeserialized<ClientMessage>()
                        launch(Dispatchers.IO) {
                            val log = SwipeLog(
                                0,
                                client = message.client,
                                info = message.message
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