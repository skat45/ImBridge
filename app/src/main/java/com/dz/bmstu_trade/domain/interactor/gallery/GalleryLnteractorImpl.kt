package com.dz.bmstu_trade.domain.interactor.gallery

import com.dz.bmstu_trade.data.mappers.toImageCard
import com.dz.bmstu_trade.data.mappers.toImageEntity
import com.dz.bmstu_trade.data.mappers.toPicture
import com.dz.bmstu_trade.data.model.ImageCard
import com.dz.bmstu_trade.data.model.gallery.GalleryDb
import com.dz.bmstu_trade.data.model.gallery.ImageEntity
import com.dz.bmstu_trade.ui.main.canvas.Picture
import javax.inject.Inject

class GalleryInteractorImpl @Inject constructor(db: GalleryDb) : GalleryInteractor {
    private val imageDao = db.imageDao()
    private fun convertToImageEntity(imageCard: ImageCard, isLikeChange: Boolean): ImageEntity {
        return imageCard.toImageEntity(isLikeChange)

    }

    override suspend fun insertImage(imageCard: ImageCard, isLikeChange: Boolean) {
        imageDao.insertImage(convertToImageEntity(imageCard, isLikeChange))
    }

    override suspend fun deleteImage(imageCard: ImageCard) {
        imageDao.deleteImage(convertToImageEntity(imageCard, imageCard.isLiked))
    }

    override suspend fun getAllImages(): List<ImageCard> {
        return imageDao.getAllImages()
            .map { it.toImageCard() }
    }

    override suspend fun getImageById(id: Int) = imageDao.getImageById(id)

    suspend fun getPictureById(id: Int): Picture = getImageById(id).toPicture()

    suspend fun insertFromCanvas(title: String, picture: Picture){
        imageDao.insertImage(picture.toImageEntity(title))
    }

    override suspend fun insertImageEntity(imageEntity: ImageEntity) {
        imageDao.insertImage(imageEntity)
    }
}