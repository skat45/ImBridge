package com.dz.bmstu_trade.ui.auth.signin

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignInViewModel : ViewModel() {

    private val _signin = MutableStateFlow(PasswordFieldState(""))
    val signin: StateFlow<PasswordFieldState> = _signin

    fun onPasswordUpdated(signin: String) {
        this._signin.value = PasswordFieldState(
            value = signin,
            error = when {
                signin.length > 63 ->
                    PasswordFieldState.Error.TOO_LONG
                signin.length < 8 ->
                    PasswordFieldState.Error.TOO_SHORT
                else -> null
            },
        )
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
    }
}


