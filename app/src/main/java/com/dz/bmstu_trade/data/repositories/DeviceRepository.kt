package com.dz.bmstu_trade.data.repositories

import com.dz.bmstu_trade.data.vo.DeviceVO

interface DeviceRepository {
    suspend fun getDevices(): List<DeviceVO>
}