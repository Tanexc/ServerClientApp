package ru.tanexc.server.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.tanexc.server.domain.usecase.logs.DeleteSwipeLogsByIdUseCase
import ru.tanexc.server.domain.usecase.logs.DeleteSwipeLogsUseCase
import ru.tanexc.server.domain.usecase.logs.GetSwipeLogsUseCase
import ru.tanexc.server.domain.usecase.logs.InsertSwipeLogsUseCase
import ru.tanexc.server.domain.usecase.servicestate.GetPortUseCase
import ru.tanexc.server.domain.usecase.servicestate.GetServiceStateUseCase
import ru.tanexc.server.domain.usecase.servicestate.SetPortUseCase
import ru.tanexc.server.domain.usecase.servicestate.SetServiceStateUseCase

val usecaseModule =
    module {
        singleOf(::SetServiceStateUseCase)
        singleOf(::GetServiceStateUseCase)
        singleOf(::SetPortUseCase)
        singleOf(::GetPortUseCase)

        singleOf(::InsertSwipeLogsUseCase)
        singleOf(::GetSwipeLogsUseCase)
        singleOf(::DeleteSwipeLogsUseCase)
        singleOf(::DeleteSwipeLogsByIdUseCase)
    }
