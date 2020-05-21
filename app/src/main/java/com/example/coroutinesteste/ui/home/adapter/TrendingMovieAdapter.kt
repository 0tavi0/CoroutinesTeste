package com.example.coroutinesteste.ui.home.adapter

import com.example.coroutinesteste.ui.home.DetailMoviePopularActivity
import com.example.coroutinesteste.ui.home.viewmodel.HomeViewModel

class TrendingMovieAdapter(private val viewModel: HomeViewModel) :
    PopularMovieAdapter(viewModel) {

    override fun getItemCount(): Int {
        return viewModel.moviesTrendingSize()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = viewModel.movieTrendingItem(position)
        holder.bindView(movie)
        holder.itemView.setOnClickListener {
            DetailMoviePopularActivity.startActivity(holder.itemView.context, movie)
        }
    }
}