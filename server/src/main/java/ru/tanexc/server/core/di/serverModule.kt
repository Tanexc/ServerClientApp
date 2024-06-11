package ru.tanexc.server.core.di

import org.koin.dsl.module
import ru.tanexc.server.server.AppServer
import ru.tanexc.server.server.AppServerImpl

val serverModule = module {
    single<AppServer> { AppServerImpl() }
}