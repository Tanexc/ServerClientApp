package ru.tanexc.server.util.server

import ru.tanexc.server.domain.model.ServerMessage
import kotlin.random.Random

fun getServerGestureMessage(): ServerMessage =
    ServerMessage(
        Random.nextDouble(-0.9, 0.9),
        Random.nextDouble(-0.9, 0.9),
        Random.nextLong(200, 2000)
    )