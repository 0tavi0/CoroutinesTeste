package com.example.coroutinesteste

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainRepository : Repository  {

  override suspend fun getFilmesCoroutines(): List<Filme> {
        return withContext(Dispatchers.Default) {
            delay(4000)
            listOf(
                Filme(1, "Filme1"),
                Filme(2, "Filme2")
            )
        }
    }
}