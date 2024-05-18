package com.dz.bmstu_trade.data.mappers

import androidx.compose.ui.graphics.Color
import com.dz.bmstu_trade.data.model.DeviceState
import com.dz.bmstu_trade.ui.main.canvas.Picture

fun DeviceState.toPicture() = ImageMapper.listToPicture(this.image)

object ImageMapper {
    fun listToPicture(pic: List<List<Int>>) = Picture(
        pic.size,
        pic.first().size,
        pic.flatten().map { hexValue -> Color(hexValue) }
    )
}