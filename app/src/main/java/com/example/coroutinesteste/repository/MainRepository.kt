package com.example.coroutinesteste.repository

import com.example.coroutinesteste.domain.response.TrendingMoviesResponse

interface MainRepository {
    suspend fun getMovies(): TrendingMoviesResponse
}