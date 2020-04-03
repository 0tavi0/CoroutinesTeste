package com.example.coroutinesteste.domain.response

import com.example.coroutinesteste.domain.response.Result

data class TrendingMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)