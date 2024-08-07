package com.example.core.di


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