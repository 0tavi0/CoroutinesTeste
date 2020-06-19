package com.example.coroutinesteste.ui.category.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.coroutinesteste.base.ResultWrapper
import com.example.coroutinesteste.domain.response.GenreResponse
import com.example.coroutinesteste.domain.response.Genres
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

@ExperimentalCoroutinesApi
class CategoryViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val repositoryImpl: MainMainRepositoryImpl = mockk()
    private val errorMessage: Observer<String> = mockk(relaxed = true)
    private val itemsMocked: Observer<List<Genres>> = mockk(relaxed = true)

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
    fun `when genres is called then it should call repository to fetch genres`() {
        coEvery {
            repositoryImpl.getGenres()
        } returns ResultWrapper.Success(mockedGenreResponse)

        instantiateViewModel().getGenres()
        coVerify { repositoryImpl.getGenres() }
    }

    @Test
    fun `when genres is called then it should generic error 422`() {
        coEvery {
            repositoryImpl.getGenres()
        } returns ResultWrapper.GenericError(422)

        instantiateViewModel().getGenres()
        coVerify { errorMessage.onChanged("422 - Entidade não processável") }

    }

    @Test
    fun `when genres is called then it should generic error 401`() {
        coEvery {
            repositoryImpl.getGenres()
        } returns ResultWrapper.GenericError(401)

        instantiateViewModel().getGenres()
        coVerify { errorMessage.onChanged("401 - Chave de API inválida") }

    }

    @Test
    fun `when genres is called then it should generic error 404`() {
        coEvery {
            repositoryImpl.getGenres()
        } returns ResultWrapper.GenericError(404)

        instantiateViewModel().getGenres()
        coVerify { errorMessage.onChanged("404 - O recurso que você solicitou não pôde ser encontrado.") }

    }

    @Test
    fun `when genres is called then it should generic error null`() {
        coEvery {
            repositoryImpl.getGenres()
        } returns ResultWrapper.GenericError()

        instantiateViewModel().getGenres()
        coVerify { errorMessage.onChanged("Erro Genérico") }

    }

    @Test
    fun `when genres is called then it should generic error anyCode`() {
        coEvery {
            repositoryImpl.getGenres()
        } returns ResultWrapper.GenericError(123)

        instantiateViewModel().getGenres()
        coVerify { errorMessage.onChanged("123 - Erro Genérico") }

    }

    @Test
    fun `when items is called then it should list Genres`() {
        coEvery {
            repositoryImpl.getGenres()
        } returns ResultWrapper.Success(mockedGenreResponse)

        instantiateViewModel().getGenres()

        coVerify { itemsMocked.onChanged(mockedListGenres()) }


    }

    private fun instantiateViewModel(): CategoryViewModel {
        val viewModel = CategoryViewModel(repositoryImpl)
        viewModel.errorMessage.observeForever(errorMessage)
        viewModel.items.observeForever(itemsMocked)
        return viewModel
    }

    private val mockedGenreResponse = GenreResponse(mockedListGenres())

    private fun mockedListGenres(): List<Genres> {
        return listOf(
            Genres(1, "Ação"),
            Genres(2, "Terror")
        )
    }

}