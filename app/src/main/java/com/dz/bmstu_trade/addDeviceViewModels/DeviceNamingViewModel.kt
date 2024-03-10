package com.dz.bmstu_trade.addDeviceViewModels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NameDeviceViewModel : ViewModel() {

    private val _name = MutableStateFlow(NameFieldState(""))
    val name: StateFlow<NameFieldState> = _name

    fun onNameUpdated(name: String) {
        this._name.value = NameFieldState(
            value = name,
            error = when {
                name.length > 20 -> NameFieldState.Error.TOO_LONG
                else -> null
            },
        )
    }
}

data class NameFieldState(
    val value: String,
    val error: Error? = null,
) {
    enum class Error (
        @StringRes
        val messageResId: Int,
    ) {
        TOO_LONG(R.string.too_long_device_name_error)
    }
}