package com.example.coroutinesteste.ui.search.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.domain.response.Result
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()



    private lateinit var viewModel: SearchViewModel

    @Test
    fun `when view model searchMovie get success then sets search result`()  = runBlocking {


        //Arrange


        //Act


        //Assert
    }

    private val mockedMovieResponse = MoviesResponse(
        1,
        mockedResultList(),
        10,
        100
    )

    private fun mockedResultList(): List<Result> {
        return arrayListOf(
            Result(
                true,
                "",
                listOf(),
                0,
                "",
                "",
                "",
                "",
                0.0,
                "",
                "",
                "",
                false,
                0.0,
                0
            )
        )
    }
}