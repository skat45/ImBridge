package com.dz.bmstu_trade.AddDevice

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShowWiFiViewModel : ViewModel() {
    val networks: StateFlow<List<WiFiNetwork>> = MutableStateFlow(listOf(
        WiFiNetwork("TP-Link_14F9", true),
        WiFiNetwork("bmstu_wifi", true),
        WiFiNetwork("POCO M3 Pro 5G (Екатерина)", true),
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

data class WiFiNetwork (
    val ssid: String,
    val protected: Boolean
)