package com.example.mywatchlist.core.di

import com.example.mywatchlist.MainActivity

import com.example.mywatchlist.home.Home
import dagger.Component
import javax.inject.Singleton

//@Singleton
//@Component(
//    dependencies = [CoreComponent::class],
//    modules = [AppModule::class, ViewModelModule::class])
//interface AppComponent {
//    interface Factory {
//        fun create(coreComponent: CoreComponent): AppComponent
//    }
//    fun inject(fragment: Home)
//    fun inject(application: MyApplication)
//    fun inject(activity: MainActivity)
//}