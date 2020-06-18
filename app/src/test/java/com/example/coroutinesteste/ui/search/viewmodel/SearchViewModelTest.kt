package com.example.coroutinesteste.ui.search.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.MoviesResponse
import com.example.coroutinesteste.domain.response.Result
import com.example.coroutinesteste.repository.MainMainRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString


@ExperimentalCoroutinesApi
class SearchViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val repositoryImpl: MainMainRepositoryImpl = mockk()
    private val listResultSearch: Observer<List<Result>> = mockk(relaxed = true)
    private val errorMessage: Observer<String> = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when searchMovie is called then it should call repository to fetch searchMovies`() {
        coEvery {
            repositoryImpl.searchMovies(anyString())
        } returns ResultWrapper.Success(
            mockedMovieResponse
        )
        instantiateViewModel().searchMovie(anyString())
        coVerify { repositoryImpl.searchMovies(anyString()) }
    }
  @Test
    fun `when searchMovie is called then it should call repository to fetch listResult`() {
        coEvery {
            repositoryImpl.searchMovies(anyString())
        } returns ResultWrapper.Success(
            mockedMovieResponse
        )
        instantiateViewModel().searchMovie(anyString())
        coVerify { listResultSearch.onChanged(mockedMovieResponse.results) }
    }

    @Test
    fun `when searchMovie is called then it should generic error 422`() {
        coEvery {
            repositoryImpl.searchMovies(anyString())
        } returns ResultWrapper.GenericError(422)

        instantiateViewModel().searchMovie(anyString())
        coVerify { errorMessage.onChanged("422 - Entidade não processável") }

    }

    @Test
    fun `when searchMovie is called then it should generic error 401`() {
        coEvery {
            repositoryImpl.searchMovies(anyString())
        } returns ResultWrapper.GenericError(401)

        instantiateViewModel().searchMovie(anyString())
        coVerify { errorMessage.onChanged("401 - Chave de API inválida") }

    }

    @Test
    fun `when searchMovie is called then it should generic error 404`() {
        coEvery {
            repositoryImpl.searchMovies(anyString())
        } returns ResultWrapper.GenericError(404)

        instantiateViewModel().searchMovie(anyString())
        coVerify { errorMessage.onChanged("404 - O recurso que você solicitou não pôde ser encontrado.") }

    }

    @Test
    fun `when searchMovie is called then it should generic error`() {
        coEvery {
            repositoryImpl.searchMovies(anyString())
        } returns ResultWrapper.GenericError(432)

        instantiateViewModel().searchMovie(anyString())
        coVerify { errorMessage.onChanged("432 - Erro Genérico") }

    }

    @Test
    fun `when searchMovie is called then it should generic error when null`() {
        coEvery {
            repositoryImpl.searchMovies(anyString())
        } returns ResultWrapper.GenericError(null)

        instantiateViewModel().searchMovie(anyString())
        coVerify { errorMessage.onChanged("Erro Genérico") }

    }

    @Test
    fun `when searchMovie is called then it should NetworkError`() {
        coEvery {
            repositoryImpl.searchMovies(anyString())
        } returns ResultWrapper.NetworkError

        instantiateViewModel().searchMovie(anyString())
        coVerify { errorMessage.onChanged("error Network") }

    }

    private fun instantiateViewModel(): SearchViewModel {
        val viewModel = SearchViewModel(repositoryImpl)
        viewModel.listMoviesTrendingResult.observeForever(listResultSearch)
        viewModel.errorMessage.observeForever(errorMessage)
        return viewModel
    }

    private val mockedMovieResponse = MoviesResponse(
        1,
        mockedResultList(),
        10,
        100
    )

    private fun mockedResultList(): List<Result> {
        return listOf(
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
            ),
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
