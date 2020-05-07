package com.example.coroutinesteste.ui.main.viewmodel

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

class MainViewModel(private val repositoryImpl: MainMainRepositoryImpl) : ViewModel() {

    private val _listResultTrendingMovies = MutableLiveData<List<Result>>()
    val listMoviesTrendingResult : LiveData<List<Result>> get() = _listResultTrendingMovies
    private val _listMoviesPopular = MutableLiveData<List<Result>>()
    val listMoviesPopular:LiveData<List<Result>> = _listMoviesPopular
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getPopularMovies(page:Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val popularMovies = withContext(Dispatchers.Default) {
                repositoryImpl.getPopularMovies(page)
            }
            when(popularMovies){
                is ResultWrapper.NetworkError -> showNetworkError()
                is ResultWrapper.GenericError -> showGenericError(popularMovies)
                is ResultWrapper.Success -> showSuccess(popularMovies.value)
            }
        }
    }

    fun getTrendingMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            val movies = withContext(Dispatchers.Default) {
                repositoryImpl.getTrendingMovies()
            }
            _listResultTrendingMovies.value = movies.results
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
        _listMoviesPopular.value = result.results
    }

}
