package com.dz.bmstu_trade.ui.main.vk_album

import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.graphics.scale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.bmstu_trade.data.model.gallery.ImageEntity
import com.dz.bmstu_trade.domain.interactor.gallery.GalleryInteractorImpl
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.sdk.api.photos.PhotosService
import com.vk.sdk.api.photos.dto.PhotosGetResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class ChoosePictureViewModel @Inject constructor(
    val interactor: GalleryInteractorImpl
) : ViewModel() {

    private val _state =
        MutableStateFlow<ChoosePictureScreenState>(ChoosePictureScreenState.Loading)
    val state: StateFlow<ChoosePictureScreenState> = _state

    init {
        loadImages()
    }

    fun loadImages() {
        _state.value = ChoosePictureScreenState.Loading
        VK.execute(
            PhotosService().photosGet(VK.getUserId(), "profile"),
            object : VKApiCallback<PhotosGetResponseDto> {
                override fun success(result: PhotosGetResponseDto) {
                    val urls = result.items.map {
                        it.sizes?.last()?.url.toString()
                    }
                    _state.value = ChoosePictureScreenState.Success(urls)
                }

                override fun fail(error: Exception) {
                    _state.value = ChoosePictureScreenState.Error
                    Log.d("Album error", "error: $error")
                }
            }
        )
    }

    fun selectImage(url: String, callback: () -> Unit) {

        viewModelScope.launch(Dispatchers.IO) {
            val pixels = IntArray(256)
            val bitmap = BitmapFactory.decodeStream(URL(url).openStream()).scale(16, 16)
            bitmap.getPixels(pixels, 0, 16, 0, 0, 16, 16)
            val imageColors = MutableList(16) { MutableList(16) { 0 } }
            for (index in pixels.indices) {
                imageColors[index / 16][index % 16] = pixels[index]
            }
            interactor.insertImageEntity(ImageEntity(image = imageColors))
            withContext(Dispatchers.Main) {
                callback()
            }
        }
    }
}

