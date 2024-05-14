package com.dz.bmstu_trade.ui.main.gallery

import androidx.annotation.StringRes
import com.dz.bmstu_trade.R

enum class Tab(
    @StringRes
    val titleResId: Int,
) {
    FAVOURITES(R.string.favorite),
    MY_PICTURES(R.string.my_pic),
}