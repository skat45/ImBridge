package com.dz.bmstu_trade.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest (
    val body: String,
    val title: String,
    val userId: Int
)