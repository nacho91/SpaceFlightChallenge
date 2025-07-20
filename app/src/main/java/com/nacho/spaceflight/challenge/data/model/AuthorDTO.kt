package com.nacho.spaceflight.challenge.data.model

import com.nacho.spaceflight.challenge.domain.model.Author

data class AuthorDTO(
    val name: String
)

fun AuthorDTO.toDomain() = Author(
    name = name
)