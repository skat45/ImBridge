package com.dz.bmstu_trade.ui.auth.vk

import androidx.annotation.StringRes
import com.dz.bmstu_trade.ui.auth.signin.PasswordFieldState

sealed class VkAuthState{
    object Loading : VkAuthState()
    data class Error(
        @StringRes
        val titleResId: Int,
        @StringRes
        val descriptionResId: Int
    ) : VkAuthState()
}