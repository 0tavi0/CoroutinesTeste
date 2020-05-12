package com.example.coroutinesteste.repository

import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.MoviesResponse

interface MainRepository {
    suspend fun getTrendingMovies(): MoviesResponse
    suspend fun getPopularMovies(page:Int): ResultWrapper<MoviesResponse>
    suspend fun searchMovies(query:String): ResultWrapper<MoviesResponse>
}