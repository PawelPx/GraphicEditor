package com.example.graphiceditor

import android.content.Context

class Rectangle(context: Context, canvasView: CanvasView, margin: Float) : ToolDrawFigure(context, canvasView,
    margin) {

    override fun drawFigure() {
        canvasView.extraCanvas.drawRect(frame, paint)
    }
}