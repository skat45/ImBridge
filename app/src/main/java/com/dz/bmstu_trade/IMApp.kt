package com.dz.bmstu_trade

import android.app.Application
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import com.dz.bmstu_trade.data.model.gallery.GalleryDb
import com.dz.bmstu_trade.domain.interactor.gallery.GalleryInteractor
import com.dz.bmstu_trade.domain.interactor.gallery.GalleryInteractorImpl

class IMApp: Application() {
    lateinit var galleryDatabase: GalleryDb
    //lateinit var galleryInteractor: GalleryInteractor
    override fun onCreate() {
        super.onCreate()
        galleryDatabase = GalleryDb.createDataBase(applicationContext)
        //galleryInteractor = GalleryInteractorImpl()
        AppContextHolder.setContext(this)
    }
}