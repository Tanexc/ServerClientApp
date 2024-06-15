package ru.tanexc.client.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.tanexc.client.domain.usecase.GetClientIdUseCase
import ru.tanexc.client.domain.usecase.GetServiceStateUseCase
import ru.tanexc.client.domain.usecase.SetServiceStateUseCase
import ru.tanexc.client.domain.usecase.SetHostUseCase
import ru.tanexc.client.domain.usecase.SetPortUseCase
import ru.tanexc.client.domain.usecase.GetHostUseCase
import ru.tanexc.client.domain.usecase.GetPortUseCase
import ru.tanexc.client.domain.usecase.GetStopOnResumeUseCase
import ru.tanexc.client.domain.usecase.SetStopOnResumeUseCase


val useCaseModule = module {
    singleOf(::GetClientIdUseCase)
    singleOf(::SetServiceStateUseCase)
    singleOf(::GetServiceStateUseCase)

    singleOf(::GetHostUseCase)
    singleOf(::GetPortUseCase)
    singleOf(::SetHostUseCase)
    singleOf(::SetPortUseCase)

    singleOf(::SetStopOnResumeUseCase)
    singleOf(::GetStopOnResumeUseCase)

}