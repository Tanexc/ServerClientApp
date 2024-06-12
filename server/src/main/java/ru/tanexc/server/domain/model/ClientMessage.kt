package ru.tanexc.server.domain.model

data class ClientMessage(
    val client: String,
    val message: String,
    val dx: Double,
    val dy: Double,
    val duration: Long,
    val chromeOpened: Boolean
)
