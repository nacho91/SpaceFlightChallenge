package com.nacho.spaceflight.challenge.domain.usecase

import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.domain.repository.ArticleRepository
import com.nacho.spaceflight.challenge.domain.util.Result

class GetArticleByIdUseCase(
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(id: Int): Result<Article> {
        return articleRepository.getArticleById(id)
    }
}