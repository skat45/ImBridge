package com.dz.bmstu_trade.ui.main.connect.net_errors

import android.widget.Toast
import com.dz.bmstu_trade.app_context_holder.AppContextHolder

fun invalidNetworkToast() {
    Toast
        .makeText(
            AppContextHolder.getContext(),
            "Вы не можете выбрать эту сеть",
            Toast.LENGTH_SHORT
        )
        .show()
}