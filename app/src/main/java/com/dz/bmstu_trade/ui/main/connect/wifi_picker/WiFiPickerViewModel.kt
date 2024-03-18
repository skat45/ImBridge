package com.dz.bmstu_trade.ui.main.connect.wifi_picker

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.Manifest
import com.dz.bmstu_trade.data.model.WiFiNetwork
import com.dz.bmstu_trade.domain.interactor.GetWiFiList
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class WiFiPickerViewModel (private val context: Context): ViewModel() {
    private val _networks = MutableStateFlow(mutableListOf(WiFiNetwork("", false)))
    val networks: StateFlow<MutableList<WiFiNetwork>> = _networks

    init { // ACCESS_FINE_LOCATION

        val wiFiListGetter = GetWiFiList(context)
        GlobalScope.launch {
            while (true) {
                wiFiListGetter.getResult()
                _networks.value = wiFiListGetter.result
                delay(1000L)
            }
        }
    }

}