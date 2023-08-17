package com.fakhari.batmanmovie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fakhari.batmanmovie.data.model.SearchMovieModel

// movie data base
@Database(entities = [SearchMovieModel::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieSearchDao(): MovieSearchDao
}