package com.dz.bmstu_trade.ui.main.gallery

sealed class GalleryAction {
    data class SearchedChanged(val query: String, val selectedTab: Tab) : GalleryAction()
    data class LikeStateChanged(val isLiked: Boolean, val index: Int, val selectedTab: Tab) :
        GalleryAction()

    data class SearchedCleared(val selectedTab: Tab) : GalleryAction()
}