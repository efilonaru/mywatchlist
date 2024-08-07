package com.example.core.domain.usecase

import com.example.core.data.movie.model.Movie
import com.example.core.data.movie.model.MovieDetail
import com.example.core.data.movie.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase(private val repository: com.example.core.data.movie.repository.MovieRepository) {
    operator fun invoke(apiKey: String): Flow<List<com.example.core.data.movie.model.Movie>> = repository.getPopularMovies(apiKey)
}

class SaveFavoriteMovieUseCase @Inject constructor(private val repository: com.example.core.data.movie.repository.MovieRepository) {
    suspend operator fun invoke(movieDetail: com.example.core.data.movie.model.MovieDetail) = repository.saveFavoriteMovie(movieDetail)
}

class GetDetailedMovieUseCase(private val repository: com.example.core.data.movie.repository.MovieRepository) {
    suspend operator fun invoke(movieId: Int, apiKey: String) = repository.getDetailedMovie(movieId, apiKey)
}

class GetFavoriteMoviesUseCase @Inject constructor(private val repository: com.example.core.data.movie.repository.MovieRepository) {
    operator fun invoke(): Flow<List<com.example.core.data.movie.model.Movie>> = repository.getFavoriteMovies()
}

class RemoveFavoriteMovieUseCase @Inject constructor(private val repository: com.example.core.data.movie.repository.MovieRepository) {
    suspend operator fun invoke(movieId: Int) = repository.removeFavoriteMovie(movieId)
}

class CheckFavoriteStatusUseCase @Inject constructor(private val repository: com.example.core.data.movie.repository.MovieRepository) {
    suspend operator fun invoke(movieId: Int): Boolean = repository.isFavoriteMovie(movieId)
}