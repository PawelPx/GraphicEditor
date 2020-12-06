package com.example.graphiceditor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint

class Rubber(context: Context, canvasView: CanvasView) : Tool(context, canvasView) {
    private val rubber = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = paint.strokeWidth * 3
    }

    override fun start() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        rubber.strokeWidth = paint.strokeWidth * 3
    }

    override fun draw() {
        path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
        canvasView.extraCanvas.drawPath(path, rubber)
    }

    override fun touchUp() {
        path.reset()
    }
}