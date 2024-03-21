package com.dz.bmstu_trade.ui.main.gallery

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GalleryScreenViewModel : ViewModel() {
    private val _search = MutableStateFlow("")
    private val _card = MutableStateFlow(OneCard("", "", true))
    private val _cardlist = MutableStateFlow(mutableListOf(OneCard("kfkmk", "1"), OneCard("kfkmk", "mdlmcm"), OneCard("dgbSDBcjbzdsjbcjubbjbj", "jhdjcndjnjdnvhvhvhhc")))
    val search: StateFlow<String> = _search
    val cardlist: StateFlow<MutableList<OneCard>> = _cardlist
    fun OnChangeSearch(text: String) {
        this._search.value = text
    }
    fun ClearSearchFiled()
    {
        this._search.value=""
    }
    fun ChangeLikeState(isLiked: Boolean, index:Int)
    {
        this._cardlist.value[index] = OneCard(isLiked = isLiked, image = this._cardlist.value[index].image, title = this._cardlist.value[index].title )
    }
    fun addCard(card:OneCard)
    {
        this._cardlist.value.add(card)
    }
    fun deleteCard(card: OneCard)
    {
        this._cardlist.value.remove(card)
    }
}
class OneCard(
    val image: String,
    val title: String,
    val isLiked: Boolean = false,
)




