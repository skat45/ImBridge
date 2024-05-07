package com.dz.bmstu_trade.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DeviceState(
    val brightness: Int,
    val image: List<List<Int>>,
    val is_on: Boolean
)