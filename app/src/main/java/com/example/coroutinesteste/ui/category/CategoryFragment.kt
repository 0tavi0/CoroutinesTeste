package com.example.coroutinesteste.ui.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.coroutinesteste.R
import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.GenreResponse
import com.example.coroutinesteste.ui.category.viewmodel.CategoryViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment() {

    private val viewModel: CategoryViewModel by viewModel()

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
        viewModel.genreResponse.observe(viewLifecycleOwner, Observer {
            response -> handleResponse(response)
        })
    }

    private fun handleResponse(resultWrapper: ResultWrapper<GenreResponse>?) {
        when(resultWrapper){
            is ResultWrapper.Success -> Log.e("Sucesso",""+resultWrapper.value)
            is ResultWrapper.GenericError -> Log.e("Erro",""+resultWrapper.code)
            is ResultWrapper.Error -> Log.e("Erro", ""+resultWrapper.errorMessage)
        }
    }


}
