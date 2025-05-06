package com.bignerdranch.android.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.movieapp.data.AppDatabase
import com.bignerdranch.android.movieapp.model.Movie
import com.bignerdranch.android.movieapp.model.WatchedAnime
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar

class MovieViewModel(private val db: AppDatabase) : ViewModel() {
    // Основной список фильмов
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    // Состояния фильтрации
    private val _filterState = MutableStateFlow("top")
    val filterState: StateFlow<String> = _filterState

    // Поисковый запрос
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Просмотренные аниме
    private val _watchedAnime = MutableStateFlow<List<WatchedAnime>>(emptyList())
    val watchedAnime: StateFlow<List<WatchedAnime>> = _watchedAnime

    private var searchJob: Job? = null

    init {
        loadTopMovies()
        loadWatchedAnime()
    }

    // Загрузка просмотренных аниме из БД
    private fun loadWatchedAnime() {
        viewModelScope.launch {
            db.watchedAnimeDao().getAll().collect { list ->
                _watchedAnime.value = list
            }
        }
    }

    // Новый метод: Получение фильма по ID
    fun getMovieById(movieId: String): StateFlow<Movie?> {
        return _movies
            .map { movies -> movies.find { it.id == movieId.toInt() } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    }

    // Новый метод: Загрузка деталей фильма
    fun loadMovieDetails(movieId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.kinopoiskApi.getMovieDetails(
                    apiKey = RetrofitClient.API_KEY,
                    movieId = movieId.toInt() // Конвертация в Int если требуется
                )
                _movies.update { currentList ->
                    currentList.toMutableList().apply {
                        removeAll { it.id == movieId.toInt() }
                        add(response)
                    }
                }
            } catch (e: Exception) {
                println("Error loading details: ${e.message}")
            }
        }
    }

    // Оригинальные методы
    private suspend fun loadData(filter: String = _filterState.value, query: String = _searchQuery.value) {
        try {
            val response = when {
                query.isNotBlank() -> RetrofitClient.kinopoiskApi.searchAnime(
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
                else -> RetrofitClient.kinopoiskApi.getTopRatedMovies(apiKey = RetrofitClient.API_KEY)
            }
            _movies.value = response.docs
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

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
            delay(300)
            if (query.isNotEmpty()) performSearch(query)
        }
    }

    private suspend fun performSearch(query: String) {
        try {
            val response = RetrofitClient.kinopoiskApi.searchAnime(
                apiKey = RetrofitClient.API_KEY,
                searchQuery = query
            )
            _movies.value = response.docs
                .filter { it.type == "anime" || it.genres?.any { g -> g.name.equals("аниме", true) } == true }
                .sortedByDescending { it.rating?.imdb ?: 0.0 }
        } catch (e: Exception) {
            println("Search error: ${e.message}")
        }
    }

    fun toggleWatchedStatus(movie: Movie) {
        viewModelScope.launch {
            val existing = db.watchedAnimeDao().getById(movie.id)
            if (existing != null) {
                db.watchedAnimeDao().delete(existing)
            } else {
                val watchedAnime = WatchedAnime(
                    id = movie.id,
                    name = movie.name,
                    posterUrl = movie.poster?.url,
                    rating = movie.rating?.imdb,
                    year = movie.year,
                    genresJson = Gson().toJson(movie.genres),
                    description = movie.description
                )
                db.watchedAnimeDao().insert(watchedAnime)
            }
            loadWatchedAnime() // Обновляем список после изменения
        }
    }
}