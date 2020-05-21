package com.example.coroutinesteste.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.domain.response.Result
import com.example.coroutinesteste.repository.MainMainRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repositoryImpl: MainMainRepositoryImpl) : ViewModel() {


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private var currentRequestResponse: MoviesResponse? = null
    private val movies = ArrayList<Result>()
    private val requestResponse = MutableLiveData<ResultWrapper<MoviesResponse>>()
    val responseLiveData: LiveData<ResultWrapper<MoviesResponse>>
        get() = requestResponse

    private val moviesTrending = ArrayList<Result>()
    private val requestResponseTrending = MutableLiveData<ResultWrapper<MoviesResponse>>()
    val responseTrending: LiveData<ResultWrapper<MoviesResponse>>
        get() = requestResponseTrending
    private var currentRequestResponseTrending: MoviesResponse? = null


    fun getPopularMovies(page: Int) {
        requestResponse.value = ResultWrapper.loading()
        CoroutineScope(Dispatchers.Main).launch {
            val popularMovies = withContext(Dispatchers.Default) {
                repositoryImpl.getPopularMovies(page)
            }
            when (popularMovies) {
                is ResultWrapper.NetworkError -> showNetworkError()
                is ResultWrapper.Success -> showSuccess(popularMovies.value)
                is ResultWrapper.GenericError -> showGenericError(popularMovies)
                is ResultWrapper.Error -> requestResponse.value =
                    ResultWrapper.error(popularMovies.errorMessage)
            }
        }
    }

    fun moviesSize(): Int {
        return movies.size
    }

    fun movieItem(position: Int): Result {
        return movies[position]
    }

    fun moviesTrendingSize(): Int {
        return moviesTrending.size
    }

    fun movieTrendingItem(position: Int): Result {
        return moviesTrending[position]
    }


    fun fetchMoreMovies(lastVisibleItem: Int) {
        if (!shouldRefresh(lastVisibleItem)) return
        val page = currentRequestResponse!!.page
        getPopularMovies(page + 1)
    }

    fun fetchMoreMoviesTrending(lastVisibleItem: Int) {
        if (!shouldRefresh(lastVisibleItem)) return
        val page = currentRequestResponseTrending!!.page
        getTrendingMovies(page + 1)
    }

    private fun shouldRefresh(lastVisibleItem: Int): Boolean {
        val itemCount = movies.size
        return when (val requestResponse = this.requestResponse.value) {
            is ResultWrapper.Success -> {
                val page = requestResponse.value.page
                val totalPage = requestResponse.value.total_pages
                itemCount < (lastVisibleItem + 5) && page < totalPage
            }
            is ResultWrapper.GenericError -> {
                currentRequestResponse != null && itemCount < (lastVisibleItem + 5)
            }
            else -> {
                false
            }
        }
    }

    fun getTrendingMovies(page: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val moviesTrending = withContext(Dispatchers.Default) {
                repositoryImpl.getTrendingMovies(page)
            }
            when (moviesTrending) {
                is ResultWrapper.NetworkError -> showNetworkError()
                is ResultWrapper.Success -> showSuccessMoviesTrending(moviesTrending.value)
                is ResultWrapper.GenericError -> showGenericError(moviesTrending)
                is ResultWrapper.Error -> requestResponse.value =
                    ResultWrapper.error(moviesTrending.errorMessage)
            }
        }
    }

    private fun showGenericError(error: ResultWrapper.GenericError) {
        when (error.code) {
            422 -> requestResponse.value = ResultWrapper.error(error.code)
            401 -> requestResponse.value = ResultWrapper.error(error.code)
            404 -> requestResponse.value = ResultWrapper.error(error.code)
            else -> _errorMessage.value = "${error.code} - Erro Genérico"
        }
    }

    private fun showNetworkError() {
        _errorMessage.value = "error Network"
    }

    private fun showSuccess(result: MoviesResponse) {
        movies.addAll(result.results)
        requestResponse.value = ResultWrapper.Success(result)
        currentRequestResponse = result

    }

    private fun showSuccessMoviesTrending(result: MoviesResponse) {
        moviesTrending.addAll(result.results)
        requestResponseTrending.value = ResultWrapper.Success(result)
        currentRequestResponseTrending = result

    }

}
