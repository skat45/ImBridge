package com.dz.bmstu_trade.data.model.gallery

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageEntity: ImageEntity)

    @Delete
    suspend fun deleteImage(imageEntity: ImageEntity)

    @Query("SELECT * FROM ImageEntity")
    suspend fun getAllImages(): List<ImageEntity>

}