package com.example.mywatchlist.core.data.movie

import com.example.mywatchlist.core.domain.movie.model.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
}