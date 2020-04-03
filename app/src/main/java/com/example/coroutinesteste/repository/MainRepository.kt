package com.example.coroutinesteste.repository

import com.example.coroutinesteste.domain.Movie

interface MainRepository {
    suspend fun getMovies(): List<Movie>
}