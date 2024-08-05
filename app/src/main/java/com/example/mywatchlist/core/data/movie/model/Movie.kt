package com.example.mywatchlist.core.data.movie.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val rating: Double,
    val posterUrl: String
)