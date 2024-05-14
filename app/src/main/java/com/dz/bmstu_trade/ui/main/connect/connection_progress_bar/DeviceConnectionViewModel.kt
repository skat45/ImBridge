package com.dz.bmstu_trade.ui.main.connect.connection_progress_bar

import android.content.Context
import android.net.wifi.WifiManager
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.bmstu_trade.domain.interactor.ConnectToWifiInteractor
import com.dz.bmstu_trade.domain.interactor.ConnectToWifiInteractorFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = WifiViewModelFactory::class)
class WifiViewModel @AssistedInject constructor(
    @Assisted val code: String,
    connectToWifiInteractorFactory: ConnectToWifiInteractorFactory
) : ViewModel() {

    private val connectToWifiInteractor: ConnectToWifiInteractor = connectToWifiInteractorFactory.create(code)

    private val _connectedToDevice = MutableStateFlow(false)
    val connectedToDevice: StateFlow<Boolean> = _connectedToDevice

    private val _wiFiIsEnabled = MutableStateFlow(false)
    val wiFiIsEnabled: StateFlow<Boolean> = _wiFiIsEnabled

    private fun checkWifiEnabled() {
        this._wiFiIsEnabled.value = connectToWifiInteractor.checkWifiEnabled()
    }

    init {
        viewModelScope.launch {
            var lastWiFiState = false
            while (true) {
                checkWifiEnabled()
                if (!lastWiFiState && _wiFiIsEnabled.value) { // Если WiFi был выключен и сейчас включили
                    lastWiFiState = true // Фиксируем включение
                    connectToWifiInteractor.connect { // И пытаемся подключиться к сети
                        _connectedToDevice.value = true
                    }
                }
                if (lastWiFiState && !_wiFiIsEnabled.value) { // Если WiFi выключили
                    lastWiFiState = false
                }
                delay(100L)
            }
        }
        viewModelScope.launch {
            while (true) {
                delay(5000)
                if (_wiFiIsEnabled.value && !_connectedToDevice.value)
                    connectToWifiInteractor.connect {
                        _connectedToDevice.value = true
                    }
            }
        }
    }
}

@AssistedFactory
interface WifiViewModelFactory {
    fun create(code: String): WifiViewModel
}
