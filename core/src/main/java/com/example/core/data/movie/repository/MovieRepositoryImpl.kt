package com.example.core.data.movie.repository

import com.example.core.data.movie.local.MovieDao
import com.example.core.data.movie.model.Movie
import com.example.core.data.movie.model.MovieDetail
import com.example.core.data.movie.model.MovieEntity
import com.example.core.data.movie.remote.MovieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val apiService: MovieService,
    private val movieDao: MovieDao
) : MovieRepository {
    override fun getPopularMovies(apiKey: String): Flow<List<Movie>> = flow{
        val response = apiService.getPopularMovies(apiKey)
        if (response.isSuccessful) {
            val movies = response.body()?.results?.map { Movie(it?.id!!, it.title!!, it.overview!!, it.voteAverage!!, it.posterPath!!) } ?: emptyList()
            emit(movies)
        } else {
            emit(emptyList())
        }
    }

    override suspend fun saveFavoriteMovie(movieDetail: MovieDetail) {
        movieDao.insert(movieDetail.toMovieEntity())
    }

//    override suspend fun saveFavoriteMovie(movie: Movie) {
//        movieDao.insert(MovieEntity(movie.id, movie.title, movie.overview, movie.posterUrl))
//    }

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

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().map { entities ->
            entities.map { entity ->
                Movie(entity.id, entity.title, entity.overview, entity.rating, entity.posterUrl)
            }
        }
    }

    override suspend fun removeFavoriteMovie(movieId: Int) {
        movieDao.deleteMovie(movieId)
    }

    override suspend fun isFavoriteMovie(movieId: Int): Boolean {
        return movieDao.getMovieById(movieId) != null
    }

    fun MovieDetail.toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = this.id,
            title = this.title,
            overview = this.overview,
            rating = this.rating,
            posterUrl = this.posterUrl,
        )
    }


}
