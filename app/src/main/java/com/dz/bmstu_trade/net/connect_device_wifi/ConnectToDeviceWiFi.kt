package com.dz.bmstu_trade.net.connect_device_wifi

import android.Manifest.permission.ACCESS_FINE_LOCATION
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
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import com.dz.bmstu_trade.ui.main.connect.device_code.AddDeviceViewModel

fun connectToDeviceWiFi(
    codeViewModel: AddDeviceViewModel,
    onConnected: () -> Unit
) {
    val wifiManager = AppContextHolder.context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val ssid = "IMBRIDGE-" + codeViewModel.code.value.value
    val password = "012345666"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Log.d("connectToDeviceWiFi", "WiFi connection new function started")
        val specifier = WifiNetworkSpecifier.Builder()
            .setSsid(ssid)
            .setWpa2Passphrase(password)
            .build()

        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .setNetworkSpecifier(specifier)
            .build()

        val connectivityManager = AppContextHolder.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onConnected()
            }
        }
        connectivityManager.requestNetwork(request, networkCallback)
    }
    else {
        Log.d("connectToDeviceWiFi", "WiFi connection old function started")
        if(isConnectedTo(ssid, wifiManager)){ //see if we are already connected to the given ssid
            onConnected()
        }

        var wifiConfig = getWiFiConfig(ssid, wifiManager)
        if(wifiConfig == null){ //if the given ssid is not present in the WiFiConfig, create a config for it
            createWPAProfile(ssid, password, wifiManager)
            wifiConfig = getWiFiConfig(ssid, wifiManager)
        }

        wifiManager.disconnect()
        if (wifiConfig != null) {
            wifiManager.enableNetwork(wifiConfig.networkId,true)
            wifiManager.reconnect()
        }
        else {
            TODO()
        }

        if(isConnectedTo(ssid, wifiManager)) {
            onConnected()
        }
    }
}


fun getWiFiConfig(ssid: String, wifiManager: WifiManager): WifiConfiguration? {
    val wifiList= if (ActivityCompat.checkSelfPermission(
            AppContextHolder.context,
            ACCESS_FINE_LOCATION
        ) == PERMISSION_GRANTED
    ) {
        wifiManager.configuredNetworks
    } else {

        emptyList<WifiConfiguration>()
    }

    for (item in wifiList){
        if(item.SSID != null && item.SSID == "\"" + ssid + "\""){
            return item
        }
    }
    return null
}

fun isConnectedTo(ssid: String, wifiManager: WifiManager):Boolean{
    return wifiManager.connectionInfo.ssid == "\"" + ssid + "\""
}

fun createWPAProfile(ssid: String,pass: String, wifiManager: WifiManager){
    val conf = WifiConfiguration()
    conf.SSID = "\"" + ssid + "\""
    conf.preSharedKey = "\"" + pass + "\""
    wifiManager.addNetwork(conf)
}