package ru.tanexc.server.core.di

import org.koin.dsl.module
import ru.tanexc.server.server.AppServer
import ru.tanexc.server.server.AppServerImpl
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

val serverModule = module {
    single<AppServer> { AppServerImpl() }
    single<ExecutorService> { Executors.newSingleThreadExecutor() }
}