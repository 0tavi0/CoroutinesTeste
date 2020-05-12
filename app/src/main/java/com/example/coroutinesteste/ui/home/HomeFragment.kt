package com.example.coroutinesteste.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinesteste.R
import com.example.coroutinesteste.base.MessageDialogFragment
import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.ui.home.adapter.PopularMovieAdapter
import com.example.coroutinesteste.ui.home.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()
    private val adapter: PopularMovieAdapter by lazy { PopularMovieAdapter(viewModel) }
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
    }
    private val page = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) viewModel.getPopularMovies(page)
        recycler_popular.layoutManager = layoutManager
        recycler_popular.adapter = adapter
        recycler_popular.addOnScrollListener(scrollListener())
        viewModel.responseLiveData.observe(
            viewLifecycleOwner,
            Observer { response -> handleResponse(response) })
        errorObservers()
    }

    private fun handleResponse(response: ResultWrapper<MoviesResponse>?) {
        if (response == null) return
        recycler_popular.post { adapter.notifyDataSetChanged() }
        when (response) {
            is ResultWrapper.Loading -> {
                startLoading()
            }
            is ResultWrapper.Success -> {
                stopLoading()
                // showList()
                Log.e("Result", "Sucesso")
            }

            is ResultWrapper.GenericError -> {
                stopLoading()
                showError(response.code.toString())

            }
            is ResultWrapper.Error -> {
                stopLoading()
                showError(response.errorMessage.toString())

            }
        }

    }

    private fun getTrendingMovies() {
        viewModel.getTrendingMovies()
    }

    private fun observersTrending() {
        viewModel.listMoviesTrendingResult.observe(viewLifecycleOwner, Observer {
            it.let {
                with(recycler_trending) {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    // adapter = TrendingMovieAdapter(it)
                }
            }
        })
    }

    private fun errorObservers() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Log.e("erro", "" + it)
            showError(it)
        })
    }

    private fun scrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                viewModel.fetchMoreMovies(lastVisibleItem)
            }
        }
    }

    private fun startLoading() {
        pb_movie.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        pb_movie.visibility = View.GONE
    }

    private fun showError(msg: String) {
        MessageDialogFragment().let {
            it.title = "Ops!!"
            it.message = msg
            it.onDismiss = View.OnClickListener {
                viewModel.getPopularMovies(page)
            }
            it.icon = R.drawable.ic_error
            it.show(requireFragmentManager(), "errorDialog")
        }
    }
}
