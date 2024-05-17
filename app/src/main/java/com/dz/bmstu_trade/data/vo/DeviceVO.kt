package com.dz.bmstu_trade.data.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceVO(
    val name: String,
    @SerialName("is_connected") val isConnected: Boolean,
    @SerialName("device_id") val code: String
)
