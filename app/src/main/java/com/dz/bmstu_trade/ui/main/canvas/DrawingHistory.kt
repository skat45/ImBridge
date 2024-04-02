package com.dz.bmstu_trade.ui.main.canvas

class DrawingHistory(picture: Picture) {
    val undoStack: MutableList<Picture> = mutableListOf()
    val redoStack: MutableList<Picture> = mutableListOf()

    init {
        this.pushState(picture)
    }

    fun pushState(state: Picture) {
        undoStack.add(state)
        redoStack.clear()
    }

    fun undo(): Picture? {
        if (undoStack.isNotEmpty()) {
            redoStack.add(undoStack.removeLast())
            return undoStack.lastOrNull()
        }
        return null
    }

    fun redo(): Picture? {
        if (redoStack.isNotEmpty()) {
            val state = redoStack.removeLast()
            undoStack.add(state)
            return state
        }
        return null
    }
}
