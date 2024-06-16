package ru.tanexc.server.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.tanexc.server.presentation.util.server.AppServer
import ru.tanexc.server.presentation.util.server.AppServerImpl
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

val serverModule =
    module {
        singleOf(::AppServerImpl) bind AppServer::class
        single<ExecutorService> { Executors.newSingleThreadExecutor() }
    }
