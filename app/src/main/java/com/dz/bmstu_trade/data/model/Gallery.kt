package com.dz.bmstu_trade.data.model

import com.dz.bmstu_trade.ui.main.canvas.Picture


data class ImageCard(
    val id: Int? = null,
    val image: Picture,
    val title: String = "",
    val isLiked: Boolean = false,
)
