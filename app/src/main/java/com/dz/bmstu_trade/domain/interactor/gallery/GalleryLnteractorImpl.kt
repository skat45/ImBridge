package com.dz.bmstu_trade.domain.interactor.gallery

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.getSystemService
import com.dz.bmstu_trade.IMApp
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import com.dz.bmstu_trade.data.model.gallery.GalleryDb
import com.dz.bmstu_trade.data.model.gallery.ImageDao
import com.dz.bmstu_trade.data.model.gallery.ImageEntity
import kotlinx.coroutines.flow.Flow

class GalleryInteractorImpl(): GalleryInteractor {
    private val imageDao = AppContextHolder.getContext()?.applicationContext as IMApp
    override suspend fun insertImage(imageEntity: ImageEntity) {

        imageDao.galleryDatabase.imageDao().insertImage(imageEntity)


    }

    override suspend fun deleteImage(imageEntity: ImageEntity) {
        imageDao.galleryDatabase.imageDao().deleteImage(imageEntity)
    }

    override suspend fun getAllImages():List<ImageEntity> {
        return  imageDao.galleryDatabase.imageDao().getAllImages()
    }

    /*override suspend fun getLikedImages(): Flow<List<ImageEntity>> {
        return  imageDao.getLikedImages()
    }

    override suspend fun getImageById(imageId: Int) {
        imageDao.getImageById(imageId)
    }*/
}