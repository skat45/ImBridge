package com.dz.bmstu_trade.domain.interactor.gallery


import com.dz.bmstu_trade.data.model.galleryModel.ImageDao
import com.dz.bmstu_trade.data.model.galleryModel.ImageEntity
import kotlinx.coroutines.flow.Flow

class GalleryInteractorImpl(private val imageDao: ImageDao): GalleryInteractor {
    //private val imageDao = AppContextHolder.getContext()?.applicationContext
    override suspend fun insert(imageEntity: ImageEntity) {
       imageDao.insertImage(imageEntity)
    }

    override suspend fun deleteImage(imageEntity: ImageEntity) {
        imageDao.deleteImage(imageEntity)
    }

    override fun getAllImages(): Flow<List<ImageEntity>> {
        return  imageDao.getAllImages()
    }

    override fun getLikedImages(): Flow<List<ImageEntity>> {
        return  imageDao.getLikedImages()
    }

    override suspend fun updateImageLikeState(imageId: Int, newLikeState: Boolean) {
        imageDao.getImageById(imageId)
    }
}