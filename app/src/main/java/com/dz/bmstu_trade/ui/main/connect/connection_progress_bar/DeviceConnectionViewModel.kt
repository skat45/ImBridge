package com.dz.bmstu_trade.ui.main.connect.connection_progress_bar

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.ui.main.connect.device_code.AddDeviceViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class WifiViewModel(private val context: Context, private val codeViewModel: AddDeviceViewModel) : ViewModel() {
    private val _connectedToDevice = MutableStateFlow(false)
    val connectedToDevice: StateFlow<Boolean> = _connectedToDevice

    private val _specifier = WifiNetworkSpecifier.Builder()
        .setSsid("IMBRIDGE-" + codeViewModel.code.value.value)
        .setWpa2Passphrase("012345666")
        .build()
    private val _request = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .setNetworkSpecifier(_specifier)
        .build()
    private val _connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val _networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _connectedToDevice.value = true
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.EFFECT_TICK))
        }
    }


    private val _wiFiIsEnabled = MutableStateFlow(false)
    val wiFiIsEnabled: StateFlow<Boolean> = _wiFiIsEnabled

    private val wifiManager: WifiManager by lazy {
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

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
                    _connectivityManager.requestNetwork(_request, _networkCallback) // И пытаемся подключиться к сети
                }
                if (lastWiFiState && !_wiFiIsEnabled.value) { // Если WiFi выключили
                    lastWiFiState = false
                }
                delay(100L)
            }
        }
    }
}
