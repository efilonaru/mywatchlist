package com.example.mywatchlist.core.domain.usecase

import com.example.mywatchlist.core.data.movie.model.Movie
import com.example.mywatchlist.core.data.movie.repository.MovieRepository

class GetPopularMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(apiKey: String) = repository.getPopularMovies(apiKey)
}

class SaveFavoriteMovieUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movie: Movie) = repository.saveFavoriteMovie(movie)
}