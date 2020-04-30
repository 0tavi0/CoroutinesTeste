package com.example.coroutinesteste.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesteste.ResultWrapper
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.repository.MainMainRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val repositoryImpl: MainMainRepositoryImpl) : ViewModel() {
    private val _resultSearch = MutableLiveData<ResultWrapper<MoviesResponse>>()
    val resultSearch: LiveData<ResultWrapper<MoviesResponse>> = _resultSearch
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun searchMovie(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val moviesResult = withContext(Dispatchers.Default) {
                repositoryImpl.searchMovies(query)
            }
            when (moviesResult) {
                is ResultWrapper.NetworkError -> showNetworkError()
                is ResultWrapper.GenericError -> showGenericError(moviesResult)
                is ResultWrapper.Success -> _resultSearch.value = moviesResult
            }
        }
    }


    private fun showGenericError(error: ResultWrapper.GenericError) {
        when (error.code) {
            422 -> _errorMessage.value = "${error.code} campo em branco"
        }
    }

    private fun showNetworkError() {
        _errorMessage.value = "error Netowork"
    }

}


