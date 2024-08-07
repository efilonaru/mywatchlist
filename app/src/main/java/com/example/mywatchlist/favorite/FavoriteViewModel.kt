package com.example.mywatchlist.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: com.example.core.domain.usecase.GetFavoriteMoviesUseCase
) : ViewModel() {

    private val _favoriteMovies = MutableStateFlow<com.example.core.utils.Result<List<com.example.core.data.movie.model.Movie>>>(com.example.core.utils.Result.Loading())
    val favoriteMovies: StateFlow<com.example.core.utils.Result<List<com.example.core.data.movie.model.Movie>>> = _favoriteMovies

    init {
        getFavoriteMovies()
    }

    fun getFavoriteMovies() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase()
                .catch { e ->
                    _favoriteMovies.value = com.example.core.utils.Result.Error(e.localizedMessage)
                }
                .collect { movieList ->
                    _favoriteMovies.value = com.example.core.utils.Result.Success(movieList)
                }
        }
    }
}