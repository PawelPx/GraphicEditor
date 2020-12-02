package com.example.graphiceditor

import android.content.Context

class Oval(context: Context, canvasView: CanvasView) : ToolDrawFigure(context, canvasView) {

    override fun drawFigure() {
        canvasView.extraCanvas.drawOval(frame, paint)
    }
}