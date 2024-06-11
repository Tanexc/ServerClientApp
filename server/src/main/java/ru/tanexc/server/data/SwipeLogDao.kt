package ru.tanexc.server.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import ru.tanexc.server.domain.model.SwipeLog

@Dao
interface SwipeLogDao {
    @Query("SELECT * from swipelog")
    suspend fun getAll(): List<SwipeLog>

    @Delete(SwipeLog::class)
    suspend fun deleteAll()

    @Delete
    suspend fun deleteById(id: Long)
}