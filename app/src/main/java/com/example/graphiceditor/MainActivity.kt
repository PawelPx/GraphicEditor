package com.example.graphiceditor

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val CanvasView = CanvasView(this)
        CanvasView.contentDescription = getString(R.string.canvasContentDescription)
        setContentView(CanvasView)
    }
}

/*val CanvasView = CanvasView(this)
val displayMetrics = DisplayMetrics()
val margin = 100

windowManager.defaultDisplay.getMetrics(displayMetrics)
val screenWidth = displayMetrics.widthPixels
val screenHeight = displayMetrics.heightPixels

CanvasView.contentDescription = getString(R.string.canvasContentDescription)
var layoutParams = ActionBar.LayoutParams(screenWidth - 2 * margin, screenHeight - 2 * margin)
layoutParams.setMargins(margin, margin, 0, 0)
CanvasView.setLayoutParams(layoutParams)
setContentView(CanvasView)*/
