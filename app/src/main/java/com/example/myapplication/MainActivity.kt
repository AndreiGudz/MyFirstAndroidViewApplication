package com.example.myapplication

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import androidx.core.view.setPadding

/**
 * Практическая работа №16.
 * Тема: Программное создание элементов Views
 * В ходе выполнения работы ЗАПРЕЩАЕТСЯ трогать файлы xml.
 * 1. Создать TextView с текстом "Hello Programmed-View!";
 * 2. Создать циклом 10 TextView с текстом (от 0 до 9);
 * 3. Создать 10 кнопок;
 * 4. Создать калькулятор из кнопок (при помощи GridLayout);
 * 5. Создать калькулятор из кнопок циклом;
 * 6. Циклом применить стили ко всем кнопкам/TextView.
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

        addMyViews()
    }

    private fun addMyViews() {
        val metrics = resources.displayMetrics
        val unitSP = TypedValue.COMPLEX_UNIT_SP
        val unitDP = TypedValue.COMPLEX_UNIT_DIP
        val mainLayout = findViewById<ConstraintLayout>(R.id.main)

        // textView
        val textViewLayoutsParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
            .apply {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            }

        val textView = TextView(this@MainActivity)
            .apply {
                id = View.generateViewId()
                text = "Hello Programmed-View!"
                textSize = TypedValue.applyDimension(unitSP, 12F, metrics)
                layoutParams = textViewLayoutsParams
            }
        mainLayout.addView(textView)

        // linearLayout
        val linearLayoutLayoutsParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
            .apply {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                topToBottom = textView.id
            }

        val linearLayout = LinearLayout(this@MainActivity)
            .apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = linearLayoutLayoutsParams
            }
        mainLayout.addView(linearLayout)

        // список textView с цифрами 0-9
        val textViewsWithDigit = List(10) { index ->
            TextView(this@MainActivity)
                .apply {
                    text = index.toString()
                }
        }

        (0..9).forEach {
            linearLayout.addView(textViewsWithDigit[it])
        }

        // gridLayout
        val gridLayoutLayoutsParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
            .apply {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }

        // Поле 3 на 3 с цифрами 1-9 и 0 внизу отдельно на весь ряд
        val gridLayout = GridLayout(this@MainActivity)
            .apply {
                id = View.generateViewId()
                layoutParams = gridLayoutLayoutsParams
                columnCount = 3
                rowCount = 4
            }
        mainLayout.addView(gridLayout)

        val digitButtons = List(10) {
            Button(this@MainActivity)
                .apply {
                    text = it.toString()
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0
                        height = GridLayout.LayoutParams.WRAP_CONTENT
                        columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                        rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    }
                }
        }

        (1..9).forEach {
            gridLayout.addView(digitButtons[it])
        }
        val layoutParamsForZeroDigit = GridLayout.LayoutParams()
            .apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                rowSpec = GridLayout.spec(3, 1F)
                columnSpec = GridLayout.spec(0, 3, 1F)
            }

        digitButtons[0].layoutParams = layoutParamsForZeroDigit
        gridLayout.addView(digitButtons[0])

        textViewsWithDigit.forEach {
            it.setPadding(TypedValue.applyDimension(unitDP, 8F, metrics).toInt())
            it.layoutParams = LinearLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
            )
                .apply {
                    setMargins(TypedValue.applyDimension(unitDP, 8F, metrics).toInt())
                }
            it.setBackgroundColor(Color.CYAN)
        }
        digitButtons.forEach {
            it.setPadding(TypedValue.applyDimension(unitDP, 12F, metrics).toInt())
            it.backgroundTintList = ColorStateList.valueOf(Color.BLUE)
            it.setTextColor(Color.RED)
            it.width = ActionBar.LayoutParams.MATCH_PARENT
        }
    }
}