package com.bignerdranch.android.movieapp.model

data class MovieResponse(
    val docs: List<Movie>
)

data class Movie(
    val id: Int,
    val name: String?,
    val alternativeName: String?,
    val description: String?,
    val rating: Rating?,
    val poster: Poster?
)

data class Rating(
    val kp: Double?
)

data class Poster(
    val url: String?
)