package com.dz.bmstu_trade.ui.main.canvas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dz.bmstu_trade.R

@Composable
fun CanvasScreen(navController: NavHostController) {
    Scaffold(
        topBar = { CanvasTopBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val selectedColor = remember {
                mutableStateOf(Color.Blue)
            }
            val eraserMode = remember { mutableStateOf(false) }
            val reflectHorizontalMode = remember { mutableStateOf(false) }
            val reflectVerticalMode = remember { mutableStateOf(false) }
            var text by rememberSaveable { mutableStateOf("") }

            Spacer(modifier = Modifier.height(16.dp))

            DrawingGrid(
                padding = 8.dp,
                rows = 10,
                columns = 10,
                selectedColor = selectedColor,
                eraseMode = eraserMode,
                reflectHorizontalMode = reflectHorizontalMode,
                reflectVerticalMode = reflectVerticalMode,
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.input_pic_name),
                        style = TextStyle(color = Color.LightGray, fontSize = 18.sp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            DrawingInstrumentsRow(
                onBackDrawingButtonClick = { /* TODO: Откат действия */ },
                onFurtherDrawingButtonClick = { /* TODO: Возобнавление действия */ },
                onDrawButtonClick = {
                    eraserMode.value = false
                },
                onEraseModeButtonClick = {
                    eraserMode.value = true
                },
                onClearAllButtonClick = {},
            )

            Spacer(modifier = Modifier.height(16.dp))

            ColorPicker(selectedColor = selectedColor) {
                selectedColor.value = it
            }

            Spacer(modifier = Modifier.height(16.dp))

            ControlInstrumentsRow(
                onReflectVerticallyValueChange = {
                    reflectHorizontalMode.value = false
                    reflectVerticalMode.value = !reflectVerticalMode.value
                },
                onReflectHorizontallyValueChange = {
                    reflectVerticalMode.value = false
                    reflectHorizontalMode.value = !reflectHorizontalMode.value
                },
                onDragValueChange = { /* TODO: Подвигать канвас */ },
                onZoomInValueChange = { /* TODO: Приблизить канвас */ },
                onZoomOutValueChange = { /* TODO: Отдалить канвас */ },
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanvasTopBar() {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.canvas_title))
            }
        },
        navigationIcon = {
            IconButton({}) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_post),
                    contentDescription = null
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_save),
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
    )
}

@Composable
fun DrawingInstrumentsRow(
    onBackDrawingButtonClick: () -> Unit,
    onFurtherDrawingButtonClick: () -> Unit,
    onDrawButtonClick: () -> Unit,
    onEraseModeButtonClick: () -> Unit,
    onClearAllButtonClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        IconButton(onClick = onBackDrawingButtonClick) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_draw_back),
                contentDescription = null
            )
        }
        IconButton(onClick = onFurtherDrawingButtonClick) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_draw_further),
                contentDescription = null
            )
        }
        IconButton(onClick = onDrawButtonClick) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_painter),
                contentDescription = null
            )
        }
        IconButton(onClick = onEraseModeButtonClick) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_eraser),
                contentDescription = null
            )
        }
        IconButton(onClick = onClearAllButtonClick) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_clean_all),
                contentDescription = null
            )
        }
    }
}

@Composable
fun ControlInstrumentsRow(
    onReflectVerticallyValueChange: () -> Unit,
    onReflectHorizontallyValueChange: () -> Unit,
    onDragValueChange: () -> Unit,
    onZoomInValueChange: () -> Unit,
    onZoomOutValueChange: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        IconButton(onClick = onReflectVerticallyValueChange) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_reflect_horizontally),
                contentDescription = null
            )
        }
        IconButton(onClick = onReflectHorizontallyValueChange) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_reflect_vertically),
                contentDescription = null
            )
        }
        IconButton(onClick = onDragValueChange) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_move_pic),
                contentDescription = null
            )
        }
        IconButton(onClick = onZoomInValueChange) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_zoom_in),
                contentDescription = null
            )
        }
        IconButton(onClick = onZoomOutValueChange) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_zoom_out),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun CanvasPreview() {
    val navController = rememberNavController()
    CanvasScreen(navController = navController)
}
