package com.dz.bmstu_trade.domain.interactor.gallery

import com.dz.bmstu_trade.data.model.galleryModel.ImageEntity
import kotlinx.coroutines.flow.Flow

interface GalleryInteractor {
    suspend fun insert(imageEntity: ImageEntity)
    suspend fun deleteImage(imageEntity: ImageEntity)
    fun getAllImages(): Flow<List<ImageEntity>>

    fun getLikedImages(): Flow<List<ImageEntity>>
    suspend fun updateImageLikeState(imageId: Int, newLikeState: Boolean)

}