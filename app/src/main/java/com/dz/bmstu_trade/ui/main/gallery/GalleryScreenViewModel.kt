package com.dz.bmstu_trade.ui.main.gallery

import android.app.ActionBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.bmstu_trade.data.model.ImageCard
import com.dz.bmstu_trade.data.model.gallery.ImageEntity
import com.dz.bmstu_trade.domain.interactor.gallery.GalleryInteractor
import com.dz.bmstu_trade.domain.interactor.gallery.GalleryInteractorImpl
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GalleryScreenViewModel : ViewModel() {
    private val list: GalleryInteractor = GalleryInteractorImpl()
    private val _screenState = MutableStateFlow(
        GalleryScreenState(
            tabsState = persistentMapOf(
                Tab.MY_PICTURES to GalleryTabState(),
                Tab.FAVOURITES to GalleryTabState()
            )
        )
    )
    val screenState: StateFlow<GalleryScreenState> = _screenState

    init {
        loadImages()
    }

    private fun loadImages() {

        viewModelScope.launch {
            val imageList = list.getAllImages()
                .map { it -> ImageCard(it.id, it.imageUrl, it.title, it.isLiked) }
            val favImageList = imageList.filter { it.isLiked }
            _screenState.value = GalleryScreenState(
                selectedTab = _screenState.value.selectedTab,
                persistentMapOf(
                    Tab.FAVOURITES to GalleryTabState(
                        imageCards = favImageList.toPersistentList()
                    ),
                    Tab.MY_PICTURES to GalleryTabState(
                        imageCards = imageList.toPersistentList()
                    ),

                    )
            )

        }

    }

    private fun convertToImageEntity(imageCard: ImageCard, isLikeChange: Boolean): ImageEntity {
        if (isLikeChange) {
            return ImageEntity(
                id = imageCard.id,
                title = imageCard.title,
                imageUrl = imageCard.image,
                isLiked = !imageCard.isLiked
            )
        } else {
            return ImageEntity(
                id = imageCard.id,
                title = imageCard.title,
                imageUrl = imageCard.image,
                isLiked = imageCard.isLiked
            )
        }
    }

    private fun insertImageDB(imageCard: ImageCard, isLikeChange: Boolean) {

        viewModelScope.launch {
            list.insertImage(convertToImageEntity(imageCard, isLikeChange))
        }
        loadImages()
    }

    private fun deleteImageDB(imageCard: ImageCard) {
        viewModelScope.launch {
            list.deleteImage(convertToImageEntity(imageCard, false))
        }
    }


    private inline fun <T> PersistentList<T>.set(index: Int, block: (item: T) -> T) =
        this.set(index, block(this[index]))

    fun applyAction(action: GalleryAction) {
        when (action) {
            is GalleryAction.SearchedChanged -> onChangeSearch(action.query, action.selectedTab)
            is GalleryAction.LikeStateChanged -> changeStateOfLikedCard(
                action.isLiked,
                action.index,
                action.selectedTab
            )

            is GalleryAction.SearchedCleared -> clearSearchLine(action.selectedTab)
        }
    }

    private fun onChangeSearch(text: String, selectedTab: Tab) {
        val updatedState = _screenState.value.tabsState[selectedTab]?.copy(query = text)
        if (updatedState != null) {
            this._screenState.value = this._screenState.value.copy(
                tabsState = this._screenState.value.tabsState.put(selectedTab, updatedState)
            )

        }
    }

    private fun clearSearchLine(selectedTab: Tab) {
        val updatedState = _screenState.value.tabsState[selectedTab]?.copy(query = "")
        if (updatedState != null) {
            this._screenState.value = this._screenState.value.copy(
                tabsState = this._screenState.value.tabsState.put(selectedTab, updatedState)
            )

        }
    }

    private fun changeStateOfLikedCard(isLiked: Boolean, index: Int, selectedTab: Tab) {
        insertImageDB(
            _screenState.value.tabsState[selectedTab]!!.imageCards[index],
            isLikeChange = true
        )


    }


    companion object {
        private const val NOT_FOUND = -1
    }


}
