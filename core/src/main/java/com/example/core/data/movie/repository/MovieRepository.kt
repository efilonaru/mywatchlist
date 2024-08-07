package com.example.core.data.movie.repository

import com.example.core.data.movie.remote.MovieService
import com.example.core.data.movie.model.Movie
import com.example.core.data.movie.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(apiKey: String): Flow<List<Movie>>
    suspend fun saveFavoriteMovie(movie: MovieDetail)
    suspend fun getDetailedMovie(movieId: Int, apiKey: String): MovieDetail
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun removeFavoriteMovie(movieId: Int)
    suspend fun isFavoriteMovie(movieId: Int): Boolean
}