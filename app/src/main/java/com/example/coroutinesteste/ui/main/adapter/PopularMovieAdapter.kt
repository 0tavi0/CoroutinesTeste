package com.example.coroutinesteste.ui.main.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinesteste.BuildConfigHelper
import com.example.coroutinesteste.R
import com.example.coroutinesteste.domain.response.Result
import com.example.coroutinesteste.ui.main.DetailMoviePopular
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
        holder.itemView.setOnClickListener {
            DetailMoviePopular.startActivity(holder.itemView.context, listMoviePopular[position])
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgPoster = view.imagePoster


        fun bindView(movies: Result) {
            imgPoster.setImageURI(Uri.parse(BuildConfigHelper.BASE_URL_IMG + movies.poster_path))
        }

    }
}