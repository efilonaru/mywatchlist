package com.example.mywatchlist.core.data.movie.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mywatchlist.core.data.movie.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}