package com.dz.bmstu_trade.data.model.gallery

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromListOfList(value: List<List<Int>>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toListOfList(value: String): List<List<Int>> {
        val listType = object : TypeToken<List<List<Int>>>() {}.type
        return Gson().fromJson(value, listType)
    }
}