package com.example.coroutinesteste.ui.category.adapter

import android.util.Log
import android.view.View
import com.example.coroutinesteste.R
import com.example.coroutinesteste.base.BaseAdapter
import com.example.coroutinesteste.domain.response.Genres
import com.example.coroutinesteste.ui.category.CategoryMoviesActivity
import com.example.coroutinesteste.ui.category.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.item_genres.view.*


class CategoryAdapter(override val viewModel: CategoryViewModel) : BaseAdapter<Genres>(viewModel) {
    override val itemLayout = R.layout.item_genres

    override fun bindItem(item: Genres, view: View, position: Int) {
        view.tv_name_genre.text = item.name
        view.setOnClickListener {
            CategoryMoviesActivity.startActivity(view.context, item.id)
        }
    }

}