package com.dz.bmstu_trade.domain.interactor

import com.dz.bmstu_trade.data.model.DeviceState
import com.dz.bmstu_trade.data.network.WebSocketListener
import com.dz.bmstu_trade.data.vo.DeviceVO

interface DeviceStateInteractor {
    suspend fun connectToDevice(listener: WebSocketListener):Boolean
    suspend fun sendToDevice(deviceState: DeviceState)
    fun changeDevice(code: String)
    fun disconnectDevice()

    suspend fun getDevices(): List<DeviceVO>
}