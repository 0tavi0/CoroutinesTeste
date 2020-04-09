package com.example.coroutinesteste.repository

import com.example.coroutinesteste.domain.response.MoviesResponse

interface MainRepository {
    suspend fun getTrendingMovies(): MoviesResponse
    suspend fun getPopularMovies(): MoviesResponse
}