package com.example.mywatchlist.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.core.data.movie.model.Movie
import com.example.core.domain.usecase.GetPopularMoviesUseCase
import com.example.core.domain.usecase.SaveFavoriteMovieUseCase
import com.example.core.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMoviesUseCase: com.example.core.domain.usecase.GetPopularMoviesUseCase
//    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<com.example.core.utils.Result<List<com.example.core.data.movie.model.Movie>>>(com.example.core.utils.Result.Loading())
    val movies: StateFlow<com.example.core.utils.Result<List<com.example.core.data.movie.model.Movie>>> = _movies

    fun getPopularMovies(apiKey: String) {
        viewModelScope.launch {
            getPopularMoviesUseCase(apiKey)
                .catch { e ->
                    _movies.value = com.example.core.utils.Result.Error(e.localizedMessage)
                }
                .collect { movieList ->
                    _movies.value = com.example.core.utils.Result.Success(movieList)
                }
        }
    }
}
