package com.dz.bmstu_trade.addDeviceViewModels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WiFiPasswordInputViewModel : ViewModel() {
    private val ssid = "Proizvodstvo" // TODO: сдесь надо забирать ssid из model
    private val _state = MutableStateFlow(PasswordFieldState("", false, ssid))
    val state: StateFlow<PasswordFieldState> = _state

    fun onPasswordUpdated(newPassword: String) {
        this._state.value =  PasswordFieldState(
            password = newPassword,
            shown = _state.value.shown,
            ssid = ssid,
            error = when {
                newPassword.length > 63 ->
                    PasswordFieldState.Error.TOO_LONG
                newPassword.length < 8 ->
                    PasswordFieldState.Error.TOO_SHORT
                else -> null
            },
        )
    }

    fun onShownUpdated() {
        this._state.value =  PasswordFieldState(
            password = _state.value.password,
            shown = !_state.value.shown,
            ssid = ssid,
            error =  _state.value.error
        )
    }
}

data class PasswordFieldState(
    val password: String,
    val shown: Boolean = false,
    val ssid: String,
    val error: PasswordFieldState.Error? = Error.TOO_SHORT,
) {
    enum class Error(
        @StringRes
        val messageResId: Int,
    ) {
        TOO_LONG(R.string.too_long_wi_fi_password),
        TOO_SHORT(R.string.too_short_wi_fi_password),
    }
}