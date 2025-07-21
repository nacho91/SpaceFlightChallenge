package com.nacho.spaceflight.challenge.domain.util

import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class ResultUtilsTest {

    @Test
    fun `safeCall returns Success when no exception`() = runTest {
        val result = safeCall { "Success Value" }
        assertTrue(result is Result.Success)
        assertEquals("Success Value", (result as Result.Success).data)
    }

    @Test
    fun `safeCall returns Network error on UnknownHostException`() = runTest {
        val result = safeCall<String> { throw UnknownHostException("No internet") }
        assertTrue(result is Result.Error.Network)
    }

    @Test
    fun `safeCall returns Parsing error on JsonDataException`() = runTest {
        val result = safeCall<String> { throw JsonSyntaxException("Parsing failed") }
        assertTrue(result is Result.Error.Parsing)
    }

    @Test
    fun `safeCall returns Authentication error on HttpException with 401`() = runTest {
        val httpException = HttpException(Response.error<String>(401, "".toResponseBody()))
        val result = safeCall<String> { throw httpException }
        assertTrue(result is Result.Error.Authentication)
    }

    @Test
    fun `safeCall returns Network error on HttpException with 500`() = runTest {
        val httpException = HttpException(Response.error<String>(500, "".toResponseBody()))
        val result = safeCall<String> { throw httpException }
        assertTrue(result is Result.Error.Network)
    }

    @Test
    fun `safeCall returns Unknown error on generic Exception`() = runTest {
        val result = safeCall<String> { throw IllegalStateException("Unexpected") }
        assertTrue(result is Result.Error.Unknown)
    }
}