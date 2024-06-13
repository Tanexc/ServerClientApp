package ru.tanexc.server.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.tanexc.server.domain.usecase.servicestate.GetServiceStateUseCase
import ru.tanexc.server.domain.usecase.servicestate.SetServiceStateUseCase
import ru.tanexc.server.domain.usecase.logs.InsertSwipeLogsUseCase
import ru.tanexc.server.domain.usecase.logs.GetSwipeLogsUseCase
import ru.tanexc.server.domain.usecase.logs.DeleteSwipeLogsUseCase
import ru.tanexc.server.domain.usecase.logs.DeleteSwipeLogsByIdUseCase

val usecaseModule = module {
    singleOf(::SetServiceStateUseCase)
    singleOf(::GetServiceStateUseCase)
    singleOf(::InsertSwipeLogsUseCase)
    singleOf(::GetSwipeLogsUseCase)
    singleOf(::DeleteSwipeLogsUseCase)
    singleOf(::DeleteSwipeLogsByIdUseCase)
}