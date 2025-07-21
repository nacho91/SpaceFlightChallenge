package com.nacho.spaceflight.challenge.domain.util

import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import timber.log.Timber
import java.net.UnknownHostException

suspend fun <T> safeCall(block: suspend () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: HttpException) {
        Timber.e(e, "Http error: ${e.code()}")
        if (e.code() == 401 || e.code() == 403) {
            Result.Error.Authentication(e)
        } else {
            Result.Error.Network(e)
        }
    } catch (e: UnknownHostException) {
        Timber.e(e, "Unknown host - no internet")
        Result.Error.Network(e)
    } catch (e: JsonSyntaxException) {
        Timber.e(e, "Parsing error")
        Result.Error.Parsing(e)
    } catch (e: Exception) {
        Timber.e(e, "Unknown error")
        Result.Error.Unknown(e)
    }
}