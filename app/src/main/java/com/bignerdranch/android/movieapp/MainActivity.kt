package com.bignerdranch.android.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.bignerdranch.android.movieapp.ui.screens.MovieListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(">>> Приложение запущено") // Лог
        setContent {
            MaterialTheme {
                Surface {
                    MovieListScreen()
                }
            }
        }
    }
}