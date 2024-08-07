package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.movie.local.MovieDao
import com.example.core.data.movie.local.MovieDatabase
import com.example.core.data.movie.remote.MovieService
import com.example.core.data.movie.repository.MovieRepository
import com.example.core.data.movie.repository.MovieRepositoryImpl
import com.example.core.domain.usecase.GetDetailedMovieUseCase
import com.example.core.domain.usecase.GetPopularMoviesUseCase
import com.example.core.domain.usecase.SaveFavoriteMovieUseCase
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
    fun provideApiService(): com.example.core.data.movie.remote.MovieService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.example.core.data.movie.remote.MovieService::class.java)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): com.example.core.data.movie.local.MovieDatabase {
        return Room.databaseBuilder(
            context,
            com.example.core.data.movie.local.MovieDatabase::class.java,
            "movie_database"
        ).addMigrations(com.example.core.data.movie.local.MovieDatabase.MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideMovieDao(db: com.example.core.data.movie.local.MovieDatabase): com.example.core.data.movie.local.MovieDao {
        return db.movieDao()
    }

    @Provides
    fun provideMovieRepository(apiService: com.example.core.data.movie.remote.MovieService, movieDao: com.example.core.data.movie.local.MovieDao): com.example.core.data.movie.repository.MovieRepository {
        return com.example.core.data.movie.repository.MovieRepositoryImpl(apiService, movieDao)
    }

    @Provides
    fun provideGetPopularMoviesUseCase(repository: com.example.core.data.movie.repository.MovieRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(repository)
    }

    @Provides
    fun provideSaveFavoriteMovieUseCase(repository: com.example.core.data.movie.repository.MovieRepository): SaveFavoriteMovieUseCase {
        return SaveFavoriteMovieUseCase(repository)
    }

    @Provides
    fun provideDetailMovieUseCase(repository: com.example.core.data.movie.repository.MovieRepository): GetDetailedMovieUseCase {
        return GetDetailedMovieUseCase(repository)
    }

}
