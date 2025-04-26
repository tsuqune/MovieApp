package com.bignerdranch.android.movieapp.model

data class MovieResponse(
    val docs: List<Movie>
)

data class Movie(
    val id: Int,
    val name: String?,
    val poster: Poster?,
    val rating: Rating?,
    val description: String?,
    // Дополнительные поля из ответа API
    val year: Int?,
    val genres: List<Genre>?
)

data class Genre(val name: String?)

data class Poster(
    val url: String?
)

data class Rating(
    val kp: Double?
)