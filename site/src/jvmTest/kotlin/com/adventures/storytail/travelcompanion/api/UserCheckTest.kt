package com.adventures.storytail.travelcompanion.api

import com.adventures.storytail.travelcompanion.data.SupabaseDb
import com.adventures.storytail.travelcompanion.models.AuthResponse
import com.adventures.storytail.travelcompanion.models.SupabaseUser
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.Data
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.Request
import com.varabyte.kobweb.api.http.Response
import com.varabyte.kobweb.api.log.Logger
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserCheckTest {

    private val json = Json { ignoreUnknownKeys = true }

    private fun createMockContext(
        bodyBytes: ByteArray?,
        supabaseDb: SupabaseDb
    ): Pair<ApiContext, Response> {
        val response = Response()
        val mockReq = mockk<Request> {
            every { body } returns bodyBytes
            every { contentType } returns "application/json"
        }
        val mockData = mockk<Data> {
            every { getValue<SupabaseDb>() } returns supabaseDb
        }
        val mockLogger = mockk<Logger>(relaxed = true)
        val context = mockk<ApiContext> {
            every { req } returns mockReq
            every { res } returns response
            every { data } returns mockData
            every { logger } returns mockLogger
        }
        return context to response
    }

    private fun Response.bodyText(): String = body.toString(Charsets.UTF_8)

    @Test
    fun returnsErrorWhenRequestBodyIsNull() = runTest {
        val mockDb = mockk<SupabaseDb>()
        val (context, response) = createMockContext(null, mockDb)

        userCheck(context)

        assertTrue(response.bodyText().contains("Invalid request body"))
    }

    @Test
    fun returnsErrorWhenBodyIsInvalidJson() = runTest {
        val mockDb = mockk<SupabaseDb>()
        val (context, response) = createMockContext("not valid json".toByteArray(), mockDb)

        userCheck(context)

        assertTrue(response.bodyText().contains("Internal server error"))
    }

    @Test
    fun returnsAuthResponseOnSuccessfulSignIn() = runTest {
        val expectedResponse = AuthResponse(
            accessToken = "token-abc",
            tokenType = "bearer",
            expiresIn = 3600,
            refreshToken = "refresh-xyz",
            user = SupabaseUser(id = "user-1", email = "test@test.com")
        )
        val mockDb = mockk<SupabaseDb> {
            coEvery { signIn(any(), any()) } returns expectedResponse
        }
        val requestBody = """{"email":"test@test.com","password":"password123"}"""
        val (context, response) = createMockContext(requestBody.toByteArray(), mockDb)

        userCheck(context)

        val parsed = json.decodeFromString(AuthResponse.serializer(), response.bodyText())
        assertEquals("token-abc", parsed.accessToken)
        assertEquals("user-1", parsed.user?.id)
    }

    @Test
    fun returnsInvalidCredentialsWhenSignInReturnsNull() = runTest {
        val mockDb = mockk<SupabaseDb> {
            coEvery { signIn(any(), any()) } returns null
        }
        val requestBody = """{"email":"bad@test.com","password":"wrong"}"""
        val (context, response) = createMockContext(requestBody.toByteArray(), mockDb)

        userCheck(context)

        assertTrue(response.bodyText().contains("Invalid credentials"))
    }

    @Test
    fun errorResponseDoesNotLeakExceptionDetails() = runTest {
        val sensitiveMessage = "Connection to postgres://admin:secret@db.internal:5432 failed"
        val mockDb = mockk<SupabaseDb> {
            coEvery { signIn(any(), any()) } throws RuntimeException(sensitiveMessage)
        }
        val requestBody = """{"email":"test@test.com","password":"password"}"""
        val (context, response) = createMockContext(requestBody.toByteArray(), mockDb)

        userCheck(context)

        val body = response.bodyText()
        assertTrue(body.contains("Internal server error"))
        assertTrue(!body.contains("postgres"))
        assertTrue(!body.contains("secret"))
        assertTrue(!body.contains("admin"))
    }
}
