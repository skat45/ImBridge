package com.dz.bmstu_trade.domain.interactor

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import com.dz.bmstu_trade.data.model.DeviceAnswer
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConnectDeviceToHomeNetworkInteractorImpl: ConnectDeviceToHomeNetworkInteractor {
    val wifiManager = AppContextHolder.getContext()?.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val client = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 30000
        }
    }
    private val testURL = "http://imbridge-device.local/send-wifi-info"
    private var deviceCode: String? = null

    override fun isWiFiTurnOn(): Boolean {
        return wifiManager.isWifiEnabled
    }

    override suspend fun connectToDevice(ssid: String, password: String): Boolean {
        var isConnected = false

        GlobalScope.launch {
            try {
                val response: HttpResponse = client.post(testURL) {
                    contentType(ContentType.Application.Json)
                    setBody("{'SSID': '$ssid', 'password': '$password'}")
                }
                val strJson: String = response.bodyAsText()
                val deviceAnswer = parseJson(strJson)
                Log.d("isConnectedToDevice", deviceAnswer.device_code)
                if (deviceAnswer.error == 0) {
                    isConnected = true
                    deviceCode = deviceAnswer.device_code
                }
            } catch (e: Exception) {
                Log.d("onAvailable", "error: $e")
            }
        }

        return isConnected
    }

    override fun getWiFiManager(): WifiManager {
        return wifiManager
    }

    fun parseJson(jsonString: String): DeviceAnswer {
        val gson = Gson()
        return gson.fromJson(jsonString, DeviceAnswer::class.java)
    }

    override fun getCode(): String? {
        return deviceCode
    }
}