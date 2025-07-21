package com.nacho.spaceflight.challenge.domain.util

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    sealed class Error : Result<Nothing>() {
        data class Network(val exception: Throwable) : Error()
        data class Parsing(val exception: Throwable) : Error()
        data class Authentication(val exception: Throwable) : Error()
        data class Unknown(val exception: Throwable) : Error()
    }
}