package com.bignerdranch.android.movieapp.api

import com.bignerdranch.android.movieapp.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("v1.4/movie")
    suspend fun getPopularMovies(
        @Header("X-API-KEY") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): MovieResponse
}