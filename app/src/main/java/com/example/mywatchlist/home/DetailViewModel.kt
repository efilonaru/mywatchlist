package com.example.mywatchlist.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.core.data.movie.model.Movie
import com.example.core.data.movie.model.MovieDetail
import com.example.core.data.movie.repository.MovieRepository
import com.example.core.domain.usecase.CheckFavoriteStatusUseCase
import com.example.core.domain.usecase.GetDetailedMovieUseCase
import com.example.core.domain.usecase.RemoveFavoriteMovieUseCase
import com.example.core.domain.usecase.SaveFavoriteMovieUseCase
import com.example.core.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailedMovieUseCase: com.example.core.domain.usecase.GetDetailedMovieUseCase,
    private val saveFavoriteMovieUseCase: com.example.core.domain.usecase.SaveFavoriteMovieUseCase,
    private val removeFavoriteMovieUseCase: com.example.core.domain.usecase.RemoveFavoriteMovieUseCase,
    private val checkFavoriteStatusUseCase: com.example.core.domain.usecase.CheckFavoriteStatusUseCase
) : ViewModel() {

    private val _movieDetail = MutableLiveData<com.example.core.utils.Result<com.example.core.data.movie.model.MovieDetail>>()
    val movieDetail: MutableLiveData<com.example.core.utils.Result<com.example.core.data.movie.model.MovieDetail>> = _movieDetail

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private var currentMovie: com.example.core.data.movie.model.MovieDetail? = null

    fun getMovieDetail(movieId: Int, apiKey: String) {
        viewModelScope.launch {
            _movieDetail.value = com.example.core.utils.Result.Loading()
            try {
                val movieDetail = getDetailedMovieUseCase(movieId, apiKey)
                _movieDetail.value = com.example.core.utils.Result.Success(movieDetail)
                currentMovie = movieDetail
                checkFavoriteStatus(movieId)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error fetching movie details", e)
                _movieDetail.value = com.example.core.utils.Result.Error(e.localizedMessage)
            }
        }
    }

    private fun checkFavoriteStatus(movieId: Int) {
        viewModelScope.launch {
            _isFavorite.value = checkFavoriteStatusUseCase(movieId)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            currentMovie?.let { movie ->
                if (_isFavorite.value) {
                    removeFavoriteMovieUseCase(movie.id)
                } else {
                    saveFavoriteMovieUseCase(movie)
                }
                _isFavorite.value = !_isFavorite.value
            }
        }
    }
}
