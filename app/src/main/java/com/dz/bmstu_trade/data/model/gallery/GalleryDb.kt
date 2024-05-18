package com.dz.bmstu_trade.data.model.gallery

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ImageEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class GalleryDb : RoomDatabase() {
    abstract fun imageDao(): ImageDao

    companion object {
        @Volatile
        private var INSTANCE: GalleryDb? = null

        fun createDataBase(context: Context): GalleryDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GalleryDb::class.java,
                    name = "gallery"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}