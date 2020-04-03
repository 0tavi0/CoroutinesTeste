package com.example.coroutinesteste

import android.app.Application
import com.example.coroutinesteste.di.AppModules
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this@App, listOf(AppModules.modules))


    }
}