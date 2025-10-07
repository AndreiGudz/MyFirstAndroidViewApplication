package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}