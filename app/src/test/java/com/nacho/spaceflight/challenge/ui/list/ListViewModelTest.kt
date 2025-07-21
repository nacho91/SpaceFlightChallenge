package com.nacho.spaceflight.challenge.ui.list

import app.cash.turbine.test
import com.nacho.spaceflight.challenge.R
import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.domain.usecase.GetArticlesUseCase
import com.nacho.spaceflight.challenge.domain.util.Result
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var getArticlesUseCase: GetArticlesUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        getArticlesUseCase = mockk()
    }

    @Test
    fun `user sees articles when open the app`() = runTest {
        coEvery { getArticlesUseCase(any()) } returns Result.Success(listOf(
            Article(1, "Test Article", "", summary = "", authors = listOf())
        ))

        val viewModel = ListViewModel(getArticlesUseCase)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val finalState = awaitItem()
            assertEquals(false, finalState.isLoading)
            assertEquals(1, finalState.articles.size)
            assertEquals("Test Article", finalState.articles.first().title)
        }
    }

    @Test
    fun `user sees network error when opens the app`() = runTest {
        coEvery { getArticlesUseCase(any()) } returns Result.Error.Network(RuntimeException("Network error"))

        val viewModel = ListViewModel(getArticlesUseCase)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val finalState = awaitItem()
            assertEquals(false, finalState.isLoading)
            assertEquals(R.string.error_network, finalState.errorMessage)
            assertEquals(0, finalState.articles.size)
        }
    }

    @Test
    fun `user sees unknown error when opens the app`() = runTest {
        coEvery { getArticlesUseCase(any()) } returns Result.Error.Unknown(RuntimeException("Unknown error"))

        val viewModel = ListViewModel(getArticlesUseCase)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val finalState = awaitItem()
            assertEquals(false, finalState.isLoading)
            assertEquals(R.string.error_unknown, finalState.errorMessage)
            assertEquals(0, finalState.articles.size)
        }
    }


    @Test
    fun `user search article`() = runTest {
        coEvery { getArticlesUseCase(any()) } returns Result.Success(listOf(
            Article(1, "Test Search Article", "", summary = "", authors = listOf())
        ))

        val viewModel = ListViewModel(getArticlesUseCase)

        viewModel.searchQuery.test {
            assertEquals("", awaitItem())

            viewModel.onSearchQueryChanged("Query")
            assertEquals("Query", awaitItem())
        }
    }
}