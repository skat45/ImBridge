package com.dz.bmstu_trade.domain.interactor

import android.net.wifi.ScanResult
import android.net.wifi.WifiManager

interface GetWiFiInteractor {
    fun getRequiredPermissions(): Array<String>

    suspend fun subscribeToWiFiList(
        onUpdate: (List<ScanResult>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun unsubscribeFromWiFiList()
    fun getWiFiManager(): WifiManager
}