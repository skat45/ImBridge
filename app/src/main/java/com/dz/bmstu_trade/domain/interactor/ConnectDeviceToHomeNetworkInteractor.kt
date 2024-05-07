package com.dz.bmstu_trade.domain.interactor

import android.net.wifi.WifiManager

interface ConnectDeviceToHomeNetworkInteractor {
    fun isWiFiTurnOn(): Boolean
    suspend fun isConnectedToDevice(): Boolean
    fun sendSSIDInfoJsonToDevice()
    fun getWiFiManager(): WifiManager
}