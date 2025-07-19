package com.nacho.spaceflight.challenge.data.remote

import com.nacho.spaceflight.challenge.data.model.ArticleDTO
import com.nacho.spaceflight.challenge.data.model.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceFlightApi {

    @GET("articles")
    suspend fun getArticles(): ArticlesResponse

    @GET("articles/{id}")
    suspend fun getArticleById(
        @Path("id") id: Int
    ): ArticleDTO
}