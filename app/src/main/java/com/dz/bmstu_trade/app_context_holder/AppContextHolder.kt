package com.dz.bmstu_trade.app_context_holder

import android.annotation.SuppressLint
import android.content.Context


@SuppressLint("StaticFieldLeak")
object AppContextHolder  {
    lateinit var context: Context
}
