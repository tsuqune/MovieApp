package com.bignerdranch.android.movieapp.data

import androidx.room.*
import com.bignerdranch.android.movieapp.model.WatchedAnime
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchedAnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(anime: WatchedAnime)

    @Delete
    suspend fun delete(anime: WatchedAnime)

    @Query("SELECT * FROM watched_anime")
    fun getAll(): Flow<List<WatchedAnime>>

    @Query("SELECT * FROM watched_anime WHERE id = :id")
    suspend fun getById(id: Int): WatchedAnime?
}