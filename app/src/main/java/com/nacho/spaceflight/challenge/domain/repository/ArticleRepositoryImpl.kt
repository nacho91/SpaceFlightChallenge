package com.nacho.spaceflight.challenge.domain.repository

import com.nacho.spaceflight.challenge.data.model.toDomain
import com.nacho.spaceflight.challenge.data.remote.SpaceFlightApi
import com.nacho.spaceflight.challenge.domain.model.Article

class ArticleRepositoryImpl(
    private val api: SpaceFlightApi
) : ArticleRepository {
    override suspend fun getArticles(): List<Article> {
        return api.getArticles().results.map { it.toDomain() }
    }

    override suspend fun getArticleById(id: Int): Article {
        return api.getArticleById(id).toDomain()
    }
}