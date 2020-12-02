package com.example.graphiceditor

import android.content.Context

abstract class ToolDrawFigure(context: Context, canvasView: CanvasView) : Tool(context, canvasView) {

    abstract fun drawFigure()

    override fun start() {
        oldBitmap = canvasView.getBitmap()
    }

    override fun draw() {
        canvasView.extraCanvas.drawBitmap(oldBitmap, 0f, -margin, null)
        frame.set(currentX, currentY, motionTouchEventX, motionTouchEventY)
        drawFigure()
    }

    override fun touchUp() {
    }
}