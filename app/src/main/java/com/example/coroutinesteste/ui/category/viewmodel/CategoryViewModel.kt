package com.example.coroutinesteste.ui.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesteste.base.AdapterViewModel
import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.GenreResponse
import com.example.coroutinesteste.domain.response.Genres
import com.example.coroutinesteste.repository.MainMainRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(private val repositoryImpl: MainMainRepositoryImpl) : ViewModel(),
    AdapterViewModel<Genres> {
    private val _genresResponse = MutableLiveData<ResultWrapper<GenreResponse>>()
    val genreResponse: LiveData<ResultWrapper<GenreResponse>> = _genresResponse
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private val _listGenres = MutableLiveData<List<Genres>>()

    override val items: MutableLiveData<List<Genres>>
        get() = _listGenres

    fun getGenres() {
        _genresResponse.value = ResultWrapper.loading()
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
            422 -> _errorMessage.value = "${error.code} - Entidade não processável"
            401 -> _errorMessage.value = "${error.code} - Chave de API inválida"
            404 -> _errorMessage.value =
                "${error.code} - O recurso que você solicitou não pôde ser encontrado."
            null -> _errorMessage.value = "Erro Genérico"
            else -> _errorMessage.value = "${error.code} - Erro Genérico"
        }
    }

    private fun showGenres(result: GenreResponse) {
        _genresResponse.value = ResultWrapper.Success(result)
        _listGenres.value = result.genres
    }


}
