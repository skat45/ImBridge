package com.dz.bmstu_trade.domain.interactor

import com.dz.bmstu_trade.data.network.WebSocketClient
import com.dz.bmstu_trade.data.network.WebSocketListener
import javax.inject.Inject

class DeviceStateInteractorImpl @Inject constructor() : DeviceStateInteractor {
    override suspend fun connectToDevice(listener: WebSocketListener) {
        WebSocketClient.connect(listener)
    }

    override suspend fun sendCommandToDevice(command: String) {
        WebSocketClient.send(command)
    }

    override fun disconnectDevice() {
        WebSocketClient.disconnect()
    }
}