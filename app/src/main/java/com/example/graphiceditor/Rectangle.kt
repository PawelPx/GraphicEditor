package com.example.graphiceditor

import android.content.Context

class Rectangle(context: Context, canvasView: CanvasView) : ToolDrawFigure(context, canvasView) {

    override fun drawFigure() {
        canvasView.extraCanvas.drawRect(frame, paint)
    }
}