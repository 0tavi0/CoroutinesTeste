package com.example.coroutinesteste.service

import com.example.coroutinesteste.domain.response.GenreResponse
import com.example.coroutinesteste.domain.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") query: String,
        @Query("language") language: String
    ): MoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") query: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): MoviesResponse
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") querySearch: String
    ) : MoviesResponse

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ) : GenreResponse
}