package com.example.graphiceditor

import android.content.Context

class Oval(context: Context, canvasView: CanvasView, margin: Float) : ToolDrawFigure(context, canvasView,
    margin) {

    override fun drawFigure() {
        canvasView.extraCanvas.drawOval(frame, paint)
    }
}