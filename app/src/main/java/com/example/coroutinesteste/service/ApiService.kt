package com.example.coroutinesteste.service

import com.example.coroutinesteste.domain.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(@Query("api_key") query : String): MoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") query : String, @Query("language") language : String): MoviesResponse

}