package com.dz.bmstu_trade.ui.main.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    viewModel: GalleryScreenViewModel = remember { GalleryScreenViewModel() },
) {
    val screenState by viewModel.screenState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        var selectedTab by remember { mutableStateOf(Tab.COMMUNITY) }
        val tabs = screenState.keys.map { stringResource(id = it.titleResId) }

        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTab.ordinal == index,
                    onClick = { selectedTab = Tab.values()[index] },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant

                )
            }
        }
        Box()
        {
            screenState[selectedTab]?.let {
                SelectedTab(
                    navController = navController,
                    state = it,
                    onAction = { viewModel.applyAction(it) },
                    selectedTab = selectedTab
                )
            }
        }


    }

}

@Composable
fun SelectedTab(
    navController: NavHostController,
    state: GalleryState,
    onAction: (GalleryAction) -> Unit,
    selectedTab: Tab
) {
    Column {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.GalleryScreenHorizontalPadding)))
        SearchLine(
            search = state.query,
            onChange = { onAction(GalleryAction.SearchedChanged(it, selectedTab)) },
            clear = { onAction(GalleryAction.SearchedCleared(selectedTab)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.GalleryScreenHorizontalPadding))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize())
        {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                itemsIndexed(state.imageCards) { index, card ->
                    ImageCard(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth()
                            .height(205.dp),
                        state = state,
                        cardIndex = index,
                        onAction = onAction,
                        selectedTab = selectedTab
                    )

                }

            }
            Button(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.GalleryScreenHorizontalPadding),
                        vertical = dimensionResource(R.dimen.GalleryScreenHorizontalPadding)
                    )
                    .align(Alignment.BottomEnd)
                    .size(64.dp),
                onClick = {
                    navController.navigate(
                        Routes.CANVAS.value
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    contentColor = MaterialTheme.colorScheme.primary,
                ),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = stringResource(R.string.create_new_pic)
                )
            }

        }
    }
}

@Composable
fun SearchLine(
    search: String,
    modifier: Modifier = Modifier,
    onChange: (String) -> Unit,
    clear: () -> Unit,
) {

    TextField(
        modifier = modifier,
        value = search,
        onValueChange = onChange,
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Outlined.Clear,
                contentDescription = stringResource(R.string.clear_search_line),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false), onClick = clear)
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
fun ImageCard(
    modifier: Modifier,
    state: GalleryState,
    cardIndex: Int,
    onAction: (GalleryAction) -> Unit,
    selectedTab: Tab
) {
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
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorResource(R.color.canvas_color))

                ) {}//на этом месте картинка
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.End) {
                    if (selectedTab.ordinal != Tab.MY_PICTURES.ordinal) {
                        Box(
                            modifier = Modifier
                                .padding(end = 4.dp, top = 6.dp)
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(0.5f))
                        )
                        {
                            IconFavorite(
                                isLiked = state.imageCards[cardIndex].isLiked,
                                onAction = onAction,
                                selectedTab = selectedTab,
                                cardIndex = cardIndex
                            )
                        }
                    }
                }


            }
            Row {
                Text(
                    text = state.imageCards[cardIndex].title,
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
                DropDownMenuImage(selectedTab = selectedTab)

            }

        }

    }

}

@Composable
fun DropDownMenuImage(
    modifier: Modifier = Modifier,
    selectedTab: Tab
) {
    var buttonState by remember {
        mutableStateOf(false)
    }
    Box {
        IconButton(
            onClick = { buttonState = true },
            modifier = Modifier
                .size(32.dp)
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_more_horiz_24),
                contentDescription = stringResource(R.string.openDropDown_menu)
            )
        }

        DropdownMenu(
            expanded = buttonState,
            onDismissRequest = { buttonState = false },
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh)

        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    stringResource(R.string.load_pic_to_device),
                    fontSize = 10.sp,
                    modifier = Modifier
                        .clickable(onClick = {})
                        .padding(horizontal = 10.dp)
                )
                if (selectedTab.ordinal == Tab.MY_PICTURES.ordinal) {
                    Text(
                        stringResource(R.string.publish_pic),
                        fontSize = 10.sp,
                        modifier = Modifier
                            .clickable(onClick = {})
                            .padding(horizontal = 10.dp)
                    )
                    Text(
                        stringResource(R.string.delete_pic),
                        fontSize = 10.sp,
                        modifier = Modifier
                            .clickable(onClick = {})
                            .padding(horizontal = 10.dp)
                    )
                }
            }


        }
    }
}

@Composable
fun IconFavorite(
    isLiked: Boolean,
    onAction: (GalleryAction) -> Unit,
    selectedTab: Tab,
    cardIndex: Int
) {
    IconToggleButton(
        checked = isLiked,
        onCheckedChange = {
            onAction(
                GalleryAction.LikeStateChanged(
                    it,
                    cardIndex,
                    selectedTab
                )
            )
        }) {
        if (isLiked) {
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = stringResource(R.string.add_to_fav),
                tint = Color.White
            )


        } else {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = stringResource(R.string.delete_from_fav),
                tint = Color.White
            )
        }

    }
}

@Composable
fun FloatingButton(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier.size(60.dp),
        onClick = { onClick() }
    ) {
        Icon(Icons.Filled.Add, stringResource(R.string.create_new_pic))
    }
}
