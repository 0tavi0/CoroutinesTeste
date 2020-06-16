package com.example.coroutinesteste.ui.category.adapter

import android.net.Uri
import android.view.View
import com.example.coroutinesteste.BuildConfigHelper
import com.example.coroutinesteste.R
import com.example.coroutinesteste.base.BaseAdapter
import com.example.coroutinesteste.domain.response.Result
import com.example.coroutinesteste.ui.category.DetailsMoviesActivity
import com.example.coroutinesteste.ui.category.viewmodel.CategoryMoviesViewModel
import kotlinx.android.synthetic.main.item_movie.view.*

class CategoryMoviesAdapter(override val viewModel: CategoryMoviesViewModel) :
    BaseAdapter<Result>(viewModel) {

    override val itemLayout = R.layout.item_movie
    override fun bindItem(item: Result, view: View, position: Int) {
        view.setOnClickListener {
            DetailsMoviesActivity.startActivity(view.context, item)
        }
        view.imagePoster.setImageURI(Uri.parse(BuildConfigHelper.BASE_URL_IMG + item.poster_path))
    }
}