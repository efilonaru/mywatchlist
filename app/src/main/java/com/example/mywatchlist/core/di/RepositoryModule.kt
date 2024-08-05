package com.example.mywatchlist.core.di

import com.example.mywatchlist.core.data.movie.repository.MovieRepository
import com.example.mywatchlist.core.data.movie.remote.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object RepositoryModule {
//
//    @Provides
//    @Singleton
//    fun provideMovieRepository(apiService: MovieService): MovieRepository {
//        return MovieRepository(apiService)
//    }
//}