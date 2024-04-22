package com.dz.bmstu_trade.ui.main.gallery

import com.dz.bmstu_trade.data.model.ImageCard
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class GalleryTabState(
    val query: String = String(),
    val imageCards: PersistentList<ImageCard> = persistentListOf()
)