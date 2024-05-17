package com.dz.bmstu_trade.data.mappers

import androidx.compose.ui.graphics.Color
import com.dz.bmstu_trade.data.model.DeviceState
import com.dz.bmstu_trade.ui.main.canvas.Picture

fun DeviceState.toPicture() = Picture(
    this.image.size,
    this.image.first().size,
    this.image.flatten().map { hexValue -> Color(hexValue) }
)