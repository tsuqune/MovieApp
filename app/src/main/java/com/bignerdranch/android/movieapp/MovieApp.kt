package com.bignerdranch.android.movieapp

import android.app.Application
import com.bignerdranch.android.movieapp.data.AppDatabase
import androidx.room.Room

class MovieApp : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "movie-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}