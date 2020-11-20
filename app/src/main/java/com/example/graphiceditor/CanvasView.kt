package com.example.graphiceditor

import android.app.ActionBar
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.Button
import androidx.core.content.res.ResourcesCompat


private const val STROKE_WIDTH = 12f

class CanvasView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private lateinit var activeTool: Tool
    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    private var drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)
    val topMargin = 200f

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        extraBitmap = Bitmap.createBitmap(width, height - topMargin.toInt(), Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)

        // set tool
        activeTool = Pen(context, this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap, 0f, topMargin, null)
    }

    fun getBitmap(): Bitmap {
        this.isDrawingCacheEnabled = true
        this.buildDrawingCache()
        val bmp = Bitmap.createBitmap(this.drawingCache)
        this.isDrawingCacheEnabled = false
        return bmp
    }

    fun setColor(color: String){
        invalidate();
        this.drawColor = Color.parseColor(color);
        paint.color = this.drawColor;
    }

    // set paint
    private val paint = Paint().apply {
        color = drawColor
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }

    fun setActiveTool(tool: Tool) {
        activeTool = tool
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        activeTool.setTool(event.x, event.y, paint)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> activeTool.touchStart()
            MotionEvent.ACTION_MOVE -> activeTool.touchMove()
            MotionEvent.ACTION_UP -> activeTool.touchUp()
        }
        return true
    }

}