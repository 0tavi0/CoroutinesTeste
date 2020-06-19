package com.example.coroutinesteste.ui.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesteste.base.AdapterViewModel
import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.domain.response.Result
import com.example.coroutinesteste.repository.MainMainRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryMoviesViewModel(private val repositoryImpl: MainMainRepositoryImpl) : ViewModel(), AdapterViewModel<Result> {

    private val _genresMovieResponse = MutableLiveData<ResultWrapper<MoviesResponse>>()
    val genreMovieResponse: LiveData<ResultWrapper<MoviesResponse>> = _genresMovieResponse
    private val _listItems = MutableLiveData<List<Result>>()
    override val items: MutableLiveData<List<Result>> get() =  _listItems

    fun getMoviesGenres(id:Int){
        _genresMovieResponse.value = ResultWrapper.loading()
        CoroutineScope(Dispatchers.Main).launch {
            val response = withContext(Dispatchers.IO){
                repositoryImpl.getMoviesGenres(id)
            }
            when (response) {
                is ResultWrapper.Success -> showGenres(response.value)
                is ResultWrapper.GenericError -> showGenericError(response)
                is ResultWrapper.Error -> _genresMovieResponse.value
            }
        }
    }

    private fun showGenres(value: MoviesResponse) {
        _genresMovieResponse.value = ResultWrapper.Success(value)
        _listItems.value = value.results
    }

    private fun showGenericError(error: ResultWrapper.GenericError) {
        when (error.code) {
            422 -> _genresMovieResponse.value = ResultWrapper.error(error.code)
            401 -> _genresMovieResponse.value = ResultWrapper.error(error.code)
            404 -> _genresMovieResponse.value = ResultWrapper.error(error.code)
            else -> _genresMovieResponse.value = ResultWrapper.error(error.code ?: 1)
        }
    }
}
