package com.dz.bmstu_trade.ui.main.connect.entering_wifi_password

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.bmstu_trade.R
import com.dz.bmstu_trade.domain.interactor.ConnectDeviceToHomeNetworkInteractor
import com.dz.bmstu_trade.domain.interactor.ConnectDeviceToHomeNetworkInteractorImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WiFiPasswordInputViewModel(
    val ssid: String,
): ViewModel() {
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
            connectionStatus = _state.value.connectionStatus,
        )
    }

    fun onShownUpdated() {
        this._state.value =  PasswordFieldState(
            password = _state.value.password,
            shown = !_state.value.shown,
            ssid = ssid,
            error =  _state.value.error,
            connectionStatus = _state.value.connectionStatus,
        )
    }

    fun onClickSendButton() {
        viewModelScope.launch {
            if (connectDeviceToNetworkInteractor.connectToDevice(
                ssid = ssid, password = _state.value.password
            )) {
                val deviceCode = connectDeviceToNetworkInteractor.getCode()
                if (!deviceCode.isNullOrEmpty()) {
                    onConnected()
                }
            }
        }
    }

    private fun onConnected() {
        // TODO Сюда можно пихать логику того, что должно произойти после подключения девайса
        this._state.value =  PasswordFieldState(
            password = _state.value.password,
            shown = _state.value.shown,
            ssid = ssid,
            error =  _state.value.error,
            connectionStatus = PasswordFieldState.Status.CONNECTED,
        )
    }
}

data class PasswordFieldState(
    val password: String,
    val shown: Boolean = false,
    val ssid: Any,
    val error: Error? = Error.TOO_SHORT,
    val connectionStatus: Status = Status.CONNECTING
) {
    enum class Error(
        @StringRes
        val messageResId: Int,
    ) {
        TOO_LONG(R.string.too_long_wi_fi_password),
        TOO_SHORT(R.string.too_short_wi_fi_password),
    }
    enum class Status(
        val id: Int,
    ) {
        CONNECTING(1),
        CONNECTED(2),
    }
}
