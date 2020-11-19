package com.example.graphiceditor

import android.app.ActionBar
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.colorpicker.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val canvasView = findViewById<com.example.graphiceditor.CanvasView>(R.id.canvasView)

        val colorSelector = findViewById<RelativeLayout>(R.id.colorSelector)
        val strColor = findViewById<EditText>(R.id.strColor)
        val button : Button = findViewById(R.id.button);
        button.setOnClickListener {
            colorSelector.visibility = View.VISIBLE;
        }

        strColor.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.length == 6) {
                    colorA.progress = 255
                    colorR.progress = Integer.parseInt(s.substring(0..1), 16)
                    colorG.progress = Integer.parseInt(s.substring(2..3), 16)
                    colorB.progress = Integer.parseInt(s.substring(4..5), 16)
                } else if (s.length == 8) {
                    colorA.progress = Integer.parseInt(s.substring(0..1), 16)
                    colorR.progress = Integer.parseInt(s.substring(2..3), 16)
                    colorG.progress = Integer.parseInt(s.substring(4..5), 16)
                    colorB.progress = Integer.parseInt(s.substring(6..7), 16)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

                }
            })

            colorA.max = 255
            colorA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onProgressChanged(seekBar: SeekBar, progress: Int,
                                               fromUser: Boolean) {
                    val colorStr = getColorString()
                    strColor.setText(colorStr.replace("#","").toUpperCase())
                    btnColorPreview.setBackgroundColor(Color.parseColor(colorStr))
                }
            })

            colorR.max = 255
            colorR.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onProgressChanged(seekBar: SeekBar, progress: Int,
                                               fromUser: Boolean) {
                    val colorStr = getColorString()
                    strColor.setText(colorStr.replace("#","").toUpperCase())
                    btnColorPreview.setBackgroundColor(Color.parseColor(colorStr))
                }
            })

            colorG.max = 255
            colorG.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onProgressChanged(seekBar: SeekBar, progress: Int,
                                               fromUser: Boolean) {
                    val colorStr = getColorString()
                    strColor.setText(colorStr.replace("#","").toUpperCase())
                    btnColorPreview.setBackgroundColor(Color.parseColor(colorStr))
                }
            })

            colorB.max = 255
            colorB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onProgressChanged(seekBar: SeekBar, progress: Int,
                                               fromUser: Boolean) {
                    val colorStr = getColorString()
                    strColor.setText(colorStr.replace("#","").toUpperCase())
                    btnColorPreview.setBackgroundColor(Color.parseColor(colorStr))
                }
            })

            colorCancelBtn.setOnClickListener {
                colorSelector.visibility = View.GONE
            }

            colorOkBtn.setOnClickListener {
                val color:String = getColorString()
                linearLayout.setBackgroundColor(Color.parseColor(color))
                canvasView.setColor(color);
                colorSelector.visibility = View.GONE
            }
    }

    fun getColorString(): String {
        var a = Integer.toHexString(((255*colorA.progress)/colorA.max))
        if(a.length==1) a = "0"+a
        var r = Integer.toHexString(((255*colorR.progress)/colorR.max))
        if(r.length==1) r = "0"+r
        var g = Integer.toHexString(((255*colorG.progress)/colorG.max))
        if(g.length==1) g = "0"+g
        var b = Integer.toHexString(((255*colorB.progress)/colorB.max))
        if(b.length==1) b = "0"+b
        return "#" + a + r + g + b
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
