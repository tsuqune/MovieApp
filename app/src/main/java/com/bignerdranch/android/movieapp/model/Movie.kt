package com.bignerdranch.android.movieapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MovieResponse(
    val docs: List<Movie>
)

@Parcelize
data class Movie(
    val id: Int,
    val name: String?,
    val poster: Poster?,
    val rating: Rating?,
    val description: String?,
    val year: Int?,
    val genres: List<Genre>?
) : Parcelable

@Parcelize
data class Genre(val name: String?) : Parcelable

@Parcelize
data class Poster(val url: String?) : Parcelable

@Parcelize
data class Rating(val kp: Double?) : Parcelable