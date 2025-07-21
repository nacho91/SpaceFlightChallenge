package com.nacho.spaceflight.challenge.domain.usecase

import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.domain.repository.ArticleRepository
import com.nacho.spaceflight.challenge.domain.util.Result

class GetArticlesUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(query: String): Result<List<Article>> {
        return repository.getArticles(query)
    }
}