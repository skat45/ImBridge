package com.dz.bmstu_trade.domain.interactor

import com.dz.bmstu_trade.data.model.WiFiNetwork

class GetWiFiInteractorImpl: GetWiFiInteractor {
    override fun getRequiredPermissions(): Array<String> {
        return arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun subscribeToWiFiList(
        onUpdate: (List<WiFiNetwork>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun unsubscribeFromWiFiList() {
        TODO("Not yet implemented")
    }

}