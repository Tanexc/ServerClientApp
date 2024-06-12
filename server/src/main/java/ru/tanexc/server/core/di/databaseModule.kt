package ru.tanexc.server.core.di

import androidx.room.Database
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.tanexc.server.data.ServerDatabase
import ru.tanexc.server.data.SwipeLogDao
import ru.tanexc.server.domain.usecase.DeleteSwipeLogsByIdUseCase
import ru.tanexc.server.domain.usecase.DeleteSwipeLogsUseCase
import ru.tanexc.server.domain.usecase.GetSwipeLogsUseCase
import ru.tanexc.server.domain.usecase.InsertSwipeLogsUseCase

val databaseModule = module {
    single<ServerDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = ServerDatabase::class.java,
            name = "server_database"
        ).build()
    }

    single<SwipeLogDao> { get<ServerDatabase>().swipeLogDao }

    singleOf(::DeleteSwipeLogsUseCase)
    singleOf(::DeleteSwipeLogsByIdUseCase)
    singleOf(::InsertSwipeLogsUseCase)
    singleOf(::GetSwipeLogsUseCase)
}