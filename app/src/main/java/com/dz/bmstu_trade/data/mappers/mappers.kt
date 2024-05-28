package com.dz.bmstu_trade.data.mappers

import androidx.compose.ui.graphics.Color
import com.dz.bmstu_trade.data.model.DeviceState
import com.dz.bmstu_trade.data.model.ImageCard
import com.dz.bmstu_trade.data.model.gallery.ImageEntity
import com.dz.bmstu_trade.ui.main.canvas.Picture

fun DeviceState.toPicture() = ImageMapper.listToPicture(this.image)

fun ImageCard.toImageEntity(isLiked: Boolean): ImageEntity {
    return ImageEntity(
        id = this.id,
        title = this.title,
        isLiked = isLiked,
        image = this.image.getArgbList()
    )
}

fun ImageEntity.toImageCard(): ImageCard {
    return ImageCard(
        id = this.id,
        title = this.title,
        isLiked = this.isLiked,
        image = ImageMapper.listToPicture(this.image)
    )
}

fun ImageEntity.toPicture() = ImageMapper.listToPicture(this.image)

object ImageMapper {
    fun listToPicture(pic: List<List<Int>>) = Picture(
        pic.size,
        pic.first().size,
        pic.flatten().map { hexValue -> Color(hexValue) }
    )
}

fun Picture.toImageEntity(title: String) = ImageEntity(
    title = title,
    image = this.getArgbList(),
)