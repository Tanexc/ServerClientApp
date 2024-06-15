package ru.tanexc.server.presentation.screen.launch

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tanexc.server.core.util.ServiceState
import ru.tanexc.server.domain.usecase.servicestate.GetPortUseCase
import ru.tanexc.server.domain.usecase.servicestate.GetServiceStateUseCase
import ru.tanexc.server.domain.usecase.servicestate.SetPortUseCase
import ru.tanexc.server.domain.usecase.servicestate.SetServiceStateUseCase

class LaunchViewModel(
    private val getServiceStateUseCase: GetServiceStateUseCase,
    private val getPortUseCase: GetPortUseCase,
    private val setPortUseCase: SetPortUseCase,
) : ViewModel() {
    private val _serviceState: MutableState<ServiceState> = mutableStateOf(ServiceState.Undefined)
    val serviceState by _serviceState

    private val _port = mutableStateOf("")
    val port by _port

    init {
        viewModelScope.launch(Dispatchers.Main) {
            _port.value = getPortUseCase()
            getServiceStateUseCase().collect {
                _serviceState.value = it
            }
        }
    }

    fun setPort(port: String) {
        _port.value = port
        viewModelScope.launch(Dispatchers.IO) {
            if (port != "") setPortUseCase(port.toInt())
        }
    }
}
