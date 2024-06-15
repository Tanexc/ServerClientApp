package ru.tanexc.server.core.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.tanexc.server.presentation.screen.launch.LaunchViewModel
import ru.tanexc.server.presentation.screen.logs.LogsViewModel

val viewModelModule = module {
    viewModelOf(::LogsViewModel)
    viewModelOf(::LaunchViewModel)
}