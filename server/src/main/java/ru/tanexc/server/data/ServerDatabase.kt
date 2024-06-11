package ru.tanexc.server.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SwipeLogDao::class],
    exportSchema = false,
    version = 1,
)
abstract class ServerDatabase : RoomDatabase() {
    abstract val swipeLogDao: SwipeLogDao
}