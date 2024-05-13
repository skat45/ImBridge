package com.dz.bmstu_trade.domain.interactor

import android.net.wifi.WifiManager

interface ConnectDeviceToHomeNetworkInteractor {
    fun isWiFiTurnOn(): Boolean
    suspend fun connectToDevice(ssid: String, password: String): Boolean
    fun getWiFiManager(): WifiManager
    fun getCode(): String?
}