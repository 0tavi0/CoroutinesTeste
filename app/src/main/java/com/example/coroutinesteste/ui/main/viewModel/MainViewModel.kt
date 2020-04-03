package com.example.coroutinesteste.ui.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesteste.domain.response.Result
import com.example.coroutinesteste.domain.response.TrendingMoviesResponse
import com.example.coroutinesteste.repository.MainMainRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repositoryImpl: MainMainRepositoryImpl) : ViewModel() {

    val moviesLiveData = MutableLiveData<TrendingMoviesResponse>()
    val listResult = MutableLiveData<List<Result>>()
    fun getMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            val movies = withContext(Dispatchers.Default) {
                repositoryImpl.getMovies()
            }
            moviesLiveData.value = movies
            listResult.value = movies.results
        }
    }

}
