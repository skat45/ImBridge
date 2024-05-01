package com.dz.bmstu_trade.domain.interactor

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext

class ConnectToWifiInteractorImpl @AssistedInject constructor(
    @Assisted override val code: String,
    @ApplicationContext val context: Context
) : ConnectToWifiInteractor {

    val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val ssid = "IMBRIDGE-$code"
    private val password = "012345666"

    override fun connect(
        onConnected: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            connectMoreOrEqualsQAndroids(onConnected)
        } else {
            connectLessQAndroids(onConnected)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun connectMoreOrEqualsQAndroids(onConnected: () -> Unit) {
        Log.d("connectToDeviceWiFi", "WiFi connection new function started")
        val specifier = WifiNetworkSpecifier.Builder()
            .setSsid(ssid)
            .setWpa2Passphrase(password)
            .build()

        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .setNetworkSpecifier(specifier)
            .build()

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onConnected()
            }
        }
        connectivityManager.requestNetwork(request, networkCallback)
    }

    private fun connectLessQAndroids(onConnected: () -> Unit) {
        Log.d("connectToDeviceWiFi", "WiFi connection old function started")
        if (isConnectedTo(ssid, wifiManager)) { //see if we are already connected to the given ssid
            onConnected()
        }

        var wifiConfig = getWiFiConfig(ssid, wifiManager)
        if (wifiConfig == null) { //if the given ssid is not present in the WiFiConfig, create a config for it
            createWPAProfile(ssid, password, wifiManager)
            wifiConfig = getWiFiConfig(ssid, wifiManager)
        }

        wifiManager.disconnect()
        if (wifiConfig != null) {
            wifiManager.enableNetwork(wifiConfig.networkId, true)
            wifiManager.reconnect()
        } else {
            TODO()
        }

        if (isConnectedTo(ssid, wifiManager)) {
            onConnected()
        }
    }

    private fun getWiFiConfig(ssid: String, wifiManager: WifiManager): WifiConfiguration? {
        val wifiList = if (context.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PermissionChecker.PERMISSION_GRANTED
        ) {
            wifiManager.configuredNetworks
        } else {

            emptyList<WifiConfiguration>()
        }

        for (item in wifiList) {
            if (item.SSID != null && item.SSID == "\"" + ssid + "\"") {
                return item
            }
        }
        return null
    }

    private fun isConnectedTo(ssid: String, wifiManager: WifiManager): Boolean {
        return wifiManager.connectionInfo.ssid == "\"" + ssid + "\""
    }

    private fun createWPAProfile(ssid: String, pass: String, wifiManager: WifiManager) {
        val conf = WifiConfiguration()
        conf.SSID = "\"" + ssid + "\""
        conf.preSharedKey = "\"" + pass + "\""
        wifiManager.addNetwork(conf)
    }

    override fun checkWifiEnabled(): Boolean {
        return wifiManager.isWifiEnabled
    }
}

@AssistedFactory
interface ConnectToWifiInteractorFactory {
    fun create(code: String): ConnectToWifiInteractorImpl
}