package com.example.coroutinesteste.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinesteste.MainRepository
import com.example.coroutinesteste.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, MainViewModel.MainViewModelFactory(MainRepository())).get(
                MainViewModel::class.java
            )

        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer { filmes ->
            message.text = filmes[0].titulo
        })

        viewModel.getFilmesCoroutines()
    }


}
