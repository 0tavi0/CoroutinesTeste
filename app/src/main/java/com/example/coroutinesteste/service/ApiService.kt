package com.example.coroutinesteste.service

import com.example.coroutinesteste.domain.response.TrendingMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(@Query("api_key") query : String): TrendingMoviesResponse

}