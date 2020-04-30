package com.example.coroutinesteste.service

import com.example.coroutinesteste.domain.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") query: String,
        @Query("language") language: String,
        @Query("media_type") media_type: String,
        @Query("time_window") time_window: String
    ): MoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") query: String,
        @Query("language") language: String
    ): MoviesResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") querySearch: String
    ) : MoviesResponse
}