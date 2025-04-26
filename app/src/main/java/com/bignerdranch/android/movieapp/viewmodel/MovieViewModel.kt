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

    // Состояние фильтра
    private val _filterState = MutableStateFlow("top") // "top" или "recent"
    val filterState: StateFlow<String> = _filterState

    init {
        loadTopMovies()
    }

    fun loadTopMovies() {
        _filterState.value = "top"
        viewModelScope.launch {
            try {
                val response = RetrofitClient.kinopoiskApi.getTopRatedMovies(
                    apiKey = RetrofitClient.API_KEY
                )
                _movies.value = response.docs
            } catch (e: Exception) {
                println(">>> Ошибка: ${e.message}")
            }
        }
    }

    fun loadRecentAnime() {
        _filterState.value = "recent"
        viewModelScope.launch {
            try {
                val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
                val response = RetrofitClient.kinopoiskApi.getRecentAnime(
                    apiKey = RetrofitClient.API_KEY,
                    year = "${currentYear}"
                )
                _movies.value = response.docs
            } catch (e: Exception) {
                println(">>> Ошибка: ${e.message}")
            }
        }
    }
}