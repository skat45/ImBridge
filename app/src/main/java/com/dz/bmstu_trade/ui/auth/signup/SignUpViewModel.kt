package com.dz.bmstu_trade.ui.auth.signup

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignUpViewModel : ViewModel() {

    private val _password1 = MutableStateFlow(PasswordFieldState(""))
    val password1: StateFlow<PasswordFieldState> = _password1

    private val _password2 = MutableStateFlow(PasswordFieldState(""))
    val password2: StateFlow<PasswordFieldState> = _password2

    fun onPasswordUpdated(password1: String) {
        this._password1.value = PasswordFieldState(
            value = password1,
            error = when {
                password1.length > 63 ->
                    PasswordFieldState.Error.TOO_LONG
                password1.length < 8 ->
                    PasswordFieldState.Error.TOO_SHORT
                else -> null
            },
        )

        // Check if passwords match
        checkPasswordsMatch()
    }

    fun onConfirmPasswordUpdated(password2: String) {
        this._password2.value = PasswordFieldState(
            value = password2,
            error = when {
                password2.length > 63 ->
                    PasswordFieldState.Error.TOO_LONG
                password2.length < 8 ->
                    PasswordFieldState.Error.TOO_SHORT
                else -> null
            },
        )

        // Check if passwords match
        checkPasswordsMatch()
    }

    private fun checkPasswordsMatch() {
        val password1Value = _password1.value.value
        val password2Value = _password2.value.value

        if (password1Value != password2Value) {
            _password2.value = PasswordFieldState(
                value = password2Value,
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
