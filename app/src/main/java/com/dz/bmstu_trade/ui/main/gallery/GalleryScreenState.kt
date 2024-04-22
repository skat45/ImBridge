package com.dz.bmstu_trade.ui.main.gallery

import kotlinx.collections.immutable.ImmutableMap

data class GalleryScreenState (
    val selectedTab: Tab,
    val tabsState:ImmutableMap<Tab, GalleryTabState>
)

