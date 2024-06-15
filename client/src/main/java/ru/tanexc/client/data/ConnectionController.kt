package ru.tanexc.client.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.CloseReason
import io.ktor.websocket.close
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import ru.tanexc.client.core.util.DataState
import ru.tanexc.client.domain.model.ClientMessage
import ru.tanexc.client.domain.model.ServerMessage
import ru.tanexc.client.domain.usecase.GetClientIdUseCase

class ConnectionController(
    private val client: HttpClient,
    private val getClientIdUseCase: GetClientIdUseCase
) {
    private lateinit var clientId: String
    private lateinit var session: DefaultClientWebSocketSession

    fun connect(
        host: String,
        port: Int
    ): Flow<DataState<ServerMessage>> = flow {
        emit(DataState.Loading)

        clientId = getClientIdUseCase()
        session = client.webSocketSession(host = host, port = port, path = "/gestures")

        while (session.isActive) {
            val message = session.receiveDeserialized<ServerMessage>()
            emit(DataState.Success(message))
        }
    }

    suspend fun send(message: String, dx: Double, dy: Double, duration: Long, chromeOpened: Boolean) {
        if (session.isActive) {
            session.sendSerialized(ClientMessage(clientId, message, dx, dy, duration, chromeOpened))
        }
    }

    fun disconnect() {
        if (::session.isInitialized) {
            session.cancel()
        }
    }
}