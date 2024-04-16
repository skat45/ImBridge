package com.dz.bmstu_trade.ui.main.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dz.bmstu_trade.IMApp
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import com.dz.bmstu_trade.data.model.galleryModel.ImageEntity
import kotlinx.coroutines.launch


class GalleryScreenViewModel() : ViewModel() {
    val image = ImageEntity(imageUrl = "ijji", title = "jjj", isLiked = true, id = 1)
    val imageList: LiveData<List<ImageEntity>> = (AppContextHolder.getContext()?.applicationContext as IMApp).galleryInteractor.getAllImages().asLiveData()


    //val listImage = (AppContextHolder.getContext()?.applicationContext as IMApp).galleryInteractor.getAllImages()
    private val _screenState = MutableLiveData<Map<Tab, GalleryState>>().apply {
        value= mapOf(
            Tab.COMMUNITY to GalleryState(
                imageCards = imageList
            ),
            Tab.FAVOURITES to GalleryState(
                imageCards = imageList
            ),
            Tab.MY_PICTURES to GalleryState(
                imageCards = imageList
            )
        )
    }
    private fun insert() {

        viewModelScope.launch {
            (AppContextHolder.getContext()?.applicationContext as IMApp).galleryInteractor.insert(
                ImageEntity(imageUrl = "ijji", title = "jjj", isLiked = true, id = 1)
            )
        }


    }

    val screenState: LiveData<Map<Tab, GalleryState>> = _screenState
    //private inline fun <T> PersistentList<T>.set(index: Int, block: (item: T) -> T) =
     //   this.set(index, block(this[index]))

    fun applyAction(action: GalleryAction) {
        when (action) {
            is GalleryAction.SearchedChanged -> onChangeSearch(action.query, action.selectedTab)
            is GalleryAction.LikeStateChanged -> changeStateOfLikedCard(
                action.isLiked,
                action.index,
            )

            is GalleryAction.SearchedCleared -> clearSearchLine(action.selectedTab)
        }
    }

    private fun onChangeSearch(text: String, selectedTab: Tab) {

        viewModelScope.launch {
            (AppContextHolder.getContext()?.applicationContext as IMApp).galleryInteractor.insert(
                ImageEntity(imageUrl = "ijji", title = "jjj", isLiked = true)
            )
        }

    }

    private fun clearSearchLine(selectedTab: Tab) {
        viewModelScope.launch {
            (AppContextHolder.getContext()?.applicationContext as IMApp).galleryInteractor.insert(
                ImageEntity(imageUrl = "ijji", title = "jjj", isLiked = true)
            )
        }
    }

    private fun changeStateOfLikedCard(isLiked: Boolean, index: Int) {
        viewModelScope.launch {
            (AppContextHolder.getContext()?.applicationContext as IMApp).galleryInteractor.updateImageLikeState(index, isLiked)
        }

    }


    companion object {
        private const val NOT_FOUND = -1
    }


}

