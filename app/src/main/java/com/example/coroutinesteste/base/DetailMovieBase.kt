package com.example.coroutinesteste.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class DetailMovieBase : AppCompatActivity() {

    protected abstract var layout: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)

    }
}