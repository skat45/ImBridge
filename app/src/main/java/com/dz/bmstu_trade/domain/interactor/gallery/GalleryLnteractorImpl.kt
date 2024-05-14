package com.dz.bmstu_trade.domain.interactor.gallery

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.getSystemService
import com.dz.bmstu_trade.IMApp
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import com.dz.bmstu_trade.data.model.ImageCard
import com.dz.bmstu_trade.data.model.gallery.GalleryDb
import com.dz.bmstu_trade.data.model.gallery.ImageDao
import com.dz.bmstu_trade.data.model.gallery.ImageEntity
import kotlinx.coroutines.flow.Flow

class GalleryInteractorImpl() : GalleryInteractor {
    private val imageDao = AppContextHolder.getContext()?.applicationContext as IMApp
    private fun convertToImageEntity(imageCard: ImageCard, isLikeChange: Boolean): ImageEntity {

        return ImageEntity(
            id = imageCard.id,
            title = imageCard.title,
            imageUrl = imageCard.image,
            isLiked = isLikeChange
        )

    }

    override suspend fun insertImage(imageCard: ImageCard, isLikeChange: Boolean) {

        imageDao.galleryDatabase.imageDao().insertImage(convertToImageEntity(imageCard, isLikeChange))


    }

    override suspend fun deleteImage(imageCard: ImageCard) {
        imageDao.galleryDatabase.imageDao()
            .deleteImage(convertToImageEntity(imageCard, imageCard.isLiked))
    }

    override suspend fun getAllImages(): List<ImageCard> {
        return imageDao.galleryDatabase.imageDao().getAllImages()
            .map { ImageCard(it.id, it.imageUrl, it.title, it.isLiked) }
    }
}