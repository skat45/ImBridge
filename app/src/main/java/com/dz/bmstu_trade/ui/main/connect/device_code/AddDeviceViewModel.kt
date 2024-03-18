package com.dz.bmstu_trade.ui.main.connect.device_code


import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddDeviceViewModel : ViewModel() {

    private val _code = MutableStateFlow(TextFieldState(""))
    val code: StateFlow<TextFieldState> = _code

    fun onCodeUpdated(code: String) {
        this._code.value = TextFieldState(
            value = code,
            error = when {
                code.length > 4 ->
                    TextFieldState.Error.TOO_LARGE
                code.length < 4 ->
                    TextFieldState.Error.TOO_SHORT
                else -> null
            },
        )
    }
}

data class TextFieldState(
    val value: String = "",
    val error: Error? = Error.TOO_SHORT,
) {
    enum class Error(
        @StringRes
        val messageResId: Int,
    ) {
        TOO_LARGE(R.string.invalid_len_device_code_error),
        TOO_SHORT(R.string.invalid_len_device_code_error),
    }
}


