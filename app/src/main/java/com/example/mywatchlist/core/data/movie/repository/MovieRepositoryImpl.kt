package com.example.mywatchlist.core.data.movie.repository

import com.example.mywatchlist.core.data.movie.local.MovieDao
import com.example.mywatchlist.core.data.movie.model.Movie
import com.example.mywatchlist.core.data.movie.model.MovieEntity
import com.example.mywatchlist.core.data.movie.remote.MovieService

class MovieRepositoryImpl(
    private val apiService: MovieService,
    private val movieDao: MovieDao
) : MovieRepository {
    override suspend fun getPopularMovies(apiKey: String): List<Movie> {
        val response = apiService.getPopularMovies(apiKey)
        return if (response.isSuccessful) {
            response.body()?.results?.map { Movie(it?.id!!, it.title!!, it.overview!!, it.voteAverage!!, it.posterPath!!) } ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun saveFavoriteMovie(movie: Movie) {
        movieDao.insert(MovieEntity(movie.id, movie.title, movie.overview, movie.posterUrl))
    }
}
