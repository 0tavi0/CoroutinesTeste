package com.example.coroutinesteste.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.coroutinesteste.BuildConfigHelper
import com.example.coroutinesteste.R
import com.example.coroutinesteste.base.DetailMovieBase
import com.example.coroutinesteste.domain.response.Result
import kotlinx.android.synthetic.main.detail_popular_movie_activity.*

class DetailMoviePopular : DetailMovieBase() {
    override var layout = R.layout.detail_popular_movie_activity
    lateinit var movie: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = intent!!.extras!!.getSerializable(POPULAR_MOVIE) as Result
        configView()
    }

    private fun configView() {
        tv_title_original.text = movie.title
        tv_description.text = movie.overview
        img_details_movie_popular.setImageURI(BuildConfigHelper.BASE_URL_IMG + movie.backdrop_path)
    }

    companion object {
        private const val POPULAR_MOVIE =
            "com.example.coroutinesteste.ui.main.DetailMoviePopular.POPULAR_MOVIE"

        fun startActivity(context: Context, result: Result) {
            val intent = Intent(context, DetailMoviePopular::class.java)
            intent.putExtra(POPULAR_MOVIE, result)
            context.startActivity(intent)
        }
    }
}