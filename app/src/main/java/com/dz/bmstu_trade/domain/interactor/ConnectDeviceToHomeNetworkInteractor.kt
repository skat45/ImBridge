package com.dz.bmstu_trade.domain.interactor

import android.net.wifi.WifiManager

interface ConnectDeviceToHomeNetworkInteractor {
    fun isWiFiTurnOn(): Boolean
    fun isConnectedToDevice(): Boolean
    fun sendSSIDInfoJsonToDevice()
    fun getWiFiManager(): WifiManager
}