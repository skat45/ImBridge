package com.dz.bmstu_trade.ui.auth.vk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.bmstu_trade.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class VkAuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<VkAuthState>(VkAuthState.Loading)
    val authState : StateFlow<VkAuthState> = _authState

    private val eventChanel = Channel<VkAuthEvent>()
    val eventsFlow = eventChanel.receiveAsFlow()

    init {
        viewModelScope.launch {
            eventChanel.send(VkAuthEvent.StartAuth)
        }
    }

    fun onFailure (error: Throwable) {
        _authState.value = VkAuthState.Error(
            titleResId = R.string.vk_auth_failed,
            descriptionResId = R.string.vk_auth_failed_subtitle
        )
    }

    fun onRetry() {
        _authState.value = VkAuthState.Loading
        viewModelScope.launch {
            eventChanel.send(VkAuthEvent.StartAuth)
        }
    }
}