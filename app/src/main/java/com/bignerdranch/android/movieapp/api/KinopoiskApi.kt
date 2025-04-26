package com.bignerdranch.android.movieapp.api

import com.bignerdranch.android.movieapp.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("v1.4/movie")
    suspend fun getTopRatedMovies(
        @Header("X-API-KEY") apiKey: String,
        @Query("rating.kp") ratingRange: String = "8-10", // Фильтр по рейтингу
        @Query("genres.name") genre: String = "аниме",
        @Query("sortField") sortField: String = "rating.kp", // Поле для сортировки
        @Query("sortType") sortType: String = "-1", // -1 = по убыванию
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100 // Лимит 100 аниме
    ): MovieResponse

    @GET("v1.4/movie")
    suspend fun getRecentAnime(
        @Header("X-API-KEY") apiKey: String,
        @Query("year") year: String, // Фильтр по году
        @Query("genres.name") genre: String = "аниме",
        @Query("sortField") sortField: String = "rating.kp",
        @Query("sortType") sortType: String = "-1",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100
    ): MovieResponse

    @GET("v1.4/movie/search")
    suspend fun searchAnime(
        @Header("X-API-KEY") apiKey: String,
        @Query("query") searchQuery: String,
        @Query("type") type: String = "anime", // Основной фильтр
        @Query("genres.name") genre: String = "аниме", // Доп. фильтр
        @Query("sortField") sortField: String = "rating.kp",
        @Query("sortType") sortType: String = "-1",
        @Query("limit") limit: Int = 100
    ): MovieResponse
}