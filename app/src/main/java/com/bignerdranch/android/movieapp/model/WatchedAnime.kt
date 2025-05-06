package com.bignerdranch.android.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "watched_anime")
data class WatchedAnime(
    @PrimaryKey val id: Int,
    val name: String?,
    val posterUrl: String?,
    val rating: Double?,
    val year: Int?,
    val genresJson: String?, // Список жанров в JSON
    val description: String?
) {
    // Преобразование JSON в список Genre
    fun getGenres(): List<Genre> {
        return if (genresJson.isNullOrEmpty()) emptyList()
        else Gson().fromJson(genresJson, object : TypeToken<List<Genre>>() {}.type)

    }
    fun toMovie(): Movie = Movie(
        id = this.id,
        name = this.name,
        poster = Poster(this.posterUrl),
        rating = Rating(this.rating),
        year = this.year,
        genres = Gson().fromJson(this.genresJson, Array<Genre>::class.java).toList(),
        description = this.description,
        type = "anime"
    )
}