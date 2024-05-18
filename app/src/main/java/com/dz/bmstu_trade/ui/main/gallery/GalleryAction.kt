package com.dz.bmstu_trade.ui.main.gallery

import com.dz.bmstu_trade.data.model.ImageCard

sealed class GalleryAction {
    data class SearchedChanged(val query: String, val selectedTab: Tab) : GalleryAction()
    data class LikeStateChanged(val isLiked: Boolean, val index: Int, val selectedTab: Tab) :
        GalleryAction()

    data class SearchedCleared(val selectedTab: Tab) : GalleryAction()
    data class DeleteImageCard(val index: Int) : GalleryAction()
}