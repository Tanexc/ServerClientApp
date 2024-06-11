package ru.tanexc.server.core.di

import androidx.room.Database
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.tanexc.server.data.ServerDatabase
import ru.tanexc.server.data.SwipeLogDao

val databaseModule = module {
    single<ServerDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = ServerDatabase::class.java,
            name = "server_database"
        ).build()
    }
    single<SwipeLogDao> { get<ServerDatabase>().swipeLogDao }
}