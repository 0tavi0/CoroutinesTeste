package com.example.coroutinesteste.di

import com.example.coroutinesteste.MainRepository
import com.example.coroutinesteste.ui.main.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object AppModules {
    val modules = module {
        single { MainRepository() }

        viewModel { MainViewModel(get()) }
    }
}