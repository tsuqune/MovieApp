package com.bignerdranch.android.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.movieapp.model.Movie
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class MovieViewModel : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    // Состояния фильтрации
    private val _filterState = MutableStateFlow("top")
    val filterState: StateFlow<String> = _filterState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private var searchJob: Job? = null

    init {
        loadTopMovies() // Инициализация базовым списком
    }

    // Основной метод загрузки данных
    private suspend fun loadData(
        filter: String = _filterState.value,
        query: String = _searchQuery.value
    ) {
        try {
            val response = when {
                query.isNotBlank() ->
                    RetrofitClient.kinopoiskApi.searchAnime(
                        apiKey = RetrofitClient.API_KEY,
                        searchQuery = query
                    )

                filter == "recent" -> {
                    val year = Calendar.getInstance().get(Calendar.YEAR)
                    RetrofitClient.kinopoiskApi.getRecentAnime(
                        apiKey = RetrofitClient.API_KEY,
                        year = "$year"
                    )
                }

                else ->
                    RetrofitClient.kinopoiskApi.getTopRatedMovies(
                        apiKey = RetrofitClient.API_KEY
                    )
            }

            _movies.value = response.docs
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    private suspend fun performSearch(query: String) {
        try {
            val response = RetrofitClient.kinopoiskApi.searchAnime(
                apiKey = RetrofitClient.API_KEY,
                searchQuery = query
            )

            // Фильтрация результатов
            _movies.value = response.docs
                .filter { movie ->
                    movie.type == "anime" ||
                            movie.genres?.any { it.name.equals("аниме", true) } == true
                }
                .sortedByDescending { it.rating?.imdb ?: 0.0 }

        } catch (e: Exception) {
            println("Search error: ${e.message}")
        }
    }

    // Публичные методы
    fun loadTopMovies() {
        _filterState.value = "top"
        _searchQuery.value = ""
        viewModelScope.launch { loadData() }
    }

    fun loadRecentAnime() {
        _filterState.value = "recent"
        _searchQuery.value = ""
        viewModelScope.launch { loadData() }
    }

    fun searchAnime(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300) // Дебаунс для уменьшения запросов
            if (query.isNotEmpty()) {
                performSearch(query)
            } else {

            }
        }
    }
}