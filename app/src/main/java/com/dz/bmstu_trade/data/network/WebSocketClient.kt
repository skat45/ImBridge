package com.dz.bmstu_trade.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSocketException
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.wss
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText

class WebSocketClient(private val url: String) {
    private val client = HttpClient {
        install(WebSockets)
    }

    private var session: WebSocketSession? = null

    suspend fun connect(listener: WebSocketListener) {
        try {
            client.wss(url) {
                this@WebSocketClient.session = this
                listener.onConnected()

                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        listener.onMessage(frame.readText())
                    }
                }
            }
        } catch (exception: WebSocketException) {
            listener.onConnectionError()
        }
    }

    suspend fun send(message: String) {
        session?.outgoing?.send(
            Frame.Text(message)
        )
    }

    fun disconnect() {
        client.close()
    }
}