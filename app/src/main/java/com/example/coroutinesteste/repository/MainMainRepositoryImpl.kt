package com.example.coroutinesteste.repository

import com.example.coroutinesteste.BuildConfig
import com.example.coroutinesteste.BuildConfigHelper
import com.example.coroutinesteste.domain.response.TrendingMoviesResponse
import com.example.coroutinesteste.service.ApiService

class MainMainRepositoryImpl(private val apiService: ApiService) : MainRepository {
    override suspend fun getMovies(): TrendingMoviesResponse {
        return apiService.getTrendingMovies(BuildConfig.API_KEY)

//    override suspend fun getMovies(): List<Movie> {
//        return withContext(Dispatchers.Default) {
//            delay(4000)
//            listOf(
//                Movie(1, "Filme1"),
//                Movie(2, "Filme2")
//            )
//        }
//    }
    }
}