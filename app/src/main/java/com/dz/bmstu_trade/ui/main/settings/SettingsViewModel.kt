package com.dz.bmstu_trade.ui.main.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {
    private val _email = MutableStateFlow("first@inbox.ru")
    private val _switch = MutableStateFlow(false)

    val email: StateFlow<String> = _email
    val switch: StateFlow<Boolean> = _switch

    fun onSwitchChanged(change: Boolean) {
        this._switch.value = change
    }

}


