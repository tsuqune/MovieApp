package com.bignerdranch.android.movieapp.api

import com.bignerdranch.android.movieapp.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "ru-RU"
    ): MovieResponse
}