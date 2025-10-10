package com.example.myapplication

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
    var firstDigit: Int = 0
    var inputNumber: Int = 0
    var operator = ""

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

    @SuppressLint("SetTextI18n")
    private fun addMyViews() {
        val metrics = resources.displayMetrics
        val unitSP = TypedValue.COMPLEX_UNIT_SP
        val unitDP = TypedValue.COMPLEX_UNIT_DIP
        val mainLayout = findViewById<ConstraintLayout>(R.id.main)

        val textViewsWithDigit = topPath(unitSP, metrics, mainLayout)


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
                columnCount = 4
                rowCount = 4
            }
        mainLayout.addView(gridLayout)

        val answerTextView = TextView(this@MainActivity).apply {
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
                .apply {
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToTop = gridLayout.id
                }
            text = "0"
            textSize = TypedValue.applyDimension(unitSP, 12F, metrics)
            setBackgroundColor(Color.GREEN)
        }
        mainLayout.addView(answerTextView)

        val gridButtons = List(gridLayout.columnCount * 3 + 1) {
            Button(this@MainActivity)
                .apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0
                        height = GridLayout.LayoutParams.WRAP_CONTENT
                        columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                        rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    }
                }
        }

        addButtonsOnGridLayout(gridLayout, gridButtons)

        addListenersForGridButtons(gridButtons, answerTextView)

        setStyles(textViewsWithDigit, gridButtons, unitSP, unitDP, metrics)
    }

    @SuppressLint("SetTextI18n")
    private fun addListenersForGridButtons(
        gridButtons: List<Button>,
        answerTextView: TextView
    ) {
        gridButtons.forEach { button ->
            when {
                button.text.isDigitsOnly() -> {
                    button.setOnClickListener {
                        //Toast.makeText(this@MainActivity, button.text.toString(), Toast.LENGTH_SHORT).show()

                        if (inputNumber == 0 && firstDigit == 0)
                            answerTextView.text = ""

                        val buttonDigit = button.text.toString().toInt()
                        if (inputNumber == 0)
                            inputNumber = buttonDigit
                        else {
                            inputNumber = inputNumber * 10 + buttonDigit
                        }

                        if (inputNumber != 0)
                            answerTextView.text = "${answerTextView.text}$buttonDigit"
                    }
                }

                button.text == "+" || button.text == "-" -> {
                    button.setOnClickListener {
                        //Toast.makeText(this@MainActivity, button.text.toString(), Toast.LENGTH_SHORT).show()

                        if (inputNumber != 0 && firstDigit == 0) {
                            firstDigit = inputNumber
                            operator = button.text.toString()
                            inputNumber = 0
                            answerTextView.text = "${answerTextView.text}${button.text}"
                        }
                    }
                }

                button.text == "=" -> {
                    button.setOnClickListener {
                        //Toast.makeText(this@MainActivity, "=", Toast.LENGTH_SHORT).show()

                        val answer = when (operator) {
                            "" -> inputNumber
                            "+" -> firstDigit + inputNumber
                            "-" -> firstDigit - inputNumber
                            else -> 0
                        }
                        firstDigit = 0
                        inputNumber = 0
                        operator = ""
                        answerTextView.text = "$answer"
                    }
                }
            }
        }
    }

    private fun setStyles(
        textViewsWithDigit: List<TextView>,
        gridButtons: List<Button>,
        unitSP: Int,
        unitDP: Int,
        metrics: DisplayMetrics?
    ) {
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
        gridButtons.forEach {
            it.setPadding(TypedValue.applyDimension(unitDP, 12F, metrics).toInt())
            it.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
            it.setTextColor(Color.BLACK)
            it.width = ActionBar.LayoutParams.MATCH_PARENT
            it.textSize = TypedValue.applyDimension(unitSP, 12F, metrics)
        }
    }

    private fun addButtonsOnGridLayout(
        gridLayout: GridLayout,
        gridButtons: List<Button>
    ) {
        var digit = 0
        gridButtons.forEachIndexed { index, button ->
            if (index == 0)
                return@forEachIndexed
            if ((index - 1) % 4 < 3) {
                button.text = (++digit).toString()
            } else {
                when ((index - 1) / 4) {
                    0 -> button.text = "+"
                    1 -> button.text = "-"
                    2 -> {
                        button.text = "="
                        button.layoutParams = GridLayout.LayoutParams().apply {
                            width = 0
                            height = GridLayout.LayoutParams.WRAP_CONTENT
                            rowSpec = GridLayout.spec(2, 2, 1F)
                            columnSpec = GridLayout.spec(3, 1F)
                        }
                    }
                }
            }
            gridLayout.addView(button)
        }

        val layoutParamsForZeroDigit = GridLayout.LayoutParams()
            .apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                rowSpec = GridLayout.spec(3, 1F)
                columnSpec = GridLayout.spec(0, 3, 1F)
            }

        gridButtons[0].text = 0.toString()
        gridButtons[0].layoutParams = layoutParamsForZeroDigit
        gridLayout.addView(gridButtons[0])
    }

    private fun topPath(
        unitSP: Int,
        metrics: DisplayMetrics,
        mainLayout: ConstraintLayout
    ): List<TextView> {
        val topLinearLayout = LinearLayout(this@MainActivity)
            .apply {
                layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                    .apply {
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    }
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
            }
        mainLayout.addView(topLinearLayout)

        val textView = TextView(this@MainActivity)
            .apply {
                id = View.generateViewId()
                text = "Hello Programmed-View!"
                textSize = TypedValue.applyDimension(unitSP, 12F, metrics)
                layoutParams = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
            }
        topLinearLayout.addView(textView)


        val digitLinearLayout = LinearLayout(this@MainActivity)
            .apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                setBackgroundColor(Color.BLUE)
                gravity = Gravity.CENTER
            }
        topLinearLayout.addView(digitLinearLayout)

        // список textView с цифрами 0-9
        val digitCount = 10
        val textViewsWithDigit = List(digitCount) { index ->
            TextView(this@MainActivity)
                .apply {
                    id = View.generateViewId()
                    text = index.toString()
                }
        }

        (0 until digitCount).forEach {
            digitLinearLayout.addView(textViewsWithDigit[it])
        }
        return textViewsWithDigit
    }
}