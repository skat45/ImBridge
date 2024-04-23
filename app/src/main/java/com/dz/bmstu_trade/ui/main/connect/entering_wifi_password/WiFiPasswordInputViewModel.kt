package com.dz.bmstu_trade.ui.main.connect.entering_wifi_password

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.R
import com.dz.bmstu_trade.domain.interactor.ConnectDeviceToHomeNetworkInteractor
import com.dz.bmstu_trade.domain.interactor.ConnectDeviceToHomeNetworkInteractorImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WiFiPasswordInputViewModel(
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val ssid = checkNotNull(savedStateHandle["ssid"])
    private val _state = MutableStateFlow(PasswordFieldState("", false, ssid))
    val state: StateFlow<PasswordFieldState> = _state

    val connectDeviceToNetworkInteractor: ConnectDeviceToHomeNetworkInteractor
        = ConnectDeviceToHomeNetworkInteractorImpl()

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

    fun onClickSendButton() {
        if (connectDeviceToNetworkInteractor.isConnectedToDevice()) {
            Log.d("onClickSendButton", "connected to device")
        }
    }
}

data class PasswordFieldState(
    val password: String,
    val shown: Boolean = false,
    val ssid: Any,
    val error: Error? = Error.TOO_SHORT,
    val connectionStatus: Status? = null
) {
    enum class Error(
        @StringRes
        val messageResId: Int,
    ) {
        TOO_LONG(R.string.too_long_wi_fi_password),
        TOO_SHORT(R.string.too_short_wi_fi_password),
    }
    enum class Status(
        val messageResId: Int,
    ) {
        CONNECTING(1),
        CONNECTED(2),
    }
}