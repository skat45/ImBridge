package com.dz.bmstu_trade

import android.app.Application
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import dagger.hilt.android.HiltAndroidApp
import com.dz.bmstu_trade.data.model.gallery.GalleryDb
import com.dz.bmstu_trade.domain.interactor.gallery.GalleryInteractor
import com.dz.bmstu_trade.domain.interactor.gallery.GalleryInteractorImpl
import kotlinx.coroutines.flow.MutableStateFlow

@HiltAndroidApp
class IMApp: Application() {
    lateinit var galleryDatabase: GalleryDb
    override fun onCreate() {
        super.onCreate()
        galleryDatabase = GalleryDb.createDataBase(applicationContext)
        AppContextHolder.setContext(this)
    }
}