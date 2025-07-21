package com.nacho.spaceflight.challenge.domain.repository

import com.nacho.spaceflight.challenge.data.model.toDomain
import com.nacho.spaceflight.challenge.data.remote.SpaceFlightApi
import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.domain.util.Result
import com.nacho.spaceflight.challenge.domain.util.safeCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepositoryImpl(
    private val api: SpaceFlightApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ArticleRepository {
    override suspend fun getArticles(query: String): Result<List<Article>> = withContext(dispatcher) {
        safeCall { api.getArticles(query).results.map { it.toDomain() } }
    }

    override suspend fun getArticleById(id: Int): Result<Article> = withContext(dispatcher) {
        safeCall { api.getArticleById(id).toDomain() }
    }
}