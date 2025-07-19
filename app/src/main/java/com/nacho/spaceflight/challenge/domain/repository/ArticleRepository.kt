package com.nacho.spaceflight.challenge.domain.repository

import com.nacho.spaceflight.challenge.domain.model.Article

interface ArticleRepository {
    suspend fun getArticles(): List<Article>

    suspend fun getArticleById(id: Int): Article
}