package com.dz.bmstu_trade.data.network

import com.dz.bmstu_trade.data.model.DeviceState

interface WebSocketListener {
    fun onConnected(deviceState: DeviceState)
    fun onConnectionError()
    fun onMessage(deviceState: DeviceState)
    fun onDisconnected()
}