package com.dz.bmstu_trade.ui.auth.signup

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignUpViewModel : ViewModel() {

    private val _enterPassword = MutableStateFlow(PasswordFieldState(""))
    val enterPassword: StateFlow<PasswordFieldState> = _enterPassword

    private val _repeatPassword = MutableStateFlow(PasswordFieldState(""))
    val repeatPassword: StateFlow<PasswordFieldState> = _repeatPassword

    fun onPasswordUpdated(enterPassword: String) {
        this._enterPassword.value = PasswordFieldState(
            value = enterPassword,
            error = when {
                enterPassword.length > 63 ->
                    PasswordFieldState.Error.TOO_LONG
                enterPassword.length < 8 ->
                    PasswordFieldState.Error.TOO_SHORT
                else -> null
            },
        )
        checkPasswordsMatch()
    }

    fun onConfirmPasswordUpdated(repeatPassword: String) {
        this._repeatPassword.value = PasswordFieldState(
            value = repeatPassword,
            error = when {
                repeatPassword.length > 63 ->
                    PasswordFieldState.Error.TOO_LONG
                repeatPassword.length < 8 ->
                    PasswordFieldState.Error.TOO_SHORT
                else -> null
            },
        )
        checkPasswordsMatch()
    }

    private fun checkPasswordsMatch() {
        val enterPasswordValue = _enterPassword.value.value
        val repeatPasswordValue = _repeatPassword.value.value

        if (enterPasswordValue != repeatPasswordValue) {
            _repeatPassword.value = PasswordFieldState(
                value = repeatPasswordValue,
                error = PasswordFieldState.Error.PASSWORDS_MISMATCH
            )
        }
    }
}

data class PasswordFieldState(
    val value: String,
    val error: Error? = Error.TOO_SHORT,
) {
    enum class Error(
        @StringRes
        val messageResId: Int,
    ) {
        TOO_LONG(R.string.too_long_wi_fi_password),
        TOO_SHORT(R.string.too_short_wi_fi_password),
        PASSWORDS_MISMATCH(R.string.passwords_mismatch),
    }
}
