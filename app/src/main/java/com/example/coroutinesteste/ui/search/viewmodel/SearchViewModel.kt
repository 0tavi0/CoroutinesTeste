package com.example.coroutinesteste.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesteste.ResultWrapper
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.domain.response.Result
import com.example.coroutinesteste.repository.MainMainRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val repositoryImpl: MainMainRepositoryImpl) : ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private val _listResultSearch = MutableLiveData<List<Result>>()
    val listMoviesTrendingResult : LiveData<List<Result>> = _listResultSearch

    fun searchMovie(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val moviesResult = withContext(Dispatchers.Default) {
                repositoryImpl.searchMovies(query)
            }
            when (moviesResult) {
                is ResultWrapper.NetworkError -> showNetworkError()
                is ResultWrapper.GenericError -> showGenericError(moviesResult)
                is ResultWrapper.Success -> showSuccess(moviesResult.value)
            }
        }
    }


    private fun showGenericError(error: ResultWrapper.GenericError) {
        when (error.code) {
            422 -> _errorMessage.value = "${error.code} - Entidade não processável"
            401 -> _errorMessage.value = "${error.code} - Chave de API inválida"
            404 -> _errorMessage.value = "${error.code} - O recurso que você solicitou não pôde ser encontrado."
            else -> _errorMessage.value = "${error.code} - Erro Genérico"
        }
    }

    private fun showNetworkError() {
        _errorMessage.value = "error Network"
    }

    private fun showSuccess(result: MoviesResponse){
     _listResultSearch.value = result.results
    }

}


