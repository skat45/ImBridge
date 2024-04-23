package com.dz.bmstu_trade.retrofit

import retrofit2.http.GET

interface TestDeviceRequestApi {
    @GET("test")
    fun getTestResponse():TestDeviceAnswer
}