package com.dz.bmstu_trade.ui.main.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun DrawingGrid(
    modifier: Modifier = Modifier,
    picture: MutableState<Picture> = remember {
        mutableStateOf(Picture())
    },
    padding: Dp = 8.dp,
    gridCellsColor: Color = Color.Black,
    gridLinesColor: Color = Color.LightGray,
    selectedColor: MutableState<Color>,
    scale: MutableState<Float> = remember {
        mutableFloatStateOf(1f)
    },
    offset: MutableState<Offset> = remember {
        mutableStateOf(Offset.Zero)
    },
    interactMode: MutableState<InteractMode> = remember {
        mutableStateOf(InteractMode.DrawState)
    },
    drawMode: MutableState<DrawMode> = remember {
        mutableStateOf(DrawMode.NoReflect)
    },
    onUpdateDrawing: (Picture) -> Unit,
) {
    val aspectRatio = picture.value.columns.toFloat() / picture.value.rows

    with(picture.value) {
        BoxWithConstraints(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio)
                .padding(horizontal = padding)
        ) {
            val cellSize = maxWidth / columns

            Canvas(modifier = Modifier
                .size(cellSize * columns, cellSize * rows)
                .clipToBounds()
                .pointerInput(Unit) {
                    detectTapGestures { tapOffset ->
                        if (interactMode.value != InteractMode.DragState) {
                            val updateDrawing = picture.value.copy()
                            val positions = mutableListOf<Offset>()
                            positions.add(tapOffset)
                            when (drawMode.value) {
                                DrawMode.ReflectHorizontal -> {
                                    positions.add(Offset(size.width - tapOffset.x, tapOffset.y))
                                }

                                DrawMode.ReflectVertical -> {
                                    positions.add(
                                        Offset(
                                            tapOffset.x,
                                            size.height - tapOffset.y
                                        )
                                    )
                                }

                                DrawMode.NoReflect -> {}
                            }
                            for (position in positions) {
                                val x = ((position.x - offset.value.x)
                                            / cellSize.toPx()
                                            / scale.value).toInt()
                                val y = ((position.y - offset.value.y)
                                            / cellSize.toPx()
                                            / scale.value).toInt()
                                if (x in 0 until columns && y in 0 until rows) {
                                    updateDrawing.updateCell(
                                        x,
                                        y,
                                        if (interactMode.value == InteractMode.DrawState)
                                            selectedColor.value
                                        else
                                            gridCellsColor
                                    )
                                }
                            }
                            picture.value = updateDrawing
                            onUpdateDrawing(picture.value)
                        }
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures(onDragEnd = {
                        onUpdateDrawing(picture.value)
                    }) { change, dragAmount ->
                        if (interactMode.value == InteractMode.DragState) {
                            val scaledWidth = maxWidth.toPx() / scale.value
                            val scaledHeight = maxHeight.toPx() / scale.value
                            val maxOffsetX = max(0f, maxWidth.toPx() - scaledWidth)
                            val maxOffsetY = max(0f, maxHeight.toPx() - scaledHeight)
                            val newOffsetX =
                                (offset.value.x + dragAmount.x).coerceIn(-maxOffsetX, 0f)
                            val newOffsetY =
                                (offset.value.y + dragAmount.y).coerceIn(-maxOffsetY, 0f)
                            offset.value = Offset(newOffsetX, newOffsetY)
                            change.consume()
                        } else {
                            val updateDrawing = picture.value.copy()
                            val positions = mutableListOf<Offset>()
                            positions.add(change.position)
                            when (drawMode.value) {
                                DrawMode.ReflectHorizontal -> {
                                    positions.add(
                                        Offset(
                                            size.width - change.position.x,
                                            change.position.y
                                        )
                                    )
                                }
                                DrawMode.ReflectVertical -> {
                                    positions.add(
                                        Offset(
                                            change.position.x,
                                            size.height - change.position.y
                                        )
                                    )
                                }
                                DrawMode.NoReflect -> {}
                            }
                            for (position in positions) {
                                val x = ((position.x - offset.value.x) /
                                            cellSize.toPx() /
                                            scale.value).toInt()
                                val y = ((position.y - offset.value.y) /
                                            cellSize.toPx() /
                                            scale.value).toInt()
                                if (x in 0 until columns && y in 0 until rows) {
                                    updateDrawing.updateCell(
                                        x,
                                        y,
                                        if (interactMode.value == InteractMode.DrawState)
                                            selectedColor.value
                                        else
                                            gridCellsColor
                                    )
                                }
                            }
                            picture.value = updateDrawing
                        }
                    }
                }
            ) {
                with(drawContext.canvas.nativeCanvas) {
                    val checkPoint = save()
                    scale(scale.value, scale.value)
                    translate(offset.value.x, offset.value.y)
                    for (i in 0 until rows) {
                        for (j in 0 until columns) {
                            val color = pixelColors[i * columns + j]
                            drawCell(
                                offset = Offset(j * cellSize.toPx(), i * cellSize.toPx()),
                                size = cellSize.toPx(),
                                color = color
                            )
                        }
                    }
                    for (i in 0..rows) {
                        drawLine(
                            gridLinesColor,
                            start = Offset(0f, i * cellSize.toPx()),
                            end = Offset(columns * cellSize.toPx(), i * cellSize.toPx()),
                            strokeWidth = 0.5.dp.toPx()
                        )
                    }
                    for (j in 0..columns) {
                        drawLine(
                            gridLinesColor,
                            start = Offset(j * cellSize.toPx(), 0f),
                            end = Offset(j * cellSize.toPx(), rows * cellSize.toPx()),
                            strokeWidth = 0.5.dp.toPx()
                        )
                    }
                    restoreToCount(checkPoint)
                }
            }
        }
    }

}

fun DrawScope.drawCell(offset: Offset, size: Float, color: Color) {
    drawRect(
        color = color,
        topLeft = offset,
        size = Size(size, size)
    )
}

sealed class InteractMode {
    object DrawState : InteractMode()
    object EraseState : InteractMode()
    object DragState : InteractMode()
}

sealed class DrawMode {
    object NoReflect : DrawMode()
    object ReflectHorizontal : DrawMode()
    object ReflectVertical : DrawMode()

}