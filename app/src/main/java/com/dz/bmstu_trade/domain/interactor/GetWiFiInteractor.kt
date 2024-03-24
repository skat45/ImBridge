package com.dz.bmstu_trade.domain.interactor

import com.dz.bmstu_trade.data.model.WiFiNetwork

interface GetWiFiInteractor {
    fun getRequiredPermissions(): Array<String>

    fun subscribeToWiFiList(
        onUpdate: (List<WiFiNetwork>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun unsubscribeFromWiFiList()
}