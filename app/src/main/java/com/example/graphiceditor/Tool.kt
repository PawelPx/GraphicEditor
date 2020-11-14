package com.example.graphiceditor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.view.ViewConfiguration

abstract class Tool(context: Context, canvasView: CanvasView, margin: Float) {
    protected val canvasView = canvasView
    protected val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop
    protected val margin = margin
    protected var path = Path()
    protected lateinit var oldBitmap: Bitmap
    protected var frame = RectF()
    protected var motionTouchEventX = 0f
    protected var motionTouchEventY = 0f
    protected var currentX = 0f
    protected var currentY = 0f
    protected var paint = Paint()

    abstract fun start()
    abstract fun draw()
    abstract fun touchUp()

    fun setTool(x: Float, y: Float, p: Paint) {
        motionTouchEventX = x
        motionTouchEventY = y - margin
        paint = p
    }

    fun touchStart() {
        start()
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    fun touchMove() {
        val dx = Math.abs(motionTouchEventX - currentX)
        val dy = Math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            draw()
        }
        canvasView.invalidate()
    }
}