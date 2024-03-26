package com.dz.bmstu_trade.ui.main.canvas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun ColorPicker(
    selectedColor: MutableState<Color>,
    colors: List<Color> = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Magenta,
        Color.Cyan,
        Color.Yellow,
        Color.LightGray
    ),
    onColorChange: (newValue: Color) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        colors.forEach { color ->
            ColorButton(color, selectedColor, onColorChange)
        }
    }

    GradientColorPicker(selectedColor, onColorChange = onColorChange)
}

@Composable
fun ColorButton(
    color: Color,
    selectedColor: MutableState<Color>,
    onColorChange: (color: Color) -> Unit
) {
    val isSelected = color == selectedColor.value

    Box(
        modifier = Modifier
            .size(40.dp)
            .padding(4.dp)
            .border(
                width = 2.dp,
                color = if (isSelected) Color.Black else Color.Transparent,
                shape = RoundedCornerShape(20)
            )
            .background(color, shape = RoundedCornerShape(20))
            .clickable { onColorChange(color) }
    )
}

@Composable
fun GradientColorPicker(
    selectedColor: MutableState<Color>,
    pickerSize: Dp = 40.dp,
    gradientWidth: Dp = 300.dp,
    onColorChange: (color: Color) -> Unit,
) {
    val colors = listOf(
        Color.Red,
        Color.Magenta,
        Color.Blue,
        Color.Cyan,
        Color.Green,
        Color.Yellow,
        Color.Red
    )
    val brush = Brush.horizontalGradient(colors)

    var pickerOffset by remember { mutableFloatStateOf(0f) }

    val density = LocalDensity.current
    val gradientWidthPx = with(density) { gradientWidth.toPx() }
    val pickerSizePx = with(density) { pickerSize.toPx() }

    Box(
        modifier = Modifier
            .height(pickerSize)
            .width(gradientWidth)
            .padding(8.dp)
            .clip(RoundedCornerShape(50))
            .background(brush)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    val newOffset =
                        (pickerOffset + delta).coerceIn(0f, gradientWidthPx - pickerSizePx)
                    pickerOffset = newOffset
                    val fraction = newOffset / gradientWidthPx
                    onColorChange(lerpColorList(colors, fraction))
                }
            )
    ) {
        Box(
            modifier = Modifier
                .size(pickerSize)
                .offset { IntOffset(pickerOffset.toInt(), 0) }
                .background(Color.Transparent, shape = CircleShape)
                .border(2.dp, Color.Black, shape = CircleShape)
        )
    }
}


fun lerpColorList(colors: List<Color>, fraction: Float): Color {
    val s = colors.size - 1
    val index = (s * fraction).toInt().coerceIn(0, s - 1)
    return lerp(colors[index], colors[index + 1], fraction * s % 1)
}

fun lerp(start: Color, stop: Color, fraction: Float): Color {
    return Color(
        lerp(start.red, stop.red, fraction),
        lerp(start.green, stop.green, fraction),
        lerp(start.blue, stop.blue, fraction),
        1f
    )
}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + (stop - start) * fraction
}
