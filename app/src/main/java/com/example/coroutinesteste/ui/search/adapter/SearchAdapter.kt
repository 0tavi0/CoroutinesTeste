package com.example.coroutinesteste.ui.search.adapter

import android.net.Uri
import android.view.View
import com.example.coroutinesteste.BuildConfigHelper
import com.example.coroutinesteste.R
import com.example.coroutinesteste.base.BaseAdapter
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.domain.response.Result
import com.example.coroutinesteste.ui.search.DetailsMovies
import com.example.coroutinesteste.ui.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.item_search.view.*

class SearchAdapter(override val viewModel: SearchViewModel) : BaseAdapter<Result>(viewModel) {
    override val itemLayout = R.layout.item_search

    override fun bindItem(item: Result, view: View, position: Int) {
        view.setOnClickListener {
            DetailsMovies.startActivity(view.context, item)
        }
        view.imagePoster.setImageURI(Uri.parse(BuildConfigHelper.BASE_URL_IMG + item.poster_path))
    }

}