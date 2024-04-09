package com.dz.bmstu_trade

import android.app.Application
import com.dz.bmstu_trade.app_context_holder.AppContextHolder

class IMApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AppContextHolder.context = this
    }
}