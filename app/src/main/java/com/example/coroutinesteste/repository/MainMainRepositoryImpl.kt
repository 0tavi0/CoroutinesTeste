package com.example.coroutinesteste.repository

import com.example.coroutinesteste.BuildConfig
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.service.ApiService

class MainMainRepositoryImpl(private val apiService: ApiService) : MainRepository {
    override suspend fun getTrendingMovies(): MoviesResponse {
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

    override suspend fun getPopularMovies(): MoviesResponse {
        return apiService.getPopularMovies(BuildConfig.API_KEY, "pt-BR")
    }
}