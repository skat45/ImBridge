package com.dz.bmstu_trade.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.bmstu_trade.data.model.DeviceState
import com.dz.bmstu_trade.data.network.WebSocketListener
import com.dz.bmstu_trade.domain.interactor.DeviceStateInteractorImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val interactor: DeviceStateInteractorImpl
) : ViewModel(), WebSocketListener {

    private val _deviceState = MutableStateFlow<DeviceStateScreen>(DeviceStateScreen.Loading)
    val deviceState: StateFlow<DeviceStateScreen> = _deviceState.asStateFlow()

    init {
        viewModelScope.launch {
            interactor.connectToDevice(this@HomeViewModel)
        }
    }

    override fun onConnected(deviceState: DeviceState) {
        _deviceState.value = DeviceStateScreen.Success(deviceState)
    }

    override fun onConnectionError() {
        viewModelScope.launch {
            _deviceState.value = DeviceStateScreen.Error
        }
    }

    override fun onMessage(deviceState: DeviceState) {
        viewModelScope.launch {
            _deviceState.value = DeviceStateScreen.Success(deviceState)
        }
    }

    override fun onDisconnected() {
        viewModelScope.launch {
            _deviceState.value = DeviceStateScreen.Disconnected
        }
    }

}

sealed class DeviceStateScreen {
    object Loading : DeviceStateScreen()
    object Disconnected : DeviceStateScreen()
    object Error : DeviceStateScreen()
    data class Success(val data: DeviceState) : DeviceStateScreen()
}
