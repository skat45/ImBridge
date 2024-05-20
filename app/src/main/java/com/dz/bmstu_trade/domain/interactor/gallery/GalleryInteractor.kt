package com.dz.bmstu_trade.domain.interactor.gallery

import com.dz.bmstu_trade.data.model.ImageCard
import com.dz.bmstu_trade.data.model.gallery.ImageEntity
import kotlinx.coroutines.flow.Flow

interface GalleryInteractor {
    suspend fun insertImage(imageCard: ImageCard, isLikeChange: Boolean)
    suspend fun insertImageEntity(imageEntity: ImageEntity)
    suspend fun deleteImage(imageCard: ImageCard)
    suspend fun getAllImages():List<ImageCard>
    suspend fun getImageById(id: Int): ImageEntity

}