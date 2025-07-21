package com.nacho.spaceflight.challenge.domain.util

import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.net.UnknownHostException

suspend fun <T> safeCall(block: suspend () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: HttpException) {
        if (e.code() == 401 || e.code() == 403) {
            Result.Error.Authentication(e)
        } else {
            Result.Error.Network(e)
        }
    } catch (e: UnknownHostException) {
        Result.Error.Network(e)
    } catch (e: JsonSyntaxException) {
        Result.Error.Parsing(e)
    } catch (e: Exception) {
        Result.Error.Unknown(e)
    }
}