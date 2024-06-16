package ru.tanexc.server.presentation.util.server.util

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.jackson.JacksonWebsocketContentConverter
import io.ktor.serialization.jackson.jackson
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.websocket.WebSockets

fun Application.config() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
        json()
    }
    install(WebSockets) {
        contentConverter = JacksonWebsocketContentConverter()
    }
}
