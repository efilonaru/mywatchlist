package com.example.mywatchlist.core.data.movie.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val rating: Double,
    val posterUrl: String,
    val releaseDate: String,
    val genres: List<String>,
    val popularity: Double
)