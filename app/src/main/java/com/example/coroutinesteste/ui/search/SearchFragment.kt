package com.example.coroutinesteste.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinesteste.R
import com.example.coroutinesteste.base.MessageDialogFragment
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
        viewModel.listMoviesTrendingResult.observe(viewLifecycleOwner, Observer {
            it.let {
                with(recycler_search) {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    setHasFixedSize(true)
                 //   adapter = SearchAdapter(it)
                }
            }
        })
    }

    private fun errorObservers() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            showError(it)
        })
    }

    private fun showError(msg: String) {
        MessageDialogFragment().let {
            it.title = getString(R.string.title_erro_dialog)
            it.message = msg
            it.icon = R.drawable.ic_error
            it.show(requireFragmentManager(), "searchFragment")
        }

    }

    private fun setupListener() {
        bt_search.setOnClickListener {
            val query = et_search.text.toString()
            if (query.isEmpty()) {
                showError(getString(R.string.erro_field_empty))
                return@setOnClickListener
            }
            viewModel.searchMovie(query)
        }
    }

}
