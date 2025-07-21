package com.nacho.spaceflight.challenge.domain.repository

import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.domain.util.Result

interface ArticleRepository {
    suspend fun getArticles(query: String): Result<List<Article>>

    suspend fun getArticleById(id: Int): Result<Article>
}