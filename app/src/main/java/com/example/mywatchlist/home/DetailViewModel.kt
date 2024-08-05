package com.example.mywatchlist.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.core.data.movie.model.Movie
import com.example.mywatchlist.core.data.movie.model.MovieDetail
import com.example.mywatchlist.core.data.movie.repository.MovieRepository
import com.example.mywatchlist.core.domain.usecase.GetDetailedMovieUseCase
import com.example.mywatchlist.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailedMovieUseCase: GetDetailedMovieUseCase
) : ViewModel() {

    private val _movieDetail = MutableLiveData<com.example.mywatchlist.utils.Result<MovieDetail>>()
    val movieDetail: MutableLiveData<Result<MovieDetail>> = _movieDetail

    fun getMovieDetail(movieId: Int, apiKey: String) {
        viewModelScope.launch {
            _movieDetail.value = Result.Loading()
            try {
                val movieDetail = getDetailedMovieUseCase(movieId, apiKey)
                _movieDetail.value = Result.Success(movieDetail)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error fetching movie details", e)
                _movieDetail.value = Result.Error(e.localizedMessage)
            }
        }
    }
}
