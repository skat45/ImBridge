package com.dz.bmstu_trade.domain.interactor

interface ConnectToWifiInteractor {
    val code: String

    fun connect(onConnected: () -> Unit)

    fun checkWifiEnabled(): Boolean
}