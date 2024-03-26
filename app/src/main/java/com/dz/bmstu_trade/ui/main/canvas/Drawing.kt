package com.dz.bmstu_trade.ui.main.canvas

import androidx.compose.ui.graphics.Color

data class Drawing(
    val rows: Int = 16,
    val columns: Int = 16,
    var pixelColors: List<Color> = List(rows * columns) { Color.Black }
) {
    companion object{
        fun clear(rows: Int, columns: Int,color: Color): Drawing {
            return Drawing(rows, columns, List(rows * columns) { color })
        }
    }

    fun updateCell(x: Int, y: Int, color: Color) {
        if (x in 0 until columns && y in 0 until rows) {
            pixelColors = pixelColors.toMutableList().apply {
                this[y * columns + x] = color
            }
        }
    }

}

