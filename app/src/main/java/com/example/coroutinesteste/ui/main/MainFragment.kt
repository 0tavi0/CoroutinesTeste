package com.example.coroutinesteste.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinesteste.R
import com.example.coroutinesteste.domain.response.Result
import com.example.coroutinesteste.ui.main.adapter.PopularMovieAdapter
import com.example.coroutinesteste.ui.main.viewModel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
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
        configAdapter()

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
        viewModel.getMovies()
    }

    private fun observersTrending() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
          //  message.text = it.total_results.toString()
        })

        viewModel.listResult.observe(viewLifecycleOwner, Observer {
           // title_movie.text = it[0].original_title
        })
    }

    private fun observerPopularMovies() {
        viewModel.listMoviesPopular.observe(viewLifecycleOwner, Observer {
            //title_movie.text = it[0].title
            it.let {
                with(recycler){
                    layoutManager = LinearLayoutManager(activity)
                    setHasFixedSize(true)
                    adapter = PopularMovieAdapter(it)
                }
            }
        })

        viewModel.moviesPopular.observe(viewLifecycleOwner, Observer {
          //  message.text = it.page.toString()

        })
    }

    private fun configAdapter() {
    //    moviePopular = ArrayList()
        recycler.layoutManager = LinearLayoutManager(activity)
       // recycler.adapter = adapter
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
