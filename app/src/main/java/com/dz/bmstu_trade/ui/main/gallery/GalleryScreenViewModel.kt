package com.dz.bmstu_trade.ui.main.gallery

import com.dz.bmstu_trade.data.model.ImageCard
import com.dz.bmstu_trade.data.model.GalleryState

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.dz.bmstu_trade.R
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList
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

    private fun changeLikeIconState(isLiked: Boolean, index: Int, selectedTab: Tab) {
        _screenState.value[selectedTab]?.let { galleryState ->
            val updatedImageCards = galleryState.imageCards.toMutableList()
            updatedImageCards[index] = updatedImageCards[index].copy(isLiked = isLiked)
            _screenState.value = _screenState.value.put(
                selectedTab,
                galleryState.copy(imageCards = updatedImageCards.toPersistentList())
            )
        }
    }

    private fun changeStateOfLikedCard(isLiked: Boolean, index: Int, selectedTab: Tab) {

        if (isLiked) {
            changeLikeIconState(isLiked, index, selectedTab)
            addCardToFav(index)
        } else {
            if (selectedTab == Tab.FAVOURITES) {
                val community = _screenState.value[Tab.COMMUNITY]?.imageCards
                val deletedCardFavorite = _screenState.value[selectedTab]?.imageCards

                if (community != null && deletedCardFavorite != null && community.indexOf(
                        deletedCardFavorite[index]
                    ) != -1
                ) {
                    changeLikeIconState(
                        false,
                        community.indexOf(deletedCardFavorite[index]),
                        Tab.COMMUNITY
                    )
                }
                deleteCardFromFav(selectedTab, index)

            } else {
                deleteCardFromFav(selectedTab, index)
                changeLikeIconState(false, index, selectedTab)

            }

        }

    }

    private fun addCardToFav(index: Int) {
        val newImageCard = _screenState.value[Tab.COMMUNITY]
        if (newImageCard != null) {
            _screenState.value[Tab.FAVOURITES]?.let { galleryState ->
                val updatedImageCards = galleryState.imageCards.toMutableList()
                updatedImageCards.add(newImageCard.imageCards[index])
                _screenState.value = _screenState.value.put(
                    Tab.FAVOURITES,
                    galleryState.copy(imageCards = updatedImageCards.toPersistentList())
                )
            }
        }


    }

    private fun deleteCardFromFav(selectedTab: Tab, index: Int) {
        val deletedImageCard = _screenState.value[selectedTab]
        if (deletedImageCard != null) {
            _screenState.value[Tab.FAVOURITES]?.let { galleryState ->
                val updatedImageCards = galleryState.imageCards.toMutableList()
                updatedImageCards.remove(deletedImageCard.imageCards[index])
                _screenState.value = _screenState.value.put(
                    Tab.FAVOURITES,
                    galleryState.copy(imageCards = updatedImageCards.toPersistentList())
                )
            }
        }


    }


}


enum class Tab(
    @StringRes
    val titleResId: Int,
) {
    COMMUNITY(R.string.community),
    FAVOURITES(R.string.favorite),
    MY_PICTURES(R.string.my_pic),
}


sealed class GalleryAction {
    data class SearchedChanged(val query: String, val selectedTab: Tab) : GalleryAction()
    data class LikeStateChanged(val isLiked: Boolean, val index: Int, val selectedTab: Tab) :
        GalleryAction()

    data class SearchedCleared(val selectedTab: Tab) : GalleryAction()
}
