package com.dz.bmstu_trade.domain.interactor

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.util.InternalAPI
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConnectDeviceToHomeNetworkInteractorImpl: ConnectDeviceToHomeNetworkInteractor {
    val wifiManager = AppContextHolder.getContext()?.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val client = HttpClient() {
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 30000
        }
    }
    private val testURL = "http://imbridge-device.local/test"

    override fun isWiFiTurnOn(): Boolean {
        return wifiManager.isWifiEnabled
    }

    @OptIn(InternalAPI::class)
    override suspend fun isConnectedToDevice(): Boolean {
        var isConnected = false

        try {
            val response: HttpResponse = client.post("http://imbridge-device.local/test")
            Log.d("isConnectedToDevice", response.content.toString())
        }
        catch (e: Exception) {
            // Обработка ошибки
            Log.e("isConnectedToDevice", "Error: ${e}")
        }
        return isConnected
    }

    override fun sendSSIDInfoJsonToDevice() {
        TODO("Not yet implemented")
    }

    override fun getWiFiManager(): WifiManager {
        return wifiManager
    }
}