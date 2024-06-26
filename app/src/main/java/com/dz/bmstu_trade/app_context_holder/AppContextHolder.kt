package com.dz.bmstu_trade.app_context_holder

import android.content.Context
import java.lang.ref.WeakReference

@Deprecated(message = "Replaced by DI")
object AppContextHolder  {
    private var contextRef: WeakReference<Context>? = null
    fun setContext(context: Context) {
        contextRef = WeakReference(context)
    }

    fun getContext(): Context? {
        return contextRef?.get()
    }
}
