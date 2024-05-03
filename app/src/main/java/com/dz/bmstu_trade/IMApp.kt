package com.dz.bmstu_trade

import android.app.Application
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IMApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AppContextHolder.setContext(this)
    }
}