package com.example.coroutinesteste.ui.home.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinesteste.BuildConfigHelper
import com.example.coroutinesteste.R
import com.example.coroutinesteste.domain.response.Result
import com.example.coroutinesteste.ui.home.DetailMoviePopularActivity
import com.example.coroutinesteste.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.item_movie.view.*

open class PopularMovieAdapter(private val viewModel : HomeViewModel) :
    RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return viewModel.moviesSize()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = viewModel.movieItem(position)
        holder.bindView(movie)
        holder.itemView.setOnClickListener {
            DetailMoviePopularActivity.startActivity(holder.itemView.context, movie)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgPoster = view.imagePoster


        fun bindView(movies: Result) {
            imgPoster.setImageURI(Uri.parse(BuildConfigHelper.BASE_URL_IMG + movies.poster_path))
        }

    }
}

//val movie = viewModel.movieItem(position)
//holder.title.text = movie.title
//holder.releaseDate.text = viewModel.formatDate(movie.releaseDate)
//val poster = viewModel.buildMoviePoster(movie)
//Ion.with(holder.poster)
//.placeholder(R.drawable.ic_image_black_24dp)
//.error(R.drawable.ic_broken_image_black_24dp)
//.load(poster)
//holder.cardView.setOnClickListener {
//    MovieDetailActivity.startActivity(
//        holder.cardView.context as Activity,
//        movie
//    )
//}
//}
//
//open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    val cardView = itemView.cv_movie!!
//    val poster = itemView.iv_poster_movie!!
//    val title = itemView.tv_title_movie!!
//    val releaseDate = itemView.tv_release_date_movie!!
//}
//}