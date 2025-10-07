package com.example.myapplication

import android.os.Bundle
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Тема: Layouts
 * 1. Создать приложение, используя AbsoluteLayout. Продемонстрировать недостатки;
 * 2. Создать приложение, используя FrameLayout;
 * 3. Создать приложение, используя LinearLayout (вертикальное и горизонтальное расположение элементов);
 * 4. Создать приложение, используя RelativeLayout;
 * 5. Создать приложение, используя TableLayout. В приложении обязательно должна быть хотя бы одна объединенная по горизонтали ячейка,
 *    (По вертикали нельзя объединять ячеёки, поэтому следующие задачи невозможны)
 *    одна по вертикали, и одна объединенная и по строкам и по столбцам.
 * 6. Создать приложение, при помощи ConstraintLayout;
 * 7. Элемент не доступен -- Создать приложение, при помощи TabLayout;
 * 8. Создать приложение, которое будет пролистываться вниз (в высоту будет больше, чем размер экрана)
 * 9. Создать приложение с разными типами верстки.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val metrics = resources.displayMetrics
        val unitDP = TypedValue.COMPLEX_UNIT_DIP
        val unitSP = TypedValue.COMPLEX_UNIT_SP
        val size = TypedValue.applyDimension(unitDP, 100F, metrics)

        val mainLayout = findViewById<ConstraintLayout>(R.id.main)
        val layoutConstraint = ConstraintLayout.LayoutParams(
            size.toInt() ,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)
            .apply {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToTop = R.id.textView
                verticalBias = 1F
            }

        val textView = TextView(this@MainActivity).apply {
            id = View.generateViewId()
            text = "My text"
            setTextSize(unitSP, 24F)
            setTextColor(0xFFFF0000.toInt())
            setBackgroundColor(0xFF00FF00.toInt())
            layoutParams = layoutConstraint
        }

        mainLayout.addView(textView)
    }
}