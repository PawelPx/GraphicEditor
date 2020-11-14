package com.example.graphiceditor

import android.content.Context

class Pen(context: Context, canvasView: CanvasView, margin: Float) : Tool(context, canvasView,
    margin) {

    override fun start() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
    }

    override fun draw() {
        path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
        canvasView.extraCanvas.drawPath(path, paint)
    }

    override fun touchUp() {
        path.reset()
    }
}