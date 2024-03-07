package com.dz.bmstu_trade.AddDevice

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NameDeviceViewModel : ViewModel() {

    private val _name = MutableStateFlow(NameFieldState(""))
    val name: StateFlow<NameFieldState> = _name

    fun onNameUpdated(name: String) {
        this._name.value = NameFieldState(
            value = name,
            error = if (name.length > 20) {
                "Длина имени не должна превышать 20 символов"
            } else {
                null
            },
        )
    }
}

data class NameFieldState(
    val value: String,
    val error: String? = null,
)