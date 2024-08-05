package com.example.mywatchlist.core.di

import android.content.Context
import androidx.room.Room
import com.example.mywatchlist.core.data.movie.local.MovieDao
import com.example.mywatchlist.core.data.movie.local.MovieDatabase
import com.example.mywatchlist.core.data.movie.remote.MovieService
import com.example.mywatchlist.core.data.movie.repository.MovieRepository
import com.example.mywatchlist.core.data.movie.repository.MovieRepositoryImpl
import com.example.mywatchlist.core.domain.usecase.GetDetailedMovieUseCase
import com.example.mywatchlist.core.domain.usecase.GetPopularMoviesUseCase
import com.example.mywatchlist.core.domain.usecase.SaveFavoriteMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiService(): MovieService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    fun provideMovieDao(db: MovieDatabase): MovieDao {
        return db.movieDao()
    }

    @Provides
    fun provideMovieRepository(apiService: MovieService, movieDao: MovieDao): MovieRepository {
        return MovieRepositoryImpl(apiService, movieDao)
    }

    @Provides
    fun provideGetPopularMoviesUseCase(repository: MovieRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(repository)
    }

    @Provides
    fun provideSaveFavoriteMovieUseCase(repository: MovieRepository): SaveFavoriteMovieUseCase {
        return SaveFavoriteMovieUseCase(repository)
    }

    @Provides
    fun provideDetailMovieUseCase(repository: MovieRepository): GetDetailedMovieUseCase {
        return GetDetailedMovieUseCase(repository)
    }

}
