package com.example.coroutinesteste.ui.main.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinesteste.BuildConfigHelper
import com.example.coroutinesteste.R
import com.example.coroutinesteste.domain.response.Result
import kotlinx.android.synthetic.main.item_movie.view.*

class PopularMovieAdapter(private val listMoviePopular: List<Result>) :
    RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMoviePopular.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(listMoviePopular[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleMovie = view.titleMovie
        private val titleMovieOriginal = view.titleOriginal
        private val voteCount = view.evaluationTotal
        private val imgPoster = view.imagePoster

        fun bindView(movies: Result) {
            titleMovie.text = movies.title
            titleMovieOriginal.text = movies.original_title
            voteCount.text = movies.vote_average.toString()
            imgPoster.setImageURI(Uri.parse(BuildConfigHelper.BASE_URL_IMG + movies.poster_path))
        }

    }
}