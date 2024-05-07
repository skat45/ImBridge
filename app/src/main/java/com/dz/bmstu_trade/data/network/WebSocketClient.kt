package com.dz.bmstu_trade.data.network

import com.dz.bmstu_trade.data.model.DeviceState
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSocketException
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.wss
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object WebSocketClient {
    val url: String = ""
    private val client = HttpClient {
        install(WebSockets)
    }

    private var session: WebSocketSession? = null

    suspend fun connect(listener: WebSocketListener) {
        try {
            client.wss(url) {
                this@WebSocketClient.session = this
                val initialStateFrame = incoming.receive() as? Frame.Text
                if (initialStateFrame != null) {
                    val initialState = Json.decodeFromString<DeviceState>(initialStateFrame.readText())
                    listener.onConnected(initialState)
                }

                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val state = Json.decodeFromString<DeviceState>(frame.readText())
                        listener.onMessage(state)
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