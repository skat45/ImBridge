package com.dz.bmstu_trade.ui.main.gallery

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.PersistentMap

data class GalleryScreenState (
    val selectedTab: MutableState<Tab> = mutableStateOf(Tab.MY_PICTURES) ,
    val tabsState:PersistentMap<Tab, GalleryTabState>
)

