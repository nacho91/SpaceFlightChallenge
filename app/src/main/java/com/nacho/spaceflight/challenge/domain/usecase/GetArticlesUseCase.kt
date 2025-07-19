package com.nacho.spaceflight.challenge.domain.usecase

import com.nacho.spaceflight.challenge.domain.repository.ArticleRepository

class GetArticlesUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke() = repository.getArticles()
}