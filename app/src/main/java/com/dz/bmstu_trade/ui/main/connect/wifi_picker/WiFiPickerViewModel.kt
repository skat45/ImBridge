package com.dz.bmstu_trade.ui.main.connect.wifi_picker

import androidx.compose.material3.AlertDialog
import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.data.model.WiFiNetwork
import com.dz.bmstu_trade.domain.interactor.GetWiFiInteractor
import com.dz.bmstu_trade.domain.interactor.GetWiFiInteractorImpl
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class WiFiPickerViewModel (): ViewModel() {
    private val getWiFiInteractor: GetWiFiInteractor = GetWiFiInteractorImpl()

    private val _requiredPermissions = MutableStateFlow<Array<String>>(emptyArray())
    val requiredPermissions: StateFlow<Array<String>> = _requiredPermissions

    private val _networks = MutableStateFlow(mutableListOf(WiFiNetwork("", false)))
    val networks: StateFlow<MutableList<WiFiNetwork>> = _networks

    private val eventChanel = Channel<WiFiPickerEvent>()
    val eventsFlow = eventChanel.receiveAsFlow()

    init {
        _requiredPermissions.value = getWiFiInteractor.getRequiredPermissions()
    }

    fun onPermissionsResult(result: Map<String, Boolean>) {
        if (result.all { (_, value) -> value }) {

        }
        else {
            GlobalScope.launch {
                eventChanel.send(WiFiPickerEvent.ShowPermissionsAlertDialog())
            }
        }
    }
}