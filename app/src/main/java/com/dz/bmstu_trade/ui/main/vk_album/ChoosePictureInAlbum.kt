package com.dz.bmstu_trade.ui.main.vk_album

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dz.bmstu_trade.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseImageVkScreen(
    urls: List<String>,
    onArrowClick: () -> Unit,
    onImageClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.select_picture)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(onClick = onArrowClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.arrow_back_icon_description)
                    )
                }
            }
        )
        LazyVerticalGrid(
            GridCells.Fixed(2)
        ) {
            items(urls) {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onImageClick(it)
                    }
                )
            }
        }
    }
}

@Composable
fun ChooseImageErrorScreen(onRepeatConnection: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),  // Добавляем отступы для всего столбца
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Произошла ошибка при подключении", modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = onRepeatConnection) {
            Text("Повторить попытку")
        }
    }
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ChooseImageFromAlbum(
    navController: NavHostController,
    choosePictureViewModel: ChoosePictureViewModel = hiltViewModel()
) {
    val state = choosePictureViewModel.state.collectAsState()
    when (state.value) {
        is ChoosePictureScreenState.Success -> ChooseImageVkScreen(
            urls = (state.value as ChoosePictureScreenState.Success).urls,
            onArrowClick = {
                navController.popBackStack()
            },
            onImageClick = {
                choosePictureViewModel.selectImage(it) {
                    navController.popBackStack()
                }
            }
        )

        is ChoosePictureScreenState.Error -> ChooseImageErrorScreen(
            onRepeatConnection = {
                choosePictureViewModel.loadImages()
            }
        )

        is ChoosePictureScreenState.Loading -> LoadingView()
    }
}