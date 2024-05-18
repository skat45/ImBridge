package com.dz.bmstu_trade.ui.main.vk_album
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.graphics.scale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.bmstu_trade.data.mappers.imageVkMapper
import com.dz.bmstu_trade.data.model.gallery.ImageEntity
import com.dz.bmstu_trade.domain.interactor.gallery.GalleryInteractorImpl
import com.dz.bmstu_trade.ui.main.gallery.ImageCard
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.sdk.api.photos.PhotosService
import com.vk.sdk.api.photos.dto.PhotosGetResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.http.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import java.net.URL
import java.util.Arrays
import javax.inject.Inject
@HiltViewModel
class ChoosePictureViewModel@Inject constructor(
    val interactor: GalleryInteractorImpl
) : ViewModel() {

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

    fun selectImage(url:String){

        viewModelScope.launch(Dispatchers.IO)  {
            val pixels = IntArray(256)
            val bitmap = BitmapFactory.decodeStream(URL(url).openStream()).scale(16, 16)
            bitmap.getPixels(pixels, 0, 16, 0, 0, 16, 16)
            val imageColors = MutableList<MutableList<Int>>(16) { MutableList<Int>(16) { 0 } }
            for (index in pixels.indices){
                imageColors.get(index/16).set(index%16, pixels[index])
            }
            interactor.insertImageEntity(ImageEntity(image = imageColors) )

        }
    }
}

