package com.dz.bmstu_trade.data.model.gallery

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageEntity: ImageEntity)

    @Delete
    suspend fun deleteImage(imageEntity: ImageEntity)

    @Query("SELECT * FROM ImageEntity")
    suspend fun getAllImages(): List<ImageEntity>

    /*@Query("SELECT * FROM ImageEntity WHERE isLiked=0")
    fun getLikedImages(): Flow<List<ImageEntity>>

    @Query("SELECT * FROM ImageEntity WHERE id = :id")
    fun getImageById(id: Int): ImageEntity*/


}