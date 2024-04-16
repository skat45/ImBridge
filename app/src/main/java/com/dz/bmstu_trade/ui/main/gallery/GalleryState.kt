package com.dz.bmstu_trade.ui.main.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dz.bmstu_trade.data.model.ImageCard
import com.dz.bmstu_trade.data.model.galleryModel.ImageEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow

data class GalleryState(
    val query: String = String(),
    val imageCards: LiveData<List<ImageEntity>>
)