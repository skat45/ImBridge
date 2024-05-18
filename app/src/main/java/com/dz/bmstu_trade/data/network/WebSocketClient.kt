package com.dz.bmstu_trade.data.network

import android.util.Log
import com.dz.bmstu_trade.data.model.DeviceState
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSocketException
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.wss
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object WebSocketClient {
    val url: String = "ws://s256850.hostiman.com:8000/ws/device"
    private val client = HttpClient {
        install(WebSockets)
    }

    val json = Json {
        ignoreUnknownKeys = true
    }

    private var session: WebSocketSession? = null

    suspend fun connect(listener: WebSocketListener, code: String) {
        try {
            client.wss("$url/$code/") {
                this@WebSocketClient.session = this
                val initialStateFrame = incoming.receive() as? Frame.Text
                if (initialStateFrame != null) {
                    val initialState = json.decodeFromString<DeviceState>(
                        initialStateFrame.readText()
                    )
                    listener.onConnected(initialState)
                }

                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val state = json.decodeFromString<DeviceState>(frame.readText())
                        listener.onMessage(state)
                    }
                }
            }
        } catch (exception: WebSocketException) {
            Log.d("error", exception.toString())
            listener.onConnectionError()
        } catch (e: Exception) {
            Log.d("error", e.toString())
            listener.onConnectionError()
        }
    }

    suspend fun send(deviceState: DeviceState) {
        val jsonMessage = json.encodeToString(deviceState)
        session?.outgoing?.send(
            Frame.Text(jsonMessage)
        )
    }

    fun disconnect() {
        client.close()
    }
}