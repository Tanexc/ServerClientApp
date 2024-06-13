package ru.tanexc.server.core.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.tanexc.server.data.database.ServerDatabase
import ru.tanexc.server.data.database.SwipeLogDao
import ru.tanexc.server.domain.usecase.logs.DeleteSwipeLogsByIdUseCase
import ru.tanexc.server.domain.usecase.logs.DeleteSwipeLogsUseCase
import ru.tanexc.server.domain.usecase.logs.GetSwipeLogsUseCase
import ru.tanexc.server.domain.usecase.logs.InsertSwipeLogsUseCase

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