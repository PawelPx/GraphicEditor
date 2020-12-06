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
import kotlinx.android.synthetic.main.activity_main.view.*



class CanvasView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private lateinit var activeTool: Tool
    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    private var drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)
    var STROKE_WIDTH = 12f
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

    fun setBitmap(bitmap: Bitmap){
        this.extraBitmap = bitmap
        //this.draw(this.extraCanvas)
    }

    fun setColor(color: String){
        invalidate()
        this.drawColor = Color.parseColor(color)
        paint.color = this.drawColor
    }

    fun setThickness(thickness: Float){
        this.STROKE_WIDTH = thickness
        paint.strokeWidth = STROKE_WIDTH
    }

    fun setFilling(filling: Boolean){
        if(filling)
            paint.style = Paint.Style.FILL_AND_STROKE
        else
            paint.style = Paint.Style.STROKE
    }

    // set paint
    private val paint = Paint().apply {
        color = drawColor
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = STROKE_WIDTH
    }

    fun setActiveTool(tool: Tool) {
        activeTool = tool
    }

    fun clear() {
        extraCanvas.drawColor(Color.WHITE)
        invalidate()
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