package com.dz.bmstu_trade.ui.main.canvas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dz.bmstu_trade.R

@Composable
fun CanvasScreen(
    navController: NavHostController,
    withConnection: Boolean,
    pictureId: Int? = null,
    viewModel: CanvasViewModel = hiltViewModel(
        creationCallback = { factory: CanvasViewModelFactory ->
            factory.create(pictureId, withConnection)
        }
    )
) {
    Scaffold(
        topBar = {
            CanvasTopBar(
                onGoBack = {
                    navController.popBackStack()
                },
                onSave = {
                    viewModel.savePicture()
                    navController.popBackStack()
                },
            )
        }
    ) { paddingValues ->
        val state = viewModel.canvasState.collectAsState()
        when (state.value){
            is CanvasStateScreen.Success -> CanvasPanel(
                paddingValues = paddingValues,
                pictureState = viewModel.picture,
                withConnection = withConnection,
                title = viewModel.title,
                onPictureUpdate = {
                    viewModel.onPictureUpdate(it)
                },
                onTitleUpdate = {
                    viewModel.setTitle(it)
                },
            )
            CanvasStateScreen.Error -> ErrorView(
                message = "Ошибочка",
                onRepeatConnection = {
                    viewModel.connectToDevice()
                }
            )
            CanvasStateScreen.Loading -> LoadingView()
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanvasTopBar(
    onGoBack: () -> Unit,
    onSave: () -> Unit,
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.canvas_title))
            }
        },
        navigationIcon = {
            IconButton(onClick = onGoBack) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow),
                    contentDescription = stringResource(R.string.back_button_description)
                )
            }
        },
        actions = {
            IconButton(onClick = onSave) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_save),
                    contentDescription = stringResource(R.string.save_drawing_button_description)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
    )
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String, onRepeatConnection:() -> Unit) {
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
