package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

/**
 * Практическая работа №13.
 * Тема: Компоненты Views
 * 1. Вывести Hello world!;
 * 2. Вывести 5 надписей по очереди, с паузой в 1 секунду;
 * 3. Добавить кнопку. Менять надписи по нажатию кнопки;
 * 4. Добавить кнопку с картинкой с тем же функционалом;
 * 5. Поменять цвета у всех элементов;
 * 6. Добавить картинку на задний фон;
 * 7. Сделать опрос с несколькими вариантами ответа. Выводить правильно ли выбраны ответы или нет;
 * 8. Сделать программу, которая эмулирует работу этой gif.
 *
 */
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

        startThreadForAllElementsCicleColorChanging()
        startThreadForFiveTextViewsAdding()
    }

    private fun startThreadForFiveTextViewsAdding() {
        val linearLayout = findViewById<LinearLayout>(R.id.mainVerticalLinearLayout)
        val threadTextViewsAdding = Thread {
            repeat(5) { index ->
                runOnUiThread {
                    linearLayout.run {
                        addView(TextView(this@MainActivity).apply {
                            setText("TextView + ${index + 1}")
                            textSize = 24.0F
                        })
                    }
                }

                Thread.sleep(1000L)
            }
        }
        threadTextViewsAdding.start()
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun startThreadForAllElementsCicleColorChanging() {
        val threadCicleColorChanging = Thread {
            val layouts = listOf(
                findViewById<ConstraintLayout>(R.id.main),
                findViewById<LinearLayout>(R.id.mainVerticalLinearLayout)
            )

            val elements = listOf(
                findViewById<TextView>(R.id.textViewHelloWorld),
                findViewById<Button>(R.id.clickCountButton),
                findViewById<TextView>(R.id.textViewCreatedBy),
                findViewById<TextView>(R.id.textViewDate),
            )

            val colors = listOf(
                0xFFFF0000.toInt(), // Красный
                0xFFFFA500.toInt(), // Оранжевый
                0xFFFFFF00.toInt(), // Желтый
                0xFF00FF00.toInt(), // Зеленый
                0xFF00FFFF.toInt(), // Голубой
                0xFF0000FF.toInt(), // Синий
                0xFF800080.toInt()  // Фиолетовый
            )
            var i = 0
            while (true) {
                val colorIndex = i++
                val randomColor = colors[colorIndex % colors.size]
                Log.d(
                    "Color Change",
                    "Color changing on ${randomColor.toHexString(HexFormat.UpperCase)}"
                )
                runOnUiThread {
                    layouts.forEach {
                        it.setBackgroundColor(randomColor)
                    }
                    elements.forEach {
                        it.setTextColor(colors[(colorIndex + 3) % colors.size])
                    }
                }
                Thread.sleep(1000)
            }
        }
        threadCicleColorChanging.start()
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