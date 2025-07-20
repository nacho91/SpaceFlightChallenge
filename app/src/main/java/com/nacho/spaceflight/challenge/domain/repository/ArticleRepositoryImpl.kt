package com.nacho.spaceflight.challenge.domain.repository

import com.nacho.spaceflight.challenge.data.model.toDomain
import com.nacho.spaceflight.challenge.data.remote.SpaceFlightApi
import com.nacho.spaceflight.challenge.domain.model.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class ArticleRepositoryImpl(
    private val api: SpaceFlightApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ArticleRepository {
    override suspend fun getArticles(query: String): List<Article> = withContext(dispatcher) {
        api.getArticles(query).results.map { it.toDomain() }
    }

    override suspend fun getArticleById(id: Int): Article = withContext(dispatcher) {
        api.getArticleById(id).toDomain()
    }
}