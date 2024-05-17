package com.dz.bmstu_trade.ui.main.canvas

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.bmstu_trade.data.mappers.toPicture
import com.dz.bmstu_trade.data.model.DeviceState
import com.dz.bmstu_trade.data.network.WebSocketListener
import com.dz.bmstu_trade.domain.interactor.DeviceStateInteractorImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CanvasViewModel @Inject constructor(
    val interactor: DeviceStateInteractorImpl
) : ViewModel(), WebSocketListener {

    private val _canvasState = MutableStateFlow<CanvasStateScreen>(CanvasStateScreen.Loading)
    val canvasState: StateFlow<CanvasStateScreen> = _canvasState.asStateFlow()

    var picture = mutableStateOf(Picture())
        private set

    private lateinit var device: DeviceState

    init {
        connectToDevice()
    }

    fun onPictureUpdate(newPicture: Picture){
        viewModelScope.launch {
            interactor.sendToDevice(
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
        viewModelScope.launch{
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

    fun connectToDevice(){
        viewModelScope.launch {
            _canvasState.value = CanvasStateScreen.Loading
            interactor.connectToDevice(this@CanvasViewModel)
        }
    }

}

sealed class CanvasStateScreen {
    object Loading : CanvasStateScreen()
    object Error : CanvasStateScreen()
    object Success : CanvasStateScreen()
}