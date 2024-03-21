package com.dz.bmstu_trade.data.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class ImageCard(
    val image: String = "",
    val title: String = "",
    val isLiked: Boolean = false,
)
data class GalleryState(
    val query: String = String(),
    val imageCards: ImmutableList<ImageCard> = persistentListOf()
)