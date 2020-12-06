package com.example.graphiceditor

import android.content.Context

class Line(context: Context, canvasView: CanvasView) : ToolDrawFigure(context, canvasView) {
    override fun drawFigure() {
        canvasView.extraCanvas.drawLine(currentX, currentY, motionTouchEventX, motionTouchEventY, paint)
    }
}