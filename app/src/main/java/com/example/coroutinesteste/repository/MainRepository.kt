package com.example.coroutinesteste.repository

import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.GenreResponse
import com.example.coroutinesteste.domain.response.MoviesResponse

interface MainRepository {
    suspend fun getTrendingMovies(page:Int): ResultWrapper<MoviesResponse>
    suspend fun getPopularMovies(page:Int): ResultWrapper<MoviesResponse>
    suspend fun searchMovies(query:String): ResultWrapper<MoviesResponse>
    suspend fun getGenres(): ResultWrapper<GenreResponse>
    suspend fun getMoviesGenres(id:Int): ResultWrapper<MoviesResponse>
}