package com.dz.bmstu_trade.domain.interactor.gallery

import com.dz.bmstu_trade.data.model.ImageCard
import com.dz.bmstu_trade.data.model.gallery.GalleryDb
import com.dz.bmstu_trade.data.model.gallery.ImageEntity
import javax.inject.Inject

class GalleryInteractorImpl @Inject constructor(db: GalleryDb) : GalleryInteractor {
    private val imageDao = db.imageDao()
    private fun convertToImageEntity(imageCard: ImageCard, isLikeChange: Boolean): ImageEntity {

        return ImageEntity(
            id = imageCard.id,
            title = imageCard.title,
            image = imageCard.image,
            isLiked = isLikeChange
        )

    }

    override suspend fun insertImage(imageCard: ImageCard, isLikeChange: Boolean) {

        imageDao.insertImage(convertToImageEntity(imageCard, isLikeChange))


    }

    override suspend fun deleteImage(imageCard: ImageCard) {
        imageDao.deleteImage(convertToImageEntity(imageCard, imageCard.isLiked))
    }

    override suspend fun getAllImages(): List<ImageCard> {
        return imageDao.getAllImages()
            .map { ImageCard(it.id, it.image, it.title, it.isLiked) }
    }
}