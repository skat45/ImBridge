package com.dz.bmstu_trade.addDeviceViewModels


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddDeviceViewModel : ViewModel() {

    private val _code = MutableStateFlow(TextFieldState(""))
    val code: StateFlow<TextFieldState> = _code

    fun onCodeUpdated(code: String) {
        this._code.value = TextFieldState(
            value = code,
            errorCode = if (code.length > 4) {
                1
            } else {
                null
            },
        )
    }
}

data class TextFieldState(
    val value: String,
    val errorCode: Int? = null,
)


