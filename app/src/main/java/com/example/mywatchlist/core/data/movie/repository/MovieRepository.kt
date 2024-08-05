package com.example.mywatchlist.core.data.movie.repository

import com.example.mywatchlist.core.data.movie.remote.MovieService
import com.example.mywatchlist.core.data.movie.model.Movie
import com.example.mywatchlist.core.data.movie.model.MovieDetail

interface MovieRepository {
    suspend fun getPopularMovies(apiKey: String): List<Movie>
    suspend fun saveFavoriteMovie(movie: Movie)
    suspend fun getDetailedMovie(movieId: Int, apiKey: String): MovieDetail
}