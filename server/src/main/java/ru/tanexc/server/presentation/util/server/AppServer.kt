package ru.tanexc.server.presentation.util.server

interface AppServer {
    fun stop()

    fun start(port: Int = 8081)
}
