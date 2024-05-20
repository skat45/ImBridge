package com.dz.bmstu_trade.ui.main.vk_album

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dz.bmstu_trade.R
import com.dz.bmstu_trade.data.mappers.imageVkMapper
import com.dz.bmstu_trade.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseImageFromAlbum(
    navController: NavHostController,
    choosePictureViewModel: ChoosePictureViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val urlsFieldState by choosePictureViewModel.urls.collectAsState()

        TopAppBar(
            title = { Text(text = "Выбор фото") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(onClick = {navController.popBackStack() }) {
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
            items(urlsFieldState) {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier.clickable {  choosePictureViewModel.selectImage(it) }
                )
            }
        }
    }
}