package com.example.coroutinesteste.ui.category.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.GenreResponse
import com.example.coroutinesteste.domain.response.Genres
import com.example.coroutinesteste.repository.MainMainRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(private val repositoryImpl: MainMainRepositoryImpl) : ViewModel() {
    private val _genresResponse = MutableLiveData<ResultWrapper<GenreResponse>>()
    val genreResponse: LiveData<ResultWrapper<GenreResponse>> = _genresResponse
    val listGenres = ArrayList<Genres>()

    fun getGenres() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = withContext(Dispatchers.Default) {
                repositoryImpl.getGenres()
            }
            when (response) {
                is ResultWrapper.Success -> showGenres(response.value)
                is ResultWrapper.GenericError -> showGenericError(response)
                is ResultWrapper.Error -> _genresResponse.value
            }
        }
    }


    private fun showGenericError(error: ResultWrapper.GenericError) {
        when (error.code) {
            422 -> _genresResponse.value = ResultWrapper.error(error.code)
            401 -> _genresResponse.value = ResultWrapper.error(error.code)
            404 -> _genresResponse.value = ResultWrapper.error(error.code)
            else -> _genresResponse.value = ResultWrapper.error(error.code ?: 1)
        }
    }

    private fun showGenres(result: GenreResponse) {
//        listGenres.add(result.list)
//        Log.e("Lista", ""+listGenres.size)
        _genresResponse.value = ResultWrapper.Success(result)
    }

}
