package com.example.coroutinesteste.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.coroutinesteste.R
import com.example.coroutinesteste.ui.main.viewModel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configListeners()
        observersTrending()
        observerPopularMovies()

    }

    private fun configListeners() {
        btn_getPopularMovies.setOnClickListener {
            getPopularMovies()
        }

        btn_getTrendingMovies.setOnClickListener {
            getTrendingMovies()
        }
    }

    private fun getPopularMovies() {
        viewModel.getPopularMovies()
    }

    private fun getTrendingMovies() {
        viewModel.getMovies()
    }

    private fun observersTrending() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            message.text = it.total_results.toString()
        })

        viewModel.listResult.observe(viewLifecycleOwner, Observer {
            title_movie.text = it[0].original_title
        })
    }

    private fun observerPopularMovies() {
        viewModel.listMoviesPopular.observe(viewLifecycleOwner, Observer {
            title_movie.text = it[0].title
        })

        viewModel.moviesPopular.observe(viewLifecycleOwner, Observer {
            message.text = it.page.toString()

        })
    }
}
