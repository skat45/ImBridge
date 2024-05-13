package com.dz.bmstu_trade.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DeviceAnswer(
    val error: Int,
    val device_code: String,
)
