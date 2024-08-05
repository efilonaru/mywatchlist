package com.example.mywatchlist.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.mywatchlist.core.data.movie.model.Movie
import com.example.mywatchlist.core.domain.usecase.GetPopularMoviesUseCase
import com.example.mywatchlist.core.domain.usecase.SaveFavoriteMovieUseCase
import com.example.mywatchlist.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<com.example.mywatchlist.utils.Result<List<Movie>>>()
    val movies: LiveData<com.example.mywatchlist.utils.Result<List<Movie>>> = _movies

    fun getPopularMovies(apiKey: String) {
        viewModelScope.launch {
            _movies.value = Result.Loading()
            try {
                val movieList = getPopularMoviesUseCase(apiKey)
                _movies.value = Result.Success(movieList)
            } catch (e: Exception) {
                _movies.value = Result.Error(e.localizedMessage)
            }
        }
    }

    fun saveFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            saveFavoriteMovieUseCase(movie)
        }
    }
}
