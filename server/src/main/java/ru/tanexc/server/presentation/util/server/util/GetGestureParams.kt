package ru.tanexc.server.presentation.util.server.util

import ru.tanexc.server.domain.model.ServerMessage
import kotlin.random.Random

fun getServerGestureMessage(): ServerMessage =
    ServerMessage(
        Random.nextDouble(-0.9, 0.9),
        Random.nextDouble(-0.9, 0.9),
        Random.nextLong(250, 1500),
    )
