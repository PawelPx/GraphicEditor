package com.example.graphiceditor

import android.content.Context
import android.graphics.Paint

class Pen(context: Context, canvasView: CanvasView) : Tool(context, canvasView) {
    private val penPaint = Paint().apply {
        color = paint.color
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = paint.strokeWidth
    }

    override fun start() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        penPaint.color = paint.color
        penPaint.strokeWidth = paint.strokeWidth
    }

    override fun draw() {
        path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
        canvasView.extraCanvas.drawPath(path, penPaint)
    }

    override fun touchUp() {
        path.reset()
    }
}