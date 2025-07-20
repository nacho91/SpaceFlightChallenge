package com.nacho.spaceflight.challenge.domain.usecase

import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.domain.repository.ArticleRepository

class GetArticleByIdUseCase(
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(id: Int): Article {
        return articleRepository.getArticleById(id)
    }
}