package com.dz.bmstu_trade.domain.interactor

import com.dz.bmstu_trade.data.network.WebSocketListener

interface DeviceStateInteractor {
    suspend fun connectToDevice(listener: WebSocketListener)
    suspend fun sendCommandToDevice(command: String)
    fun disconnectDevice()
}