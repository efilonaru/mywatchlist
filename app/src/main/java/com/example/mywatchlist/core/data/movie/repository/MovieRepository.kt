package com.example.mywatchlist.core.data.movie.repository

import com.example.mywatchlist.core.data.movie.remote.MovieService
import com.example.mywatchlist.core.data.movie.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(apiKey: String): List<Movie>
    suspend fun saveFavoriteMovie(movie: Movie)
}