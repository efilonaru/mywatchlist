package com.example.core.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoMap

//@Suppress("unused")
//@Module
//@InstallIn(ViewModelComponent::class)
//abstract class ViewModelModule {
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(HomeViewModel::class)
//    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
//}