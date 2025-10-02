package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.chip.Chip

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
 */


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startThreadForFiveTextViewsAdding()

//         Коментарии остальись от неправильно понятого задания, а удалять жалко

//        var clickCount = 0
//        findViewById<Button>(R.id.clickCountButton)
//            .setOnClickListener {
//            (it as Button).text = "${getString(R.string.buttonText_clickCount)} ${++clickCount}"
//        }
//
//        var btn_star_big_state = false
//        findViewById<ImageButton>(R.id.imageButton)
//            .setOnClickListener {
//                val btn = it as ImageButton
//                btn_star_big_state = !btn_star_big_state
//                btn.setImageResource(
//                    if (btn_star_big_state)
//                        android.R.drawable.btn_star_big_on
//                    else
//                        android.R.drawable.btn_star_big_off
//                )
//            }
    }

    fun HelloWorldChangeOnClick(v: View): Unit {
        val textViewHelloWorld = findViewById<TextView>(R.id.textViewHelloWorld)
        textViewHelloWorld.text = "${textViewHelloWorld.text}!"
    }

    fun chips_onClick(v: View): Unit {
        val chip = v as Chip
        val answererTextView = findViewById<TextView>(R.id.answererTextView)
        if (chip == findViewById<Chip>(R.id.chip1))
        {
            answererTextView.text = "Верно, я крутой"
        }
        else
        {
            answererTextView.text = "Неверно, Я крутой, а не ${chip.text}"
        }

    }

    val countingTextViews = mutableListOf<TextView>()
    private fun startThreadForFiveTextViewsAdding() {
        val linearLayout = findViewById<LinearLayout>(R.id.mainVerticalLinearLayout)
        val secondCount = 5
        val threadTextViewsAdding = Thread {
            repeat(secondCount) { index ->
                val textView = TextView(this@MainActivity).apply {
                    setText("До начала психодела ${secondCount - index}")
                    textSize = 24.0F
                }
                countingTextViews.add(textView)

                runOnUiThread {
                    linearLayout.run {
                        addView(textView)
                    }
                    Log.d("Add TextView", "add TextView with ${index + 1} index")
                }

                Thread.sleep(1000L)
            }
            startThreadForAllElementsCicleColorChanging()
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
                findViewById<TextView>(R.id.answererTextView),
                findViewById<Chip>(R.id.chip1),
                findViewById<Chip>(R.id.chip2),
                findViewById<Chip>(R.id.chip3),
                *(countingTextViews.toTypedArray()),

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