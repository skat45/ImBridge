package com.dz.bmstu_trade.domain.interactor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.TransportInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import com.dz.bmstu_trade.retrofit.TestDeviceRequestApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ConnectDeviceToHomeNetworkInteractorImpl: ConnectDeviceToHomeNetworkInteractor {
    val wifiManager = AppContextHolder.getContext()?.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val retrofit = Retrofit.Builder()
        .baseUrl("imbridge_device.local")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val testDeviceRequestApi = retrofit.create(TestDeviceRequestApi::class.java)

    override fun isWiFiTurnOn(): Boolean {
        return wifiManager.isWifiEnabled
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun isConnectedToDevice(): Boolean {
        var isConnected = false

        GlobalScope.launch {
            val testResponce = testDeviceRequestApi.getTestResponse()
            when (testResponce.error) {
                0 -> isConnected = true
            }
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