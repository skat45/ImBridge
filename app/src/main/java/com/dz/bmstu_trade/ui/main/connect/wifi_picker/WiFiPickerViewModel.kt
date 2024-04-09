package com.dz.bmstu_trade.ui.main.connect.wifi_picker

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import com.dz.bmstu_trade.domain.interactor.GetWiFiInteractor
import com.dz.bmstu_trade.domain.interactor.GetWiFiInteractorImpl
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
class WiFiPickerViewModel: ViewModel() {
    private val getWiFiInteractor: GetWiFiInteractor = GetWiFiInteractorImpl()
    private val wifiManager: WifiManager = getWiFiInteractor.getWiFiManager()

    private val _requiredPermissions = MutableStateFlow<Array<String>>(emptyArray())
    val requiredPermissions: StateFlow<Array<String>> = _requiredPermissions

    private val _permissionsGranted = MutableStateFlow(false)
    val permissionsGranted: StateFlow<Boolean> = _permissionsGranted

    private val _networks = MutableStateFlow<ImmutableList<ScanResult>>(ImmutableList.of())
    val networks: StateFlow<ImmutableList<ScanResult>> = _networks

    private val _wiFiIsEnabled = MutableStateFlow(false)
    val wiFiIsEnabled: StateFlow<Boolean> = _wiFiIsEnabled

    private val eventChanel = Channel<WiFiPickerEvent>()
    val eventsFlow = eventChanel.receiveAsFlow()

    init {
        refreshRequiredPermissions()
        GlobalScope.launch {
            checkWifiEnabled()
        }
    }

    private fun checkWifiEnabled() {
        this._wiFiIsEnabled.value = wifiManager.isWifiEnabled
    }

    fun refreshRequiredPermissions() {
        _requiredPermissions.value = getWiFiInteractor.getRequiredPermissions()
    }

    suspend fun onPermissionsResult(result: Map<String, Boolean>) {
        if (
            result.all { (_, value) -> value }
            && result.isNotEmpty()
            ) {
            _permissionsGranted.value = true
            getWiFiInteractor.subscribeToWiFiList(
                onUpdate = { it ->
                    _networks.value = ImmutableList.copyOf(
                        it.sortedByDescending { it.level }
                    )
                },
                onFailure = {

                }
            )
        }
        else {
            _permissionsGranted.value = false
            viewModelScope.launch {
                eventChanel.send(WiFiPickerEvent.ShowPermissionsAlertDialog())
            }
        }
    }
}