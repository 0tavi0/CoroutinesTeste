package com.example.coroutinesteste.ui.home.viewmodel

import android.util.Log
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

class MainViewModel(private val repositoryImpl: MainMainRepositoryImpl) : ViewModel() {

    private val _listResultTrendingMovies = MutableLiveData<List<Result>>()
    val listMoviesTrendingResult: LiveData<List<Result>> get() = _listResultTrendingMovies

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private var currentRequestResponse: MoviesResponse? = null
    private val movies = ArrayList<Result>()
    private val requestResponse = MutableLiveData<ResultWrapper<MoviesResponse>>()

    val responseLiveData: LiveData<ResultWrapper<MoviesResponse>>
        get() = requestResponse

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


    fun fetchMoreMovies(lastVisibleItem: Int) {
        if (!shouldRefresh(lastVisibleItem)) return
        val page = currentRequestResponse!!.page

        getPopularMovies(page + 1)
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
        for (i in movies) {
            Log.e("Original Title", "" + i.original_title)
        }
        Log.e("Original Title", "" + movies.size.toString())

        requestResponse.value = ResultWrapper.Success(result)
        currentRequestResponse = result

    }

}
