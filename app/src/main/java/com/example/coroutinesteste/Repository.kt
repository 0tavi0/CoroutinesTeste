package com.example.coroutinesteste

interface Repository {
    suspend fun getFilmesCoroutines(): List<Filme>
}