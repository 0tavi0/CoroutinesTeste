package com.example.coroutinesteste.repository

import com.example.coroutinesteste.ResultWrapper
import com.example.coroutinesteste.domain.response.MoviesResponse

interface MainRepository {
    suspend fun getTrendingMovies(): MoviesResponse
    suspend fun getPopularMovies(): MoviesResponse
    suspend fun searchMovies(query:String): ResultWrapper<MoviesResponse>
}