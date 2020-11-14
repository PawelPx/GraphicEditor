package com.example.graphiceditor

import android.content.Context

abstract class ToolDrawFigure(context: Context, canvasView: CanvasView, margin: Float) : Tool(context, canvasView,
    margin) {

    abstract fun drawFigure()

    override fun start() {
        oldBitmap = canvasView.getBitmap()
    }

    override fun draw() {
        canvasView.extraCanvas.drawBitmap(oldBitmap, 0f, 0f, null)
        frame.set(currentX, currentY, motionTouchEventX, motionTouchEventY)
        drawFigure()
    }

    override fun touchUp() {
    }
}