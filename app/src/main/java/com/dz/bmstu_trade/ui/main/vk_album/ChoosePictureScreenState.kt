package com.dz.bmstu_trade.ui.main.vk_album

sealed class ChoosePictureScreenState {
    object Loading : ChoosePictureScreenState()
    object Error : ChoosePictureScreenState()
    data class Success(val urls: List<String>) : ChoosePictureScreenState()
}