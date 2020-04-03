package com.example.coroutinesteste.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesteste.Filme
import com.example.coroutinesteste.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: MainRepository) : ViewModel() {

     val moviesLiveData = MutableLiveData<List<Filme>>()

    fun getFilmesCoroutines(){
        CoroutineScope(Dispatchers.Main).launch{
            val movies = withContext(Dispatchers.Default) {
                repository.getFilmesCoroutines()
            }
            moviesLiveData.value = movies
        }
    }
}
