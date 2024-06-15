package ru.tanexc.client.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.tanexc.client.presentation.screen.LaunchViewModel

val viewModelModule = module {
    singleOf(::LaunchViewModel)
}