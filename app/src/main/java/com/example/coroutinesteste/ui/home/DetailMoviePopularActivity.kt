package com.example.coroutinesteste.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.example.coroutinesteste.R
import com.example.coroutinesteste.base.DetailMovieBase
import com.example.coroutinesteste.domain.response.Result
import kotlinx.android.synthetic.main.detail_popular_movie_activity.*

open class DetailMoviePopularActivity : DetailMovieBase() {
    override var layout = R.layout.detail_popular_movie_activity
    lateinit var movie: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = intent!!.extras!!.getSerializable(result()) as Result
        configView()
    }

    protected open fun result() : String{
        return POPULAR_MOVIE
    }

    private fun configView() {
        tv_title_original.text = movie.title
        tv_description.text = movie.overview
        if (movie.vote_average <= 6.0) {
            tv_vote_average.setTextColor(Color.RED)
        }
        tv_vote_average.text = movie.vote_average.toString()
        tv_vote_count.text = movie.vote_count.toString()
        img_details_movie_popular.setImageURI("https://image.tmdb.org/t/p/w500" + movie.backdrop_path)
    }

    companion object {
        private const val POPULAR_MOVIE =
            "com.example.coroutinesteste.ui.main.DetailMoviePopular.POPULAR_MOVIE"

        fun startActivity(context: Context, result: Result) {
            val intent = Intent(context, DetailMoviePopularActivity::class.java)
            intent.putExtra(POPULAR_MOVIE, result)
            context.startActivity(intent)
        }
    }
}