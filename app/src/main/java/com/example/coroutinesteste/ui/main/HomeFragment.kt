package com.example.coroutinesteste.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinesteste.R
import com.example.coroutinesteste.ui.main.adapter.PopularMovieAdapter
import com.example.coroutinesteste.ui.main.adapter.TrendingMovieAdapter
import com.example.coroutinesteste.ui.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
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
        getPopularMovies()
        getTrendingMovies()

    }

    private fun configListeners() {
//        btn_getPopularMovies.setOnClickListener {
//            getPopularMovies()
//        }
//
//        btn_getTrendingMovies.setOnClickListener {
//            getTrendingMovies()
//        }
    }

    private fun getPopularMovies() {
        viewModel.getPopularMovies()
    }

    private fun getTrendingMovies() {
        viewModel.getTrendingMovies()
    }

    private fun observersTrending() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            //  message.text = it.total_results.toString()
        })

        viewModel.listMoviesTrendingResult.observe(viewLifecycleOwner, Observer {
            it.let {
                with(recycler_trending) {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    adapter = TrendingMovieAdapter(it)
                }
            }
        })
    }

    private fun observerPopularMovies() {
        viewModel.listMoviesPopular.observe(viewLifecycleOwner, Observer {
            //title_movie.text = it[0].title
            it.let {
                with(recycler_popular) {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    adapter = PopularMovieAdapter(it)
                }
            }
        })

        viewModel.moviesPopular.observe(viewLifecycleOwner, Observer {
            //  message.text = it.page.toString()


        })
    }


    companion object {
        fun newInstance() = HomeFragment()
    }
}
