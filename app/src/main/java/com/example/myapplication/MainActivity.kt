package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bottomTextViewsCicleColorChanging()

    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun bottomTextViewsCicleColorChanging() {
        val myThread = Thread {
            val view1 = findViewById<TextView>(R.id.textViewCreatedBy)
            val view2 = findViewById<TextView>(R.id.textViewDate)

            val colors = listOf(
                0xFF000000.toInt(), 0xFFFF0000.toInt(),
                0xFF00FF00.toInt(), 0xFF0000FF.toInt()
            )
            while (true) {
                val randomColor = colors.random()
                Log.d(
                    "Color Change",
                    "Color changing on ${randomColor.toHexString(HexFormat.UpperCase)}"
                )
                runOnUiThread {
                    view1.setTextColor(randomColor)
                    view2.setTextColor(randomColor)
                }
                Thread.sleep(1000)
            }
        }
        myThread.start()
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifecycle", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("lifecycle", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("lifecycle", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifecycle", "onDestroy")
    }
}