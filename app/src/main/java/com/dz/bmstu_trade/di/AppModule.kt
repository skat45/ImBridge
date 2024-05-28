package com.dz.bmstu_trade.di

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideLocalState():MutableStateFlow<Locale>{
        return MutableStateFlow(Locale.getDefault())
    }

    @Provides
    @Singleton
    fun provideConfiguration(@ApplicationContext context: Context):Configuration{
        return  context.resources.configuration
    }

    @Provides
    @Singleton
    fun providerThemeState():MutableStateFlow<Boolean>{
        return MutableStateFlow(false)
    }
}