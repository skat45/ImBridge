package com.dz.bmstu_trade.net.wifi

import android.content.Context
import android.net.wifi.WifiManager
import com.dz.bmstu_trade.app_context_holder.AppContextHolder

val wifiManager = AppContextHolder.context.getSystemService(Context.WIFI_SERVICE) as WifiManager