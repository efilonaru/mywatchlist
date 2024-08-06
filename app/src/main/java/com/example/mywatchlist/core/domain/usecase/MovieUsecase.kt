package com.example.mywatchlist.core.domain.usecase

import com.example.mywatchlist.core.data.movie.model.Movie
import com.example.mywatchlist.core.data.movie.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val repository: MovieRepository) {
    operator fun invoke(apiKey: String): Flow<List<Movie>> = repository.getPopularMovies(apiKey)
}

class SaveFavoriteMovieUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movie: Movie) = repository.saveFavoriteMovie(movie)
}

class GetDetailedMovieUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: Int, apiKey: String) = repository.getDetailedMovie(movieId, apiKey)
}