package ru.tanexc.server.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.tanexc.server.domain.model.SwipeLog

@Dao
interface SwipeLogDao {
    @Query("select * from swipelog limit :limit offset :offset")
    suspend fun getAll(
        limit: Int = 20,
        offset: Int,
    ): List<SwipeLog>

    @Query("delete from swipelog")
    suspend fun deleteAll()

    @Query("delete from swipelog where id = :id")
    suspend fun deleteById(id: Long)

    @Insert
    suspend fun insert(log: SwipeLog)
}
