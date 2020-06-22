package com.example.coroutinesteste.ui.search

import android.content.Context
import android.content.Intent
import com.example.coroutinesteste.domain.response.Result
import com.example.coroutinesteste.ui.home.DetailMoviePopularActivity

class DetailsMovies : DetailMoviePopularActivity() {

    override fun result(): String {
        return MOVIE_DETAILS
    }

    companion object {
        private const val MOVIE_DETAILS =
            "com.example.coroutinesteste.ui.search.DetailsMovies"

        fun startActivity(context: Context, result: Result) {
            val intent = Intent(context, DetailsMovies::class.java)
            intent.putExtra(MOVIE_DETAILS, result)
            context.startActivity(intent)
        }
    }
}