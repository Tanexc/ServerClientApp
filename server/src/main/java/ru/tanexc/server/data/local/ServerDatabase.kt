package ru.tanexc.server.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.tanexc.server.domain.model.SwipeLog

@Database(
    entities = [SwipeLog::class],
    exportSchema = false,
    version = 1,
)
abstract class ServerDatabase : RoomDatabase() {
    abstract val swipeLogDao: SwipeLogDao
}