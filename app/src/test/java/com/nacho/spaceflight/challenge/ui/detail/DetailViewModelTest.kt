package com.nacho.spaceflight.challenge.ui.detail

import app.cash.turbine.test
import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.domain.usecase.GetArticleByIdUseCase
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
class DetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var getArticleByIdUseCase: GetArticleByIdUseCase

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        getArticleByIdUseCase = mockk()

        viewModel = DetailViewModel(getArticleByIdUseCase)
    }

    @Test
    fun `user opens article`() = runTest {
        coEvery { getArticleByIdUseCase(any()) } returns
                Article(1, "Test Article", "", summary = "", authors = listOf())

        viewModel.loadArticle(1)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val finalState = awaitItem()
            assertEquals(false, finalState.isLoading)
            assertEquals("Test Article", finalState.article?.title)
        }
    }

    @Test
    fun `user sees error when opens article`() = runTest {
        coEvery { getArticleByIdUseCase(any()) } throws RuntimeException("Network error")

        viewModel.loadArticle(1)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val finalState = awaitItem()
            assertEquals(false, finalState.isLoading)
            assertEquals(true, finalState.error)
            assertEquals(null, finalState.article)
        }
    }
}