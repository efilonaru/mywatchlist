package com.example.mywatchlist.core.data.movie.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mywatchlist.core.data.movie.model.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Query("SELECT * FROM movies")
    fun getFavoriteMovies(): LiveData<List<MovieEntity>>
}