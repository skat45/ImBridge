package com.dz.bmstu_trade.data.model.gallery

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String = "",
    val isLiked: Boolean = false,
    val image: List<List<Int>>,
)
