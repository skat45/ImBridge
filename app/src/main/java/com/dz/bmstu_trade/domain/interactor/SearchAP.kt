package com.dz.bmstu_trade.domain.interactor

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.dz.bmstu_trade.data.model.WiFiNetwork

// Да, способ помечен как старый, но гугловские ничего нового пока не предложили
@SuppressLint("UnspecifiedRegisterReceiverFlag")
class GetWiFiListInteractor(context: Context, appContext: Context) {
    var result: MutableList<WiFiNetwork> = mutableListOf()
    private val context = context
    private val appContext = appContext

    private val _wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val wifiScanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                scanSuccess(this@GetWiFiListInteractor.appContext)
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



    fun getResult() {
        if (ContextCompat.checkSelfPermission(context, "ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED) {
            _wifiManager.startScan()
            val results = _wifiManager.scanResults
            Log.d("WIFINAME", results.toString())
            for (i in results) {
                this.result.add(WiFiNetwork(ssid = i.SSID, protected = i.isPasspointNetwork))
            }
        }
        else {
            when {
                ContextCompat.checkSelfPermission(context, "ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED -> {
                    _wifiManager.startScan()
                    val results = _wifiManager.scanResults
                    Log.d("WIFINAME", results.toString())
                    for (i in results) {
                        this.result.add(WiFiNetwork(ssid = i.SSID, protected = i.isPasspointNetwork))
                    }
                }
                shouldShowRequestPermissionRationale(appContext as Activity, "ACCESS_FINE_LOCATION") -> {
                    Log.d("WIFINAME", "shouldShowRequest")
                    showRequestDialog("ACCESS_FINE_LOCATION",  101)
                }
                else -> {
                    Log.d("WIFINAME", "getRequestPermission")
//                    ActivityCompat.requestPermissions(_context as Activity,
//                        arrayOf("ACCESS_FINE_LOCATION"), 101)
                    showRequestDialog("ACCESS_FINE_LOCATION",  101)
                }
            }
        }
    }

    private fun scanSuccess(appContext: Context) {
        when {
            ContextCompat.checkSelfPermission(context, "ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED -> {
                _wifiManager.startScan()
                val results = _wifiManager.scanResults
                Log.d("WIFINAME", results.toString())
                for (i in results) {
                    this.result.add(WiFiNetwork(ssid = i.SSID, protected = i.isPasspointNetwork))
                }
            }
            shouldShowRequestPermissionRationale(appContext as Activity, "ACCESS_FINE_LOCATION") -> {
                showRequestDialog("ACCESS_FINE_LOCATION",  101)
            }
            else -> {
                ActivityCompat.requestPermissions(context as Activity,
                    arrayOf("ACCESS_FINE_LOCATION"), 101)
            }
        }
    }

    private fun showRequestDialog(permission: String, requestCode: Int) {
        val builder = AlertDialog.Builder(context)

        builder.apply {
            setMessage("Нужен доступ к WiFi, чтобы просканировать окружающие Вас сети")
            setTitle("Необходим доступ")
            setPositiveButton("Разрешить") { dialog, which ->
                ActivityCompat.requestPermissions(appContext as Activity, arrayOf(permission), requestCode)
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun scanFailure() {
        Log.d("WIFINAME", "Scanning error")
    }
}