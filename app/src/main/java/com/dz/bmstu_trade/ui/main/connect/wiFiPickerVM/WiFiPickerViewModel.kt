package com.dz.bmstu_trade.ui.main.connect.wiFiPickerVM

import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.data.model.WiFiNetwork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WiFiPickerViewModel : ViewModel() {
    val networks: StateFlow<List<WiFiNetwork>> = MutableStateFlow(listOf(
        WiFiNetwork("TP-Link_14F9", true),
        WiFiNetwork("bmstu_wifi", true),
        WiFiNetwork("POCO M3 Pro 5G (Екатерина Чижикова Олеговна)", true),
        WiFiNetwork("Mosscow_WiFi_Free", false),
        WiFiNetwork("bmstu_guest", true),
        WiFiNetwork("Proizvodstvo", true),
        WiFiNetwork("bmstu_staff", true),
        WiFiNetwork("iPhone (София)", false),
        WiFiNetwork("BRUSOK", true),
        WiFiNetwork("IU4-Student", true),
    ))

    // TODO Надо прифигаить обновление списка сетей вай фай
}