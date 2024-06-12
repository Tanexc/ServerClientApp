package ru.tanexc.client.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.CloseReason
import io.ktor.websocket.close
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import ru.tanexc.client.core.DataState
import ru.tanexc.client.data.local.clientId
import ru.tanexc.client.model.ClientMessage
import ru.tanexc.client.model.ServerMessage
import kotlin.time.Duration

class ConnectionController(
    private val client: HttpClient,
    private val dataStore: DataStore<Preferences>
) {
    private lateinit var clientId: String
    private lateinit var session: DefaultClientWebSocketSession

    fun connect(
        host: String,
        port: Int,
    ): Flow<DataState<ServerMessage>> = flow {
        emit(DataState.Loading)

        clientId = dataStore.clientId()
        session = client.webSocketSession(host = host, port = port, path = "/test")

        while (session.isActive) {
            val message = session.receiveDeserialized<ServerMessage>()
            emit(DataState.Success(message))
        }
        if (session.closeReason.await()?.knownReason != CloseReason.Codes.NORMAL) {
            emit(DataState.Error)
        }
    }

    suspend fun send(message: String, dx: Double, dy: Double, duration: Long, chromeOpened: Boolean) {
        if (session.isActive) {
            session.sendSerialized(ClientMessage(clientId, message, dx, dy, duration, chromeOpened))
        }
    }

    suspend fun disconnect() {
        session.close(CloseReason(CloseReason.Codes.NORMAL, ""))
    }
}