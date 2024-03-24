package com.dz.bmstu_trade

import android.app.Application

class IMApp: Application() {
    override fun onCreate() {
        super.onCreate()
        ContextHolder.appContext = this
    }
}