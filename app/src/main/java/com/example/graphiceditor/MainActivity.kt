package com.example.graphiceditor

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.colorpicker.*
import kotlinx.android.synthetic.main.load_file.*
import kotlinx.android.synthetic.main.save_file.*
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val canvasView = findViewById<CanvasView>(R.id.canvasView)
        val pen = Pen(this, canvasView)
        val rectangle = Rectangle(this, canvasView)
        val oval = Oval(this, canvasView)
        var selectedElement = "";

        val listView = findViewById<ListView>(R.id.recipe_list_view)
        val colorSelector = findViewById<RelativeLayout>(R.id.colorSelector)
        val saveFile = findViewById<RelativeLayout>(R.id.saveFile)
        val loadFile = findViewById<RelativeLayout>(R.id.loadFile)
        val strColor = findViewById<EditText>(R.id.strColor)
        val colorButton : Button = findViewById(R.id.colorButton);
        val toolButton : Button = findViewById(R.id.toolButton);
        val optionsButton : Button = findViewById(R.id.optionsButton);
        colorButton.setOnClickListener {
            colorSelector.visibility = View.VISIBLE;
        }
        toolButton.setOnClickListener {
            val toolsMenu: PopupMenu = PopupMenu(this, toolButton)
            toolsMenu.menuInflater.inflate(R.menu.tool_selector, toolsMenu.menu)
            toolsMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.pen ->
                        canvasView.setActiveTool(pen)
                    R.id.rectangle ->
                        canvasView.setActiveTool(rectangle)
                    R.id.oval ->
                        canvasView.setActiveTool(oval)
                }
                true
            })
            toolsMenu.show()
        }

        optionsButton.setOnClickListener {
            val optionsMenu: PopupMenu = PopupMenu(this, optionsButton)
            optionsMenu.menuInflater.inflate(R.menu.options, optionsMenu.menu)
            optionsMenu.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.save -> saveFile.visibility = View.VISIBLE;
                        R.id.load -> {
                            val m = packageManager
                            var s = packageName
                            val p = m.getPackageInfo(s!!, 0)
                            s = p.applicationInfo.dataDir
                            val list = mutableListOf<String>()

                            File(s).listFiles().forEach {// 3
                                list.add(it.name);
                            }
                            val adapter = ArrayAdapter(
                                loadFile.context,
                                android.R.layout.simple_list_item_1,
                                list.toTypedArray()
                            )
                            listView.adapter = adapter
                            loadFile.visibility = View.VISIBLE
                        }
                    }
                    return true;
                }
            })
            optionsMenu.show()
        }

        saveOk.setOnClickListener {
            val m = packageManager
            var s = packageName
            val p = m.getPackageInfo(s!!, 0)
            s = p.applicationInfo.dataDir
            var bitmap = canvasView.getBitmap()
            val file = File(s, strFileName.text.toString() + ".jpg");
            val fOut = FileOutputStream(file)
            val result = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

            var builder = AlertDialog.Builder(this)
            if(result)
            {
                builder.setTitle("Success");
                builder.setMessage("File save successful");
            }
            else
            {
                builder.setTitle("Failure");
                builder.setMessage("File save failed");
            }
            builder.show();
            saveFile.visibility = View.GONE;
        }

        saveCancel.setOnClickListener {
            saveFile.visibility = View.GONE;
        }

        loadOk.setOnClickListener {
            val m = packageManager
            var s = packageName
            val p = m.getPackageInfo(s!!, 0)
            s = p.applicationInfo.dataDir
            val file = File(s, strFileName.text.toString() + ".jpg");
            val bMap = BitmapFactory.decodeFile("$s/$selectedElement");
            canvasView.extraCanvas.drawBitmap(bMap, 0f, -200f, null)
            loadFile.visibility = View.GONE;
        }

        loadCancel.setOnClickListener {
            loadFile.visibility = View.GONE;
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            selectedElement = parent.getItemAtPosition(position).toString() // The item that was clicked
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
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                val colorStr = getColorString()
                strColor.setText(colorStr.replace("#", "").toUpperCase())
                btnColorPreview.setBackgroundColor(Color.parseColor(colorStr))
            }
        })

        colorR.max = 255
        colorR.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                val colorStr = getColorString()
                strColor.setText(colorStr.replace("#", "").toUpperCase())
                btnColorPreview.setBackgroundColor(Color.parseColor(colorStr))
            }
        })

        colorG.max = 255
        colorG.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                val colorStr = getColorString()
                strColor.setText(colorStr.replace("#", "").toUpperCase())
                btnColorPreview.setBackgroundColor(Color.parseColor(colorStr))
            }
        })

        colorB.max = 255
        colorB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                val colorStr = getColorString()
                strColor.setText(colorStr.replace("#", "").toUpperCase())
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.tool_selector, menu)
        return true
    }

    fun getColorString(): String {
        var a = Integer.toHexString(((255 * colorA.progress) / colorA.max))
        if(a.length==1) a = "0"+a
        var r = Integer.toHexString(((255 * colorR.progress) / colorR.max))
        if(r.length==1) r = "0"+r
        var g = Integer.toHexString(((255 * colorG.progress) / colorG.max))
        if(g.length==1) g = "0"+g
        var b = Integer.toHexString(((255 * colorB.progress) / colorB.max))
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
