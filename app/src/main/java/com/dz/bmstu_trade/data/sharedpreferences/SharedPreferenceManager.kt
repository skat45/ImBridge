package com.dz.bmstu_trade.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "device_prefs"
        private const val DEVICE_CODE_KEY = "device_code"
    }

    fun saveDeviceCode(deviceCode: String) {
        val editor = sharedPreferences.edit()
        editor.putString(DEVICE_CODE_KEY, deviceCode)
        editor.apply()
    }

    fun getDeviceCode(): String? {
        return sharedPreferences.getString(DEVICE_CODE_KEY, null)
    }

    fun clearDeviceCode() {
        val editor = sharedPreferences.edit()
        editor.remove(DEVICE_CODE_KEY)
        editor.apply()
    }
}
