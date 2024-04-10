package com.dz.bmstu_trade.app_context_holder

import android.annotation.SuppressLint
import android.content.Context
import java.lang.ref.WeakReference


object AppContextHolder  {
    private var contextRef: WeakReference<Context>? = null
    fun setContext(context: Context) {
        contextRef = WeakReference(context)
    }

    fun getContext(): Context? {
        return contextRef?.get()
    }
}
