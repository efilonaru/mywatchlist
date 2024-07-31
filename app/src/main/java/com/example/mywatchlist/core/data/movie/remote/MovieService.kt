package com.example.mywatchlist.core.data.movie.remote

import retrofit2.http.GET

interface MovieService {
    @GET("movie/popular")
    suspend fun getMovies(): BasicMovieResponse
}