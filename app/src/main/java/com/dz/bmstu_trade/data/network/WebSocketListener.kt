package com.dz.bmstu_trade.data.network

interface WebSocketListener {
    fun onConnected()
    fun onConnectionError()
    fun onMessage(message: String)
    fun onDisconnected()
}