package com.example.coroutinesteste.di

import com.example.coroutinesteste.repository.MainMainRepositoryImpl
import com.example.coroutinesteste.service.ApiService
import com.example.coroutinesteste.service.ServiceGenerator.provideOkHttpClient
import com.example.coroutinesteste.service.ServiceGenerator.provideRetrofit
import com.example.coroutinesteste.ui.main.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object AppModules {
    val modules = module {
        single { provideOkHttpClient() }
        single { provideRetrofit<ApiService>(get()) }
        single { MainMainRepositoryImpl(get()) }

        viewModel { MainViewModel(get()) }
    }
}