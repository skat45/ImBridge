package com.dz.bmstu_trade.ui.main.canvas

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

data class Picture(
    val rows: Int = 16,
    val columns: Int = 16,
    var pixelColors: List<Color> = List(rows * columns) { Color.Black }
) {
    companion object{
        fun clear(rows: Int, columns: Int,color: Color): Picture {
            return Picture(rows, columns, List(rows * columns) { color })
        }
    }

    fun updateCell(x: Int, y: Int, color: Color) {
        if (x in 0 until columns && y in 0 until rows) {
            pixelColors = pixelColors.toMutableList().apply {
                this[y * columns + x] = color
            }
        }
    }

    fun getArgbList(): List<List<Int>> {
        return List(rows) { y ->
            List(columns) { x ->
                val index = y * columns + x
                pixelColors[index].toArgb()
            }
        }
    }
}

