package com.example.coroutinesteste.repository

import com.example.coroutinesteste.ResultWrapper
import com.example.coroutinesteste.domain.response.MoviesResponse

interface MainRepository {
    suspend fun getTrendingMovies(): MoviesResponse
    suspend fun getPopularMovies(page:Int): MoviesResponse
    suspend fun searchMovies(query:String): ResultWrapper<MoviesResponse>
}