package com.example.coroutinesteste.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.coroutinesteste.R
import com.example.coroutinesteste.ResultWrapper
import com.example.coroutinesteste.base.MessageDialogFragment
import com.example.coroutinesteste.domain.response.ErrorResponse
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.ui.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListener()
        observers()
        errorObservers()
    }

    private fun observers() {
        viewModel.resultSearch.observe(viewLifecycleOwner, Observer {
            Log.e("Resultato",it.toString())
        })
    }

    private fun errorObservers(){
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Log.e("Erroorr", ""+it)
            showError(it)
        })
    }

    private fun showError(msg:String) {
            MessageDialogFragment().let {
                it.title = "Erro Genérico"
                it.message = msg
                it.icon = R.drawable.ic_error
                it.show(requireFragmentManager(), "searchFragment")
            }

    }

    private fun setupListener() {
            bt_search.setOnClickListener {
                viewModel.searchMovie(et_search.text.toString())
        }
    }

}
