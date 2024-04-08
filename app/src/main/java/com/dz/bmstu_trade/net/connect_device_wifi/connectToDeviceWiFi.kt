package com.dz.bmstu_trade.net.connect_device_wifi

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiNetworkSpecifier
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import com.dz.bmstu_trade.net.wifi.wifiManager
import com.dz.bmstu_trade.ui.main.connect.device_code.AddDeviceViewModel

fun connectToDeviceWiFi(
    codeViewModel: AddDeviceViewModel,
    onConnected: () -> Unit
) {
    val ssid = "IMBRIDGE-" + codeViewModel.code.value.value
    val password = "012345666"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Log.d("connectToDeviceWiFi", "android Q+ connect fun started")
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
        Log.d("connectToDeviceWiFi", "Old connect fun started")

        if(isConnectedTo(ssid)){ //see if we are already connected to the given ssid
            onConnected()
        }

        var wifiConfig = getWiFiConfig(ssid)
        if(wifiConfig == null){ //if the given ssid is not present in the WiFiConfig, create a config for it
            createWPAProfile(ssid, password)
            wifiConfig = getWiFiConfig(ssid)
        }

        wifiManager.disconnect()
        if (wifiConfig != null) {
            wifiManager.enableNetwork(wifiConfig.networkId,true)
            wifiManager.reconnect()
        }
        else {
            TODO()
        }

        if(isConnectedTo(ssid)) {
            onConnected()
        }
    }
}


fun getWiFiConfig(ssid: String): WifiConfiguration? {
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

fun isConnectedTo(ssid: String):Boolean{
    return wifiManager.connectionInfo.ssid == "\"" + ssid + "\""
}

fun createWPAProfile(ssid: String,pass: String){
    val conf = WifiConfiguration()
    conf.SSID = "\"" + ssid + "\""
    conf.preSharedKey = "\"" + pass + "\""
    wifiManager.addNetwork(conf)
}