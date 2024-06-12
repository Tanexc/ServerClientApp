package ru.tanexc.server.util.server

interface AppServer {
    fun stop()

    fun start(port: Int = 8081)
}