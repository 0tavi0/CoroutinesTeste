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
import com.example.coroutinesteste.ui.home.adapter.TrendingMovieAdapter
import com.example.coroutinesteste.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModel()
    private val adapter: PopularMovieAdapter by lazy { PopularMovieAdapter(viewModel) }
    private val adapterTrendingMovieAdapter: TrendingMovieAdapter by lazy { TrendingMovieAdapter(viewModel) }
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
    }
    private val layoutManagerTrending: LinearLayoutManager by lazy {
        LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
    }
    private val page = 1
    private val pageTrending = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) viewModel.getPopularMovies(page)
        setupRecycler()
        setupRecyclerTrending()
        viewModel.responseLiveData.observe(
            viewLifecycleOwner,
            Observer { response -> handleResponse(response) })
        errorObservers()
        getTrendingMovies()
        viewModel.responseTrending.observe(viewLifecycleOwner, Observer { response ->
            handleResponseTrending(response)
        })
    }

    private fun setupRecycler() {
        recycler_popular.layoutManager = layoutManager
        recycler_popular.adapter = adapter
        recycler_popular.addOnScrollListener(scrollListener())
    }

    private fun setupRecyclerTrending() {
        recycler_trending.layoutManager = layoutManagerTrending
        recycler_trending.adapter = adapterTrendingMovieAdapter
        recycler_trending.addOnScrollListener(scrollListenerTrending())
    }

    private fun scrollListenerTrending(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                viewModel.fetchMoreMoviesTrending(lastVisibleItem)
            }
        }
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
    private fun handleResponseTrending(response: ResultWrapper<MoviesResponse>?) {
        if (response == null) return
        recycler_trending.post { adapterTrendingMovieAdapter.notifyDataSetChanged() }
        when (response) {
            is ResultWrapper.Loading -> {
                startLoading()
            }
            is ResultWrapper.Success -> {
                stopLoading()
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
        viewModel.getTrendingMovies(pageTrending)
    }

    private fun errorObservers() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
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
