package ru.tanexc.server.server

interface AppServer {
    fun stop()

    fun start(port: Int = 8081)
}