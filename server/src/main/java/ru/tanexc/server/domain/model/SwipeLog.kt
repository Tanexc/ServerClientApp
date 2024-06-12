package ru.tanexc.server.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "swipelog")
data class SwipeLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val client: String,
    val info: String,
    val dx: Double,
    val dy: Double,
    val duration: Long
)