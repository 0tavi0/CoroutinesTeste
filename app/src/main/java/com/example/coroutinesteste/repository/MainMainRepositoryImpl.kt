package com.example.coroutinesteste.repository

import com.example.coroutinesteste.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainMainRepositoryImpl : MainRepository {
    override suspend fun getMovies(): List<Movie> {
        return withContext(Dispatchers.Default) {
            delay(4000)
            listOf(
                Movie(1, "Filme1"),
                Movie(2, "Filme2")
            )
        }
    }
}