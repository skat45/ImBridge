package com.dz.bmstu_trade.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TestDeviceAnswer(
    val error: Int,
    val deviceCode: String,
)
