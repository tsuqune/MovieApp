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
                println(">>> Загрузка фильмов...")
                val response = RetrofitClient.kinopoiskApi.getPopularMovies()
                println(">>> Получено фильмов: ${response.docs.size}")

                if (response.docs.isEmpty()) {
                    println(">>> Внимание: список фильмов пуст!")
                }

                _movies.value = response.docs
            } catch (e: Exception) {
                println(">>> Ошибка при загрузке: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}