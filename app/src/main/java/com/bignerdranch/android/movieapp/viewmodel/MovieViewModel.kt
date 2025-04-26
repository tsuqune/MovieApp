package com.bignerdranch.android.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.movieapp.api.RetrofitClient
import com.bignerdranch.android.movieapp.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    fun loadMovies() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.kinopoiskApi.getPopularMovies(
                    apiKey = RetrofitClient.API_KEY
                )
                _movies.value = response.docs
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}