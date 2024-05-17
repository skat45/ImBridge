package com.dz.bmstu_trade.di

import com.dz.bmstu_trade.data.repositories.DeviceRepository
import com.dz.bmstu_trade.data.repositories.DeviceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideDeviceRepository(): DeviceRepository = DeviceRepositoryImpl()

}