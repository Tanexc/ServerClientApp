package ru.tanexc.server.server

import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

class AppServerImpl: AppServer {
    private val server = embeddedServer(Netty) {
        install(ContentNegotiation) {
            jackson {}
        }
        routing {
            get("/") {
                call.respond(mapOf("message" to "Hello world"))
            }
        }
    }

    override fun stop() {
        server.stop()
    }

    override fun start() {
        server.start(true)
    }
}