package com.dz.bmstu_trade.ui.main.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private  val themeState: MutableStateFlow<Boolean>)  : ViewModel() {
    private val _email = MutableStateFlow("first@inbox.ru")
   // private val _switch = MutableStateFlow(false)


    val email: StateFlow<String> = _email
    val switch: StateFlow<Boolean> = themeState

    fun onSwitchChanged() {
        this.themeState.value = !this.themeState.value
    }

}


