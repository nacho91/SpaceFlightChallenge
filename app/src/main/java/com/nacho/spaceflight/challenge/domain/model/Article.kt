package com.nacho.spaceflight.challenge.domain.model

data class Article(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val summary: String,
    val authors: List<Author>
)