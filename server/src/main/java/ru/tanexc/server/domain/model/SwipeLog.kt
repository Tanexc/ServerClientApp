package ru.tanexc.server.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "swipelog")
data class SwipeLog(
    @PrimaryKey
    val id: Long,
    val client: String,
    val info: String
)