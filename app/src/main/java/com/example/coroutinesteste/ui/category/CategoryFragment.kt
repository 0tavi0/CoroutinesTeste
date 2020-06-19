package com.example.coroutinesteste.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coroutinesteste.R
import com.example.coroutinesteste.base.MessageDialogFragment
import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.GenreResponse
import com.example.coroutinesteste.ui.category.adapter.CategoryAdapter
import com.example.coroutinesteste.ui.category.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.category_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment() {

    private val viewModel: CategoryViewModel by viewModel()
    private val adapter: CategoryAdapter by lazy { CategoryAdapter(viewModel) }
    private val layoutManager: GridLayoutManager by lazy {
        GridLayoutManager(activity, 2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.category_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null)
            viewModel.getGenres()
        viewModel.genreResponse.observe(viewLifecycleOwner, Observer { response ->
            handleResponse(response)
        })
        setupRecycler()
    }

    private fun handleResponse(resultWrapper: ResultWrapper<GenreResponse>?) {
        rv_genres.post { adapter.notifyDataSetChanged() }
        when (resultWrapper) {
            is ResultWrapper.Loading -> {
                startProgress()
            }
            is ResultWrapper.Success -> {
                stopProgress()
            }
            is ResultWrapper.GenericError -> {
                stopProgress()
                errorObserver()
            }
        }
    }

    private fun errorObserver() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            showError(it)
        })
    }

    private fun startProgress() {
        pb_category.visibility = View.VISIBLE
    }

    private fun stopProgress() {
        pb_category.visibility = View.GONE
    }

    private fun setupRecycler() {
        rv_genres.layoutManager = layoutManager
        rv_genres.adapter = adapter
    }


    private fun showError(msg: String) {
        MessageDialogFragment().let {
            it.title = "Ops!!"
            it.message = msg
            it.onDismiss = View.OnClickListener {
                viewModel.getGenres()
            }
            it.icon = R.drawable.ic_error
            it.show(requireFragmentManager(), "errorDialog")
        }
    }

}
