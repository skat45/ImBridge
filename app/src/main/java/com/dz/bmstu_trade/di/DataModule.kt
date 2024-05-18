package com.dz.bmstu_trade.di

import android.content.Context
import com.dz.bmstu_trade.data.model.gallery.GalleryDb
import com.dz.bmstu_trade.data.repositories.DeviceRepository
import com.dz.bmstu_trade.data.repositories.DeviceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GalleryDb =
        GalleryDb.createDataBase(context)

}