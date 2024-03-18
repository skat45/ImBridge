package com.dz.bmstu_trade.domain.interactor

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.util.Log
import com.dz.bmstu_trade.data.model.WiFiNetwork

// Да, способ старый, но гугловские ничего пока не предложили взамен
@SuppressLint("UnspecifiedRegisterReceiverFlag")
class GetWiFiList(context: Context) {
    var result: MutableList<WiFiNetwork> = mutableListOf()

    private val _wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val wifiScanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                scanSuccess()
            } else {
                scanFailure()
            }
        }
    }

    val intentFilter = IntentFilter()
    init {
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(wifiScanReceiver, intentFilter)
    }


    @SuppressLint("MissingPermission")
    fun getResult() {
        _wifiManager.startScan()
        val results = _wifiManager.scanResults
        Log.d("WIFINAME", results.toString())
        for (i in results) {
            this.result.add(WiFiNetwork(ssid = i.SSID, protected = i.isPasspointNetwork))
        }
    }

    @SuppressLint("MissingPermission")
    private fun scanSuccess() {
        val results = _wifiManager.scanResults
    }

    @SuppressLint("MissingPermission")
    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = _wifiManager.scanResults
    }
}