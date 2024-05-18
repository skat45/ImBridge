package com.dz.bmstu_trade.ui.main.vk_album
import android.util.Log
import androidx.lifecycle.ViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.sdk.api.photos.PhotosService
import com.vk.sdk.api.photos.dto.PhotosGetResponseDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach

class ChoosePictureViewModel : ViewModel() {

    private val _urls = MutableStateFlow(emptyList<String>())
    val urls: StateFlow<List<String>> = _urls

    init {
        VK.execute(PhotosService().photosGet(VK.getUserId(), "profile"), object : VKApiCallback<PhotosGetResponseDto> {
            override fun success(result: PhotosGetResponseDto) {
                _urls.value = result.items.map{
                    it.sizes?.last()?.url.toString()
                }
            }

            override fun fail(error: Exception) {
                Log.d("Album error", "error: $error")
            }
        })

    }
}

