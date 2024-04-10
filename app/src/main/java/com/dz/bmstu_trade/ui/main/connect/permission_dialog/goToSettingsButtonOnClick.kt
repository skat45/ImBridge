package com.dz.bmstu_trade.ui.main.connect.permission_dialog

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.dz.bmstu_trade.app_context_holder.AppContextHolder

fun GoToSettingsButtonOnClick() {
    AppContextHolder.getContext()?.startActivity(
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(
                Uri.fromParts(
                    "package",
                    AppContextHolder.getContext()?.packageName.toString(),
                    null
                )
            )
    )
}