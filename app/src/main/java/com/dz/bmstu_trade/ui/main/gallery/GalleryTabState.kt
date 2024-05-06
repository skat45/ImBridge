package com.dz.bmstu_trade.ui.main.gallery

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.dz.bmstu_trade.data.model.ImageCard
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class GalleryTabState(
    val query: String = String(),
    val imageCards: PersistentList<ImageCard> = persistentListOf(),
    val scrollState: MutableState<LazyGridState> = mutableStateOf( LazyGridState(0))
)