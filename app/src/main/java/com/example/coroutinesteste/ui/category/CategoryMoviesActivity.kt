package com.example.coroutinesteste.ui.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coroutinesteste.R
import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.ui.category.adapter.CategoryMoviesAdapter
import com.example.coroutinesteste.ui.category.viewmodel.CategoryMoviesViewModel
import kotlinx.android.synthetic.main.activity_category_movies.*
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryMoviesActivity : AppCompatActivity() {

    private val viewModel: CategoryMoviesViewModel by viewModel()
    private val layoutManager: GridLayoutManager by lazy {
        GridLayoutManager(this, 2)
    }
    private val adapter: CategoryMoviesAdapter by lazy {
        CategoryMoviesAdapter(viewModel)
    }
    private var idMovie = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_movies)
        idMovie = intent.extras!!.getInt(ID_GENRE)
        viewModel.getMoviesGenres(intent.extras!!.getInt(ID_GENRE))
        observer()
        setupRecycler()
    }

    private fun observer() {
        viewModel.genreMovieResponse.observe(this, Observer { response ->
            handleResponse(response)
        })
    }

    private fun handleResponse(resultWrapper: ResultWrapper<MoviesResponse>?) {
        rv_genres_movies.post { adapter.notifyDataSetChanged() }
        when (resultWrapper) {
            is ResultWrapper.Loading -> {
                starProgress()
            }
            is ResultWrapper.Success -> {
                stopProgress()
            }
            is ResultWrapper.GenericError -> {
                stopProgress()
                showError(resultWrapper.code.toString())
            }
            is ResultWrapper.Error -> {
                stopProgress()
                showError(resultWrapper.errorMessage.toString())
            }
        }
    }

    private fun stopProgress() {
        pb_category_movies.visibility = View.GONE
    }

    private fun starProgress() {
        pb_category_movies.visibility = View.VISIBLE
    }

    private fun setupRecycler() {
        rv_genres_movies.layoutManager = layoutManager
        rv_genres_movies.adapter = adapter
    }

    private fun showError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
//        MessageDialogFragment().let {
//            it.title = "Ops!!"
//            it.message = msg
//            it.onDismiss = View.OnClickListener {
//                viewModel.getMoviesGenres(idMovie)
//            }
//            it.icon = R.drawable.ic_error
//            it.show(, "errorDialog")
    }


    companion object {
        private const val ID_GENRE =
            "com.example.coroutinesteste.ui.CategoryMoviesActivity.ID_GENRE"

        fun startActivity(context: Context, id: Int) {
            val intent = Intent(context, CategoryMoviesActivity::class.java)
            intent.putExtra(ID_GENRE, id)
            context.startActivity(intent)
        }
    }
}
