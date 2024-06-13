package ru.tanexc.client.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.tanexc.client.domain.usecase.GetClientIdUseCase
import ru.tanexc.client.domain.usecase.GetServiceStateUseCase
import ru.tanexc.client.domain.usecase.SetServiceStateUseCase

val usecaseModule = module {
    singleOf(::GetClientIdUseCase)
    singleOf(::SetServiceStateUseCase)
    singleOf(::GetServiceStateUseCase)
}