package com.example.coroutinesteste.ui.main.viewModel

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

    private val _moviesLiveData = MutableLiveData<MoviesResponse>()
    val moviesLiveData: LiveData<MoviesResponse> get() = _moviesLiveData
    private val _listResultTrendingMovies = MutableLiveData<List<Result>>()
    val listMoviesTrendingResult : LiveData<List<Result>> get() = _listResultTrendingMovies
    val moviesPopular = MutableLiveData<MoviesResponse>()
    val listMoviesPopular = MutableLiveData<List<Result>>()


    fun getTrendingMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            val movies = withContext(Dispatchers.Default) {
                repositoryImpl.getTrendingMovies()
            }
            _moviesLiveData.value = movies
            _listResultTrendingMovies.value = movies.results
        }
    }

    fun getPopularMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            val popularMovies = withContext(Dispatchers.Default) {
                repositoryImpl.getPopularMovies()
            }
            moviesPopular.value = popularMovies
            listMoviesPopular.value = popularMovies.results
        }
    }

}
