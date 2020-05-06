package com.example.coroutinesteste.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    fun getPopularMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            val popularMovies = withContext(Dispatchers.Default) {
                repositoryImpl.getPopularMovies()
            }
            _listMoviesPopular.value = popularMovies.results
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

}
