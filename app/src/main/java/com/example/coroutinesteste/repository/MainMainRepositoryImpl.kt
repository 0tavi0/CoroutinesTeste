package com.example.coroutinesteste.repository

import com.example.coroutinesteste.BuildConfig
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.service.ApiService

class MainMainRepositoryImpl(private val apiService: ApiService) : MainRepository {
    override suspend fun getTrendingMovies(): MoviesResponse {
        return apiService.getTrendingMovies(BuildConfig.API_KEY,"pt-BR","all","week")
    }

    override suspend fun getPopularMovies(): MoviesResponse {
        return apiService.getPopularMovies(BuildConfig.API_KEY, "pt-BR")
    }
}