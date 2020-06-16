package com.example.coroutinesteste.ui.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.coroutinesteste.domain.response.Result
import com.example.coroutinesteste.ui.home.DetailMoviePopularActivity

class DetailsMoviesActivity : DetailMoviePopularActivity() {

    override fun result(): String {
        return POPULAR_MOVIE_GENRE
    }
    companion object {
        private const val POPULAR_MOVIE_GENRE =
            "com.example.coroutinesteste.ui.main.DetailMovieActivity.POPULAR_MOVIE_GENRE"
        fun startActivity(context: Context, result: Result) {
            val intent = Intent(context, DetailsMoviesActivity::class.java)
            intent.putExtra(POPULAR_MOVIE_GENRE, result)
            context.startActivity(intent)
        }
    }
}