package com.nacho.spaceflight.challenge.ui.list

import app.cash.turbine.test
import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.domain.usecase.GetArticlesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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
        coEvery { getArticlesUseCase(any()) } returns listOf(
            Article(1, "Test Article", "", summary = "", authors = listOf())
        )

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
    fun `user sees error when opens the app`() = runTest {
        coEvery { getArticlesUseCase(any()) } throws RuntimeException("Network error")

        val viewModel = ListViewModel(getArticlesUseCase)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val finalState = awaitItem()
            assertEquals(false, finalState.isLoading)
            assertEquals(true, finalState.error)
            assertEquals(0, finalState.articles.size)
        }
    }

    @Test
    fun `user search article`() = runTest {
        val viewModel = ListViewModel(getArticlesUseCase)

        viewModel.searchQuery.test {
            assertEquals("", awaitItem())

            viewModel.onSearchQueryChanged("Query")
            assertEquals("Query", awaitItem())
        }
    }
}