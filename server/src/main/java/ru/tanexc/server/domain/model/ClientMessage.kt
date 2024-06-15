package ru.tanexc.server.domain.model

data class ClientMessage(
    val client: String,
    val message: String,
    val dx: Double,
    val dy: Double,
    val duration: Long,
    val chromeOpened: Boolean
) {
    fun asSwipeLog(): SwipeLog = SwipeLog(
        client = client,
        info = message,
        dx = dx,
        dy = dy,
        duration = duration
    )
}
