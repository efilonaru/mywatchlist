package com.example.mywatchlist.core.data.movie.repository

import com.example.mywatchlist.core.data.movie.remote.MovieService
import com.example.mywatchlist.core.data.movie.model.Movie
import com.example.mywatchlist.core.data.movie.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(apiKey: String): Flow<List<Movie>>
    suspend fun saveFavoriteMovie(movie: Movie)
    suspend fun getDetailedMovie(movieId: Int, apiKey: String): MovieDetail
    fun getFavoriteMovies(): Flow<List<Movie>>
}