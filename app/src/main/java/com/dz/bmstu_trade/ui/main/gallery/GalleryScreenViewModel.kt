package com.dz.bmstu_trade.ui.main.gallery

import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.data.model.ImageCard
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GalleryScreenViewModel : ViewModel() {
    private val _screenState = MutableStateFlow(
        persistentMapOf(
            Tab.COMMUNITY to GalleryState(
                imageCards = persistentListOf(
                    ImageCard(
                        image = "",
                        title = "Title"
                    ),
                    ImageCard(
                        image = "",
                        title = "Title 2"
                    )
                )
            ),
            Tab.FAVOURITES to GalleryState(
                imageCards = persistentListOf(
                    ImageCard(
                        image = "",
                        title = "Title 3",
                        isLiked = true
                    )
                )
            ),
            Tab.MY_PICTURES to GalleryState(
            )
        )

    )
    val screenState: StateFlow<ImmutableMap<Tab, GalleryState>> = _screenState
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
        val updatedState = _screenState.value[selectedTab]?.copy(query = text)
        if (updatedState != null) {
            this._screenState.value = this._screenState.value.put(selectedTab, updatedState)
        }
    }

    private fun clearSearchLine(selectedTab: Tab) {
        val updatedState = _screenState.value[selectedTab]?.copy(query = "")
        if (updatedState != null) {
            this._screenState.value = this._screenState.value.put(selectedTab, updatedState)
        }
    }

    private fun changeStateOfLikedCard(isLiked: Boolean, index: Int, selectedTab: Tab) {

        if (isLiked) {
            changeLikeIconState(isLiked, index, selectedTab)
            addCardToFav(index)
        } else {
            if (selectedTab == Tab.FAVOURITES) {
                changeLikeStateInCommunityFromFav(selectedTab, index)
                deleteCardFromFav(selectedTab, index)

            } else {
                deleteCardFromFav(selectedTab, index)
                changeLikeIconState(false, index, selectedTab)

            }

        }

    }

    private fun changeLikeStateInCommunityFromFav(selectedTab: Tab, index: Int) {
        val community = _screenState.value[Tab.COMMUNITY]?.imageCards ?: return
        val deletedCardFavorite = _screenState.value[selectedTab]?.imageCards ?: return

        community.indexOf(deletedCardFavorite[index]).let {
            if (it == NOT_FOUND) return
            changeLikeIconState(
                isLiked = false,
                index = it,
                selectedTab = Tab.COMMUNITY
            )
        }
    }

    private fun changeLikeIconState(isLiked: Boolean, index: Int, selectedTab: Tab) {
        _screenState.value[selectedTab]?.let { galleryState ->
            _screenState.value = _screenState.value.put(
                selectedTab,
                galleryState.copy(imageCards = galleryState.imageCards.set(index) { it.copy(isLiked = isLiked) })
            )
        }
    }

    private fun addCardToFav(index: Int) {
        val newImageCard = _screenState.value[Tab.COMMUNITY]
        if (newImageCard != null) {
            _screenState.value[Tab.FAVOURITES]?.let { galleryState ->
                _screenState.value = _screenState.value.put(
                    Tab.FAVOURITES,
                    galleryState.copy(imageCards = galleryState.imageCards.add(newImageCard.imageCards[index]))
                )
            }
        }


    }

    private fun deleteCardFromFav(selectedTab: Tab, index: Int) {
        val deletedImageCard = _screenState.value[selectedTab]
        if (deletedImageCard != null) {
            _screenState.value[Tab.FAVOURITES]?.let { galleryState ->
                _screenState.value = _screenState.value.put(
                    Tab.FAVOURITES,
                    galleryState.copy(imageCards = galleryState.imageCards.remove(deletedImageCard.imageCards[index]))
                )
            }
        }


    }

    companion object {
        private const val NOT_FOUND = -1
    }


}

