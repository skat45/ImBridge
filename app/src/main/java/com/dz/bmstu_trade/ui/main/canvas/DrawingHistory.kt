package com.dz.bmstu_trade.ui.main.canvas

class DrawingHistory(drawing: Drawing) {
    val undoStack: MutableList<Drawing> = mutableListOf()
    val redoStack: MutableList<Drawing> = mutableListOf()

    init {
        this.pushState(drawing)
    }

    fun pushState(state: Drawing) {
        undoStack.add(state)
        redoStack.clear()
    }

    fun undo(): Drawing? {
        if (undoStack.isNotEmpty()) {
            redoStack.add(undoStack.removeLast())
            return undoStack.lastOrNull()
        }
        return null
    }

    fun redo(): Drawing? {
        if (redoStack.isNotEmpty()) {
            val state = redoStack.removeLast()
            undoStack.add(state)
            return state
        }
        return null
    }
}
