package com.example.core.di

import com.example.core.data.lib.HeaderInterceptor
import com.example.core.data.movie.remote.MovieService
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Interceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//val networkModule = module {
//    single {
//        val interceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//        OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()
//    }
//
//    single {
//        Retrofit.Builder()
//            .baseUrl("https://api.themoviedb.org/3/")
//            .client(get())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    single {
//        get<Retrofit>().create(MovieService::class.java)
//    }
//}