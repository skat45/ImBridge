package com.dz.bmstu_trade.ui.main.canvas

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.bmstu_trade.data.mappers.toPicture
import com.dz.bmstu_trade.data.model.DeviceState
import com.dz.bmstu_trade.data.network.WebSocketListener
import com.dz.bmstu_trade.domain.interactor.DeviceStateInteractorImpl
import com.dz.bmstu_trade.domain.interactor.gallery.GalleryInteractorImpl
import com.dz.bmstu_trade.ui.main.connect.connection_progress_bar.WifiViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel(assistedFactory = CanvasViewModelFactory::class)
class CanvasViewModel @AssistedInject constructor(
    val deviceInteractor: DeviceStateInteractorImpl,
    val galleryInteractor: GalleryInteractorImpl,
    @Assisted val pictureId: Int?,
    @Assisted val withConnection: Boolean,
) : ViewModel(), WebSocketListener {

    private val _canvasState = MutableStateFlow<CanvasStateScreen>(CanvasStateScreen.Loading)
    val canvasState: StateFlow<CanvasStateScreen> = _canvasState.asStateFlow()

    var picture = mutableStateOf(Picture())
        private set

    var title = mutableStateOf("")
        private set

    private lateinit var device: DeviceState

    init {
        if (withConnection)
        {
            connectToDevice()
            Log.d("id", pictureId.toString())
            if (pictureId != null){
                getPicture()
            }
        }
        else
            if (pictureId != null)
                getPicture()
            else
                _canvasState.value = CanvasStateScreen.Success
    }

    private fun getPicture() {
        viewModelScope.launch {
            _canvasState.value = CanvasStateScreen.Loading
            try {
                val image = galleryInteractor.getImageById(pictureId!!)
                picture.value = image.toPicture()
                title.value = image.title
                _canvasState.value = CanvasStateScreen.Success
            } catch (e: Exception) {
                Log.d("Exception", e.toString())
                _canvasState.value = CanvasStateScreen.Error
            }
        }
    }

    fun setTitle(newTitle: String) {
        title.value = newTitle
    }

    fun savePicture() {
        viewModelScope.launch {
            galleryInteractor.insertFromCanvas(title.value, picture.value)
        }
    }

    fun onPictureUpdate(newPicture: Picture) {
        viewModelScope.launch {
            if (withConnection)
                deviceInteractor.sendToDevice(
                    DeviceState(
                        image = newPicture.getArgbList(),
                        isOn = device.isOn,
                        brightness = device.brightness,
                        name = device.name
                    )
                )
            picture.value = newPicture
        }
    }

    override fun onConnected(deviceState: DeviceState) {
        viewModelScope.launch {
            device = deviceState
            picture.value = deviceState.toPicture()
            _canvasState.value = CanvasStateScreen.Success
        }
    }

    override fun onConnectionError() {
        viewModelScope.launch {
            _canvasState.value = CanvasStateScreen.Error
        }
    }

    override fun onMessage(deviceState: DeviceState) {
        viewModelScope.launch {
            device = deviceState
            picture.value = deviceState.toPicture()
            _canvasState.value = CanvasStateScreen.Success
        }
    }

    override fun onDisconnected() {
        viewModelScope.launch {
            _canvasState.value = CanvasStateScreen.Error
        }
    }

    fun connectToDevice() {
        viewModelScope.launch {
            _canvasState.value = CanvasStateScreen.Loading
            deviceInteractor.connectToDevice(this@CanvasViewModel)
        }
    }

}

sealed class CanvasStateScreen {
    object Loading : CanvasStateScreen()
    object Error : CanvasStateScreen()
    object Success : CanvasStateScreen()
}

@AssistedFactory
interface CanvasViewModelFactory {
    fun create(id: Int?, connection: Boolean): CanvasViewModel
}