package ru.tanexc.server.server

import android.util.Log
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.addShutdownHook
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

class AppServerImpl: AppServer {
    private lateinit var engine: ApplicationEngine

    override fun stop() {
        engine.stop()
    }

    override fun start(port: Int) {
        engine = embeddedServer(Netty, port) {
            install(ContentNegotiation) {
                jackson {}
            }
            routing {
                get("/") {
                    call.respond(mapOf("message" to "Hello world"))
                }
            }
        }
        engine.start()
    }
}