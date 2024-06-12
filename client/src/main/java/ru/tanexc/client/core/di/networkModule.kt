package ru.tanexc.client.core.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.jackson.JacksonWebsocketContentConverter
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.tanexc.client.data.ConnectionController

val networkModule = module {
    single<HttpClient> {
        HttpClient(CIO) {
            install(WebSockets) {
                contentConverter = JacksonWebsocketContentConverter()
            }
        }
    }

    singleOf(::ConnectionController)
}