package com.example.mywatchlist.core.data.movie.repository

import com.example.mywatchlist.core.data.movie.local.MovieDao
import com.example.mywatchlist.core.data.movie.model.Movie
import com.example.mywatchlist.core.data.movie.model.MovieDetail
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

    override suspend fun getDetailedMovie(movieId: Int, apiKey: String): MovieDetail {
        val response = apiService.getMovieDetails(movieId, apiKey)
        return if (response.isSuccessful) {
            val movieDetailResponse = response.body()
            MovieDetail(
                id = movieDetailResponse?.id ?: 0,
                title = movieDetailResponse?.title ?: "",
                overview = movieDetailResponse?.overview ?: "",
                rating = movieDetailResponse?.voteAverage ?: 0.0,
                posterUrl = "https://image.tmdb.org/t/p/w500${movieDetailResponse?.posterPath}",
                releaseDate = movieDetailResponse?.releaseDate ?: "",
                genres = movieDetailResponse?.genres?.mapNotNull { it.name } ?: emptyList(),
                popularity = movieDetailResponse?.popularity ?: 0.0
            )
        } else {
            throw Exception("Failed to fetch movie details")
        }
    }

}
