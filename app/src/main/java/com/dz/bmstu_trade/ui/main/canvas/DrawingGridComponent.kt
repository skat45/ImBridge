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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DrawingGrid(
    padding: Dp = 8.dp,
    rows: Int = 10,
    columns: Int = 10,
    gridCellsColor: Color = Color.Black,
    gridLinesColor: Color = Color.LightGray,
    selectedColor: MutableState<Color>,
    eraseMode: MutableState<Boolean> = mutableStateOf(false),
    reflectHorizontalMode: MutableState<Boolean> = mutableStateOf(false),
    reflectVerticalMode: MutableState<Boolean> = mutableStateOf(false),
) {
    val aspectRatio = columns.toFloat() / rows

    val gridModifier = Modifier
        .fillMaxWidth()
        .aspectRatio(aspectRatio)
        .padding(horizontal = padding)

    BoxWithConstraints(gridModifier) {
        val gridColors = remember {
            mutableStateListOf(*Array(rows * columns) { gridCellsColor })
        }
        val gridWidth = maxWidth
        val cellSize = gridWidth / columns

        Canvas(modifier = Modifier
            .size(cellSize * columns, cellSize * rows)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val x = (offset.x / cellSize.toPx()).toInt()
                    val y = (offset.y / cellSize.toPx()).toInt()
                    val index = y * columns + x
                    gridColors[index] =
                        if (!eraseMode.value) selectedColor.value else gridCellsColor
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val positions = mutableListOf<Offset>()
                    positions.add(change.position)
                    if (reflectHorizontalMode.value) {
                        positions.add(Offset(size.width - change.position.x, change.position.y))
                    }
                    if (reflectVerticalMode.value) {
                        positions.add(
                            Offset(
                                change.position.x,
                                size.height - change.position.y
                            )
                        )
                    }
                    for (position in positions) {
                        val x = (position.x / cellSize.toPx()).toInt()
                        val y = (position.y / cellSize.toPx()).toInt()
                        if (x in 0 until columns && y in 0 until rows) {
                            val index = y * columns + x
                            gridColors[index] =
                                if (!eraseMode.value) selectedColor.value else gridCellsColor
                        }
                    }
                }
            }
        ) {
            for (i in 0 until rows) {
                for (j in 0 until columns) {
                    val color = gridColors[i * columns + j]
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