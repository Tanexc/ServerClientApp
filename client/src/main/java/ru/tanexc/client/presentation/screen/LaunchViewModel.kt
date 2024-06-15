package ru.tanexc.client.presentation.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tanexc.client.core.util.ServiceState
import ru.tanexc.client.domain.usecase.GetHostUseCase
import ru.tanexc.client.domain.usecase.GetPortUseCase
import ru.tanexc.client.domain.usecase.GetServiceStateUseCase
import ru.tanexc.client.domain.usecase.GetStopOnResumeUseCase
import ru.tanexc.client.domain.usecase.SetHostUseCase
import ru.tanexc.client.domain.usecase.SetPortUseCase
import ru.tanexc.client.domain.usecase.SetServiceStateUseCase
import ru.tanexc.client.domain.usecase.SetStopOnResumeUseCase

class LaunchViewModel(
    private val getServiceStateUseCase: GetServiceStateUseCase,
    private val setServiceStateUseCase: SetServiceStateUseCase,
    private val setPortUseCase: SetPortUseCase,
    private val setHostUseCase: SetHostUseCase,
    private val getPortUseCase: GetPortUseCase,
    private val getHostUseCase: GetHostUseCase,
    private val getStopOnResumeUseCase: GetStopOnResumeUseCase,
    private val setStopOnResumeUseCase: SetStopOnResumeUseCase
): ViewModel() {
    private val _serviceState: MutableState<ServiceState> = mutableStateOf(ServiceState.Undefined)
    val serviceState by _serviceState

    private val _port = mutableStateOf("")
    val port by _port

    private val _host = mutableStateOf("")
    val host by _host

    private val _resume = mutableStateOf(false)
    val resume by _resume


    init {
        viewModelScope.launch(Dispatchers.Main) {
            _port.value = getPortUseCase()
            _host.value = getHostUseCase()
            _resume.value = getStopOnResumeUseCase()
              getServiceStateUseCase().collect {
                  _serviceState.value = it
              }
        }
    }

    fun setServiceState(state: ServiceState) {
        viewModelScope.launch(Dispatchers.IO) {
            setServiceStateUseCase(state)
        }
    }

    fun setHost(host: String) {
        _host.value = host
        viewModelScope.launch(Dispatchers.IO) {
            setHostUseCase(host)
        }
    }

    fun setPort(port: String) {
        _port.value = port
        viewModelScope.launch(Dispatchers.IO) {
            if (port != "") setPortUseCase(port.toInt())
        }
    }

    fun setResume(resume: Boolean) {
        _resume.value = resume
        viewModelScope.launch(Dispatchers.IO) {
            setStopOnResumeUseCase(resume)
        }

    }
}