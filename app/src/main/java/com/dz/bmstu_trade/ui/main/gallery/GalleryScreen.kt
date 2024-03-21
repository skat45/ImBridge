package com.dz.bmstu_trade.ui.main.gallery

import android.graphics.ColorSpace.Rgb
import android.widget.PopupWindow
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.dz.bmstu_trade.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dz.bmstu_trade.navigation.Routes


@Composable
fun GalleryScreen(
    navController: NavHostController,
    viewModelCommunity: GalleryScreenViewModel = remember { GalleryScreenViewModel() },
    viewModelFav: GalleryScreenViewModel = remember { GalleryScreenViewModel() },
    viewModelMyPic: GalleryScreenViewModel = remember { GalleryScreenViewModel() },
) {


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        var selectedTabIndex by remember { mutableStateOf(0) }
        val tabs = listOf(
            stringResource(R.string.Community),
            stringResource(R.string.Favorite),
            stringResource(R.string.My_pic)
        )

        TabRow(selectedTabIndex, containerColor = MaterialTheme.colorScheme.surface) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant

                )
            }
        }
        Box()
        {
            when (selectedTabIndex) {

                0 -> {
                    for_one_tab(
                        searchView = viewModelCommunity,
                        navController = navController,
                        noLike = false
                    )
                }

                1 -> {
                    for_one_tab(
                        searchView = viewModelFav,
                        navController = navController,
                        noLike = false
                    )
                }


                2 -> {
                    for_one_tab(
                        searchView = viewModelMyPic,
                        navController = navController,
                        noLike = true
                    )
                }

            }
        }


    }


}

@Composable
fun for_one_tab(
    navController: NavHostController,
    searchView: GalleryScreenViewModel,
    noLike: Boolean

) {
    val search by searchView.search.collectAsState()
    val cards by searchView.cardlist.collectAsState()
    Column {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.GalleryScreenHorizontalPadding)))
        for_Search(
            search = search,
            onChange = { searchView.OnChangeSearch(it) },
            clear = { searchView.ClearSearchFiled() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.GalleryScreenHorizontalPadding))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Scaffold(
            floatingActionButton = {
                for_floatingButton(onClick = {
                    navController.navigate(
                        Routes.CANVAS.value
                    )
                })
            },
            floatingActionButtonPosition = FabPosition.End,
            modifier = Modifier.weight(1f)

        ) {
            it
            Box {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 186.dp),
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    itemsIndexed(cards) { index, card ->
                        for_Card(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(168.dp)
                                .height(205.dp),
                            cardIndex = index,
                            viewModel = searchView,
                            noLike = noLike

                        )

                    }

                }
            }


        }
    }


}

@Composable
fun for_Search(
    search: String,
    modifier: Modifier = Modifier,
    onChange: (String) -> Unit,
    clear: () -> Unit
) {

    TextField(
        modifier = modifier,
        value = search,
        onValueChange = onChange,
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Outlined.Clear,
                contentDescription = null,
                modifier = Modifier.clickable(onClick = clear)
            )
        },
        placeholder = { Text(text = stringResource(R.string.enter_name_of_pic)) },
        shape = RoundedCornerShape(28.dp),
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun for_Card(
    modifier: Modifier,
    cardIndex: Int,
    viewModel: GalleryScreenViewModel,
    noLike: Boolean
) {
    val isLiked = mutableStateOf(viewModel.cardlist.value[cardIndex].isLiked)
    var ButtonState by mutableStateOf(false)


    Card(
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(168.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.canvas_color))

                ) {}//на этом месте картинка
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.End) {
                    if (!noLike) {
                        Box(
                            modifier = Modifier
                                .padding(end = 4.dp, top = 6.dp)
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(0.5f))
                        )
                        {

                            ChooseFavIcon(
                                isLiked = isLiked
                            )
                            viewModel.ChangeLikeState(isLiked.value, cardIndex)
                        }
                    }
                }


            }
            Row {
                Text(
                    text = viewModel.cardlist.value[cardIndex].title,
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                )
                Box()
                {
                    IconButton(onClick = { ButtonState = true }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_more_horiz_24),
                            contentDescription = stringResource(R.string.OpenDropDown_menu)
                        )
                    }

                    DropdownMenu(
                        expanded = ButtonState,
                        onDismissRequest = { ButtonState = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh)

                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                stringResource(R.string.Load_pic_to_device), fontSize = 10.sp, modifier = Modifier
                                    .clickable(onClick = {})
                                    .padding(horizontal = 10.dp)
                            )
                            if (noLike) {
                                Text(
                                    stringResource(R.string.Publish_pic), fontSize = 10.sp, modifier = Modifier
                                        .clickable(onClick = {})
                                        .padding(horizontal = 10.dp)
                                )
                                Text(
                                    stringResource(R.string.Delete_pic), fontSize = 10.sp, modifier = Modifier
                                        .clickable(onClick = {})
                                        .padding(horizontal = 10.dp)
                                )
                            }
                        }


                    }
                }


            }

        }

    }

}

@Composable
fun ChooseFavIcon(
    isLiked: MutableState<Boolean>,
) {

    IconToggleButton(
        checked = isLiked.value,
        onCheckedChange = { isLiked.value = it }) {
        if (isLiked.value) {
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = stringResource(R.string.Add_to_fav),
                tint = Color.White
            )


        } else {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = stringResource(R.string.Delete_from_fav),
                tint = Color.White
            )
        }

    }
}

@Composable
fun for_floatingButton(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier.size(60.dp),
        onClick = { onClick() }
    ) {
        Icon(Icons.Filled.Add, stringResource(R.string.Create_new_pic))
    }
}
