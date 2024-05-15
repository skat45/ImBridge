package com.dz.bmstu_trade.ui.auth.vk

sealed interface VkAuthEvent {
    object StartAuth : VkAuthEvent
}