package com.dz.bmstu_trade.domain.interactor

import android.util.Log
import com.dz.bmstu_trade.data.model.DeviceState
import com.dz.bmstu_trade.data.network.WebSocketClient
import com.dz.bmstu_trade.data.network.WebSocketListener
import com.dz.bmstu_trade.data.repositories.DeviceRepository
import com.dz.bmstu_trade.data.sharedpreferences.SharedPreferenceManager
import com.dz.bmstu_trade.data.vo.DeviceVO
import javax.inject.Inject

class DeviceStateInteractorImpl @Inject constructor(
    val sharedPreferenceManager: SharedPreferenceManager,
    val deviceRepository: DeviceRepository
) : DeviceStateInteractor {

    private var codeDevice = sharedPreferenceManager.getDeviceCode()

    override suspend fun connectToDevice(listener: WebSocketListener): Boolean {
        if (!codeDevice.isNullOrEmpty()){
            WebSocketClient.connect(listener, codeDevice!!)
            return true
        }
        return false
    }

    override suspend fun sendToDevice(deviceState: DeviceState) {
        WebSocketClient.send(deviceState)
    }

    override fun changeDevice(code: String) {
        codeDevice = code
        sharedPreferenceManager.saveDeviceCode(code)
    }

    override fun disconnectDevice() {
        WebSocketClient.disconnect()
    }

    override suspend fun getDevices(): List<DeviceVO> {
        Log.d("devices", deviceRepository.getDevices().toString())
        return deviceRepository.getDevices()
    }
}