package com.dz.bmstu_trade.ui.main.canvas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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

private const val MAX_SCALE = 3f
private const val MIN_SCALE = 1f

@Composable
fun CanvasScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CanvasTopBar(
                onGoBack = {
                    navController.popBackStack()
                },
                onPost = {
                    /* TODO: Публикация рисунка */
                },
                onSave = {
                    /* TODO: Сохранение рисунка */
                },
            )
        }
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
            val scale = remember { mutableFloatStateOf(1f) }
            val offset: MutableState<Offset> = remember { mutableStateOf(Offset.Zero) }
            var text by rememberSaveable { mutableStateOf("") }
            val picture = remember { mutableStateOf(Picture()) }
            val drawingHistory = remember { DrawingHistory(picture.value) }
            val interactMode: MutableState<InteractMode> = remember {
                mutableStateOf(InteractMode.DrawState)
            }
            val drawMode: MutableState<DrawMode> = remember {
                mutableStateOf(DrawMode.NoReflect)
            }

            Spacer(modifier = Modifier.height(16.dp))

            DrawingGrid(
                picture = picture,
                padding = 8.dp,
                selectedColor = selectedColor,
                scale = scale,
                offset = offset,
                drawMode = drawMode,
                interactMode = interactMode,
                onUpdateDrawing = {
                    drawingHistory.pushState(it)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DrawingInstrumentsRow(
                interactMode = interactMode.value,
                onBackDrawingButtonClick = {
                    picture.value = drawingHistory.undo() ?: picture.value
                },
                onFurtherDrawingButtonClick = {
                    picture.value = drawingHistory.redo() ?: picture.value
                },
                onDrawButtonClick = {
                    interactMode.value = InteractMode.DrawState
                },
                onEraseModeButtonClick = {
                    interactMode.value = InteractMode.EraseState
                },
                onClearAllButtonClick = {
                    picture.value = Picture.clear(16, 16, Color.Black)
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            ColorPicker(
                selectedColor = selectedColor,
                onColorChange = {
                    selectedColor.value = it
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ControlInstrumentsRow(
                drawMode = drawMode.value,
                interactMode = interactMode.value,
                onReflectVerticallyValueChange = {
                    if (drawMode.value != DrawMode.ReflectHorizontal)
                        drawMode.value = DrawMode.ReflectHorizontal
                    else
                        drawMode.value = DrawMode.NoReflect
                },
                onReflectHorizontallyValueChange = {
                    if (drawMode.value != DrawMode.ReflectVertical)
                        drawMode.value = DrawMode.ReflectVertical
                    else
                        drawMode.value = DrawMode.NoReflect
                },
                onDragValueChange = {
                    interactMode.value = InteractMode.DragState
                },
                onZoomInValueChange = {
                    scale.floatValue = (scale.floatValue * 1.1f).coerceIn(MIN_SCALE, MAX_SCALE)
                },
                onZoomOutValueChange = {
                    scale.floatValue = (scale.floatValue / 1.1f).coerceIn(MIN_SCALE, MAX_SCALE)
                    if (scale.floatValue == MIN_SCALE) offset.value = Offset.Zero
                },
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
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanvasTopBar(
    onGoBack: () -> Unit,
    onPost: () -> Unit,
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
            IconButton(onClick = onPost) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_post),
                    contentDescription = stringResource(R.string.post_drawing_button_description)
                )
            }
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
fun DrawingInstrumentsRow(
    interactMode: InteractMode,
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
                contentDescription = stringResource(R.string.undo_button_description)
            )
        }
        IconButton(onClick = onFurtherDrawingButtonClick) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_draw_further),
                contentDescription = stringResource(R.string.redo_button_description)
            )
        }
        DrawingToggleButton(
            isDrawingModeEnabled = interactMode == InteractMode.DrawState,
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_painter),
            contentDescription = stringResource(R.string.draw_mode_button_description),
            onDrawButtonClick = onDrawButtonClick
        )
        DrawingToggleButton(
            isDrawingModeEnabled = interactMode == InteractMode.EraseState,
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_eraser),
            contentDescription = stringResource(R.string.erase_mode_button_description),
            onDrawButtonClick = onEraseModeButtonClick
        )
        IconButton(onClick = onClearAllButtonClick) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_clean_all),
                contentDescription = stringResource(R.string.clear_button_description)
            )
        }
    }
}

@Composable
fun ControlInstrumentsRow(
    drawMode: DrawMode,
    interactMode: InteractMode,
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
        DrawingToggleButton(
            isDrawingModeEnabled = drawMode == DrawMode.ReflectHorizontal,
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_reflect_vertically),
            contentDescription = stringResource(R.string.reflect_ver_button_description),
            onDrawButtonClick = onReflectVerticallyValueChange
        )
        DrawingToggleButton(
            isDrawingModeEnabled = drawMode == DrawMode.ReflectVertical,
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_reflect_horizontally),
            contentDescription = stringResource(R.string.reflect_hor_button_description),
            onDrawButtonClick = onReflectHorizontallyValueChange
        )
        DrawingToggleButton(
            isDrawingModeEnabled = interactMode == InteractMode.DragState,
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_move_pic),
            contentDescription = stringResource(R.string.drag_button_description),
            onDrawButtonClick = onDragValueChange
        )
        IconButton(onClick = onZoomInValueChange) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_zoom_in),
                contentDescription = stringResource(R.string.zoom_in_button_description)
            )
        }
        IconButton(onClick = onZoomOutValueChange) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_zoom_out),
                contentDescription = stringResource(R.string.zoom_out_button_description)
            )
        }
    }
}

@Composable
fun DrawingToggleButton(
    isDrawingModeEnabled: Boolean,
    imageVector: ImageVector,
    contentDescription: String,
    onDrawButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(
                if (isDrawingModeEnabled)
                    MaterialTheme.colorScheme.secondaryContainer
                else
                    Color.Transparent,
                shape = CircleShape
            )
            .clip(CircleShape)
            .clickable(onClick = onDrawButtonClick)
    ) {
        Icon(
            modifier = Modifier.padding(8.dp),
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = if (isDrawingModeEnabled) MaterialTheme.colorScheme.primary else Color.Black
        )
    }
}

@Composable
@Preview
fun CanvasPreview() {
    val navController = rememberNavController()
    CanvasScreen(navController = navController)
}
