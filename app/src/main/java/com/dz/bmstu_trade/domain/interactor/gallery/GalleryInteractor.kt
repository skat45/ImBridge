package com.dz.bmstu_trade.domain.interactor.gallery

import com.dz.bmstu_trade.data.model.gallery.ImageEntity
import kotlinx.coroutines.flow.Flow

interface GalleryInteractor {
    suspend fun insertImage(imageEntity: ImageEntity)
    suspend fun deleteImage(imageEntity: ImageEntity)
    suspend fun getAllImages():List<ImageEntity>

    /*suspend fun getLikedImages(): Flow<List<ImageEntity>>
    suspend fun getImageById(imageId: Int)*/

}