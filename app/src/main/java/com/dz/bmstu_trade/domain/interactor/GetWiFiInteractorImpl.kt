package com.dz.bmstu_trade.domain.interactor

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.dz.bmstu_trade.app_context_holder.AppContextHolder.context
import com.dz.bmstu_trade.net.wifi.wifiManager

class GetWiFiInteractorImpl: GetWiFiInteractor {

    private var wifiScanReceiver: BroadcastReceiver? = null

    override fun getRequiredPermissions(): Array<String> {
        return arrayOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION,
        )
    }

    override suspend fun subscribeToWiFiList(
        onUpdate: (List<ScanResult>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        if (checkSelfPermission(context, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            // Способ помечен как устаревший, но гугловские ничего нового пока не выкатили
            wifiManager.startScan()
        }
        if (wifiScanReceiver != null) {
            unsubscribeFromWiFiList()
        }
        wifiScanReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                if (success) {
                    if (checkSelfPermission(context, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
                        onUpdate(wifiManager.scanResults)
                    }
                }
                else {
                    onFailure(Throwable())
                }
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(wifiScanReceiver, intentFilter)
    }

    override fun unsubscribeFromWiFiList() {
        context.unregisterReceiver(wifiScanReceiver)
    }

}