package com.dz.bmstu_trade.ui.main.connect.connection_progress_bar

import android.content.Context
import android.net.wifi.WifiManager
import android.provider.Settings
import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import com.dz.bmstu_trade.domain.interactor.ConnectToWifiInteractor
import com.dz.bmstu_trade.domain.interactor.ConnectToWifiInteractorImpl
import com.dz.bmstu_trade.ui.main.connect.device_code.AddDeviceViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class WifiViewModel(codeViewModel: AddDeviceViewModel) : ViewModel() {

    private val connectToWifiInteractor: ConnectToWifiInteractor = ConnectToWifiInteractorImpl(codeViewModel.code.value.value)

    private val wifiManager = AppContextHolder.getContext()?.getSystemService(Context.WIFI_SERVICE) as WifiManager

    private val _connectedToDevice = MutableStateFlow(false)
    val connectedToDevice: StateFlow<Boolean> = _connectedToDevice


    private val _wiFiIsEnabled = MutableStateFlow(false)
    val wiFiIsEnabled: StateFlow<Boolean> = _wiFiIsEnabled

    private fun checkWifiEnabled() {
        this._wiFiIsEnabled.value = wifiManager.isWifiEnabled
    }

    init {
        GlobalScope.launch {
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
        GlobalScope.launch {
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
