package com.dz.bmstu_trade.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceState(
    val name: String,
    val brightness: Int,
    val image: List<List<Int>>,
    @SerialName("is_on") val isOn: Boolean
)