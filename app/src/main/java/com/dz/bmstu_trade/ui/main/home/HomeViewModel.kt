package com.dz.bmstu_trade.ui.main.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.bmstu_trade.data.mappers.toPicture
import com.dz.bmstu_trade.data.model.DeviceState
import com.dz.bmstu_trade.data.network.WebSocketListener
import com.dz.bmstu_trade.data.vo.DeviceVO
import com.dz.bmstu_trade.domain.interactor.DeviceStateInteractorImpl
import com.dz.bmstu_trade.ui.main.canvas.Picture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val interactor: DeviceStateInteractorImpl
) : ViewModel(), WebSocketListener {

    private val _deviceState = MutableStateFlow<DeviceStateScreen>(DeviceStateScreen.Loading)
    val deviceState: StateFlow<DeviceStateScreen> = _deviceState.asStateFlow()

    private val _devices = MutableStateFlow<List<DeviceVO>>(listOf())
    val devices: StateFlow<List<DeviceVO>> = _devices.asStateFlow()

    val pictureState = mutableStateOf(Picture())

    init {
        getDevices()
        connectToDevice()
    }

    fun onDeviceItemClick(code: String){
        interactor.changeDevice(code)
        viewModelScope.launch {
            interactor.connectToDevice(this@HomeViewModel)
        }
    }

    override fun onConnected(deviceState: DeviceState) {
        Log.d("onConnected", deviceState.toString())
        updatePic(deviceState)
        _deviceState.value = DeviceStateScreen.Success(deviceState)
    }

    override fun onConnectionError() {
        viewModelScope.launch {
            delay(3000)
            _deviceState.value = DeviceStateScreen.Error
        }
    }

    override fun onMessage(deviceState: DeviceState) {
        viewModelScope.launch {
            Log.d("onMessage", deviceState.toString())
            updatePic(deviceState)
            _deviceState.value = DeviceStateScreen.Success(deviceState)
        }
    }

    override fun onDisconnected() {
        viewModelScope.launch {
            _deviceState.value = DeviceStateScreen.Error
        }
    }

    fun sendState(
        name: String,
        brightness: Int,
        isOn: Boolean
    ){
        val deviceState = DeviceState(
            name = name,
            image = pictureState.value.getArgbList(),
            brightness = brightness,
            isOn = isOn
        )
        viewModelScope.launch {
            interactor.sendToDevice(deviceState)
        }
    }

    fun connectToDevice(){
        viewModelScope.launch {
            _deviceState.value = DeviceStateScreen.Loading
            if (!interactor.connectToDevice(this@HomeViewModel)){
                _deviceState.value = DeviceStateScreen.Undefined
            }
        }
    }

    fun disconnectFromDevice(){
        interactor.disconnectDevice()
    }

    private fun updatePic(deviceState: DeviceState){
        pictureState.value = deviceState.toPicture()
    }

    fun getDevices(){
        viewModelScope.launch {
            _devices.value = interactor.getDevices()
            if (_devices.value.isEmpty())
                _deviceState.value = DeviceStateScreen.Empty
        }
    }
}

sealed class DeviceStateScreen {
    object Loading : DeviceStateScreen()
    object Empty : DeviceStateScreen()
    object Undefined : DeviceStateScreen()
    object Error : DeviceStateScreen()
    data class Success(val data: DeviceState) : DeviceStateScreen()
}
