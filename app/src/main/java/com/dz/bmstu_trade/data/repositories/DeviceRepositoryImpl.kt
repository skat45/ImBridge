package com.dz.bmstu_trade.data.repositories

import com.dz.bmstu_trade.data.network.HttpClientKtor
import com.dz.bmstu_trade.data.vo.DeviceVO
import io.ktor.client.call.body
import io.ktor.client.request.get

class DeviceRepositoryImpl : DeviceRepository {
    override suspend fun getDevices(): List<DeviceVO> {
        return HttpClientKtor.client.get("http://10.0.2.2:8000/api/devices/").body()
    }
}