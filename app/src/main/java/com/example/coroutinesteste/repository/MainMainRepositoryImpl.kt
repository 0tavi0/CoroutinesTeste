package com.example.coroutinesteste.repository

import android.util.Log
import com.example.coroutinesteste.BuildConfig
import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.ErrorResponse
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.service.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainMainRepositoryImpl(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MainRepository {
    override suspend fun getTrendingMovies(page: Int): ResultWrapper<MoviesResponse> {
        return safeApiCall(dispatcher) {
            apiService.getTrendingMovies(
                BuildConfig.API_KEY,
                "pt-BR"
            )
        }
    }

    override suspend fun getPopularMovies(page: Int): ResultWrapper<MoviesResponse> {
        return safeApiCall(dispatcher) {
            apiService.getPopularMovies(
                BuildConfig.API_KEY,
                page,
                "pt-BR"
            )
        }
    }

    override suspend fun searchMovies(query: String): ResultWrapper<MoviesResponse> {
        return safeApiCall(dispatcher) {
            apiService.searchMovie(
                BuildConfig.API_KEY,
                "pt-BR",
                query
            )
        }
    }


    private suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {

                throwable.message?.let { Log.e("Repository", it) }

                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        ResultWrapper.GenericError(code, errorResponse)
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null)
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.toString()?.let {
                Gson().fromJson(it, ErrorResponse::class.java)
            }
        } catch (exception: Exception) {
            null
        }
    }
}