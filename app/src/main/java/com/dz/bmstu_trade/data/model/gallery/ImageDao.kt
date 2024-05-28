package com.dz.bmstu_trade.data.model.gallery

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageEntity: ImageEntity)

    @Update
    suspend fun updateImage(imageEntity: ImageEntity)

    @Delete
    suspend fun deleteImage(imageEntity: ImageEntity)

    @Query("SELECT * FROM ImageEntity")
    suspend fun getAllImages(): List<ImageEntity>

    @Query("SELECT * FROM ImageEntity WHERE id = :id")
    suspend fun getImageById(id: Int): ImageEntity

}