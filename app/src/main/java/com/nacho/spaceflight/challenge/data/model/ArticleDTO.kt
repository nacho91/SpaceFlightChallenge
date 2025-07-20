package com.nacho.spaceflight.challenge.data.model

import com.google.gson.annotations.SerializedName
import com.nacho.spaceflight.challenge.domain.model.Article

data class ArticleDTO(
    val id: Int,
    val title: String,
    @SerializedName("image_url") val imageUrl: String,
    val authors: List<AuthorDTO>
)

fun ArticleDTO.toDomain() = Article(
    id = id,
    title = title,
    imageUrl = imageUrl,
    authors = authors.map { it.toDomain() }
)