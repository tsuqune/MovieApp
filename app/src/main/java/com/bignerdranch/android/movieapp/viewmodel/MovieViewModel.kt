package com.bignerdranch.android.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.movieapp.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            try {
                println(">>> Запрос: rating.kp=1-10 | API_KEY=${RetrofitClient.API_KEY}")
                val response = RetrofitClient.kinopoiskApi.getMoviesByRating(
                    apiKey = RetrofitClient.API_KEY // Явная передача ключа
                )
                println(">>> Ответ: ${response.docs}")
                _movies.value = response.docs
            } catch (e: Exception) {
                println(">>> Ошибка: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}