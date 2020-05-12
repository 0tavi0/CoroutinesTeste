package com.example.coroutinesteste.base

import androidx.annotation.StringRes
import com.example.coroutinesteste.domain.response.ErrorResponse

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) :
        ResultWrapper<Nothing>()
    class Loading<T> : ResultWrapper<T>()
    object NetworkError : ResultWrapper<Nothing>()
    data class Error<T>(@StringRes val errorMessage: Int) : ResultWrapper<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> error(@StringRes message: Int) = Error<T>(message)
    }
}