package com.bignerdranch.android.movieapp.api

import com.bignerdranch.android.movieapp.model.Movie
import com.bignerdranch.android.movieapp.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("v1.4/movie")
    suspend fun getTopRatedMovies(
        @Header("X-API-KEY") apiKey: String,
        @Query("rating.kp") ratingRange: String = "8-10",
        @Query("genres.name") genre: String = "аниме",
        @Query("sortField") sortField: String = "rating.imdb", // Поле для сортировки
        @Query("sortType") sortType: String = "-1", // -1 = по убыванию
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100 // Лимит 100 аниме
    ): MovieResponse

    @GET("v1.4/movie")
    suspend fun getRecentAnime(
        @Header("X-API-KEY") apiKey: String,
        @Query("year") year: String,
        @Query("genres.name") genre: String = "аниме",
        @Query("sortField") sortField: String = "rating.imdb",
        @Query("sortType") sortType: String = "-1",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100
    ): MovieResponse

    @GET("v1.4/movie/search")
    suspend fun searchAnime(
        @Header("X-API-KEY") apiKey: String,
        @Query("query") searchQuery: String,
        @Query("genres.name") genre: String = "аниме", // Доп. фильтр
        @Query("sortField") sortField: String = "rating.imdb",
        @Query("sortType") sortType: String = "-1",
        @Query("limit") limit: Int = 100
    ): MovieResponse

    @GET("v1.4/movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int,
        @Header("X-API-KEY") apiKey: String
    ): Movie
}