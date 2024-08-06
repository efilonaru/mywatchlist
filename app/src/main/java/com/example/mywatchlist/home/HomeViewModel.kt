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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<com.example.mywatchlist.utils.Result<List<Movie>>>(Result.Loading())
    val movies: StateFlow<com.example.mywatchlist.utils.Result<List<Movie>>> = _movies

    fun getPopularMovies(apiKey: String) {
        viewModelScope.launch {
            getPopularMoviesUseCase(apiKey)
                .catch { e ->
                    _movies.value = Result.Error(e.localizedMessage)
                }
                .collect { movieList ->
                    _movies.value = Result.Success(movieList)
                }
        }
    }

    fun saveFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            saveFavoriteMovieUseCase(movie)
        }
    }
}
