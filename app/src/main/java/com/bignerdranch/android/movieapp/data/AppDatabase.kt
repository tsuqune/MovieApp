package com.bignerdranch.android.movieapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bignerdranch.android.movieapp.model.WatchedAnime

@Database(entities = [WatchedAnime::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun watchedAnimeDao(): WatchedAnimeDao
}