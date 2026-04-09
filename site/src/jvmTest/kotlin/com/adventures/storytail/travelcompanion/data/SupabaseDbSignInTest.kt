package com.adventures.storytail.travelcompanion.data

import com.adventures.storytail.travelcompanion.models.AuthResponse
import com.adventures.storytail.travelcompanion.models.SupabaseUser
import com.varabyte.kobweb.api.init.InitApiContext
import com.varabyte.kobweb.api.log.Logger
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SupabaseDbSignInTest {

    private val mockLogger = mockk<Logger>(relaxed = true)
    private val mockContext = mockk<InitApiContext>(relaxed = true) {
        every { logger } returns mockLogger
    }

    private fun createMockClient(handler: MockRequestHandleScope.(HttpRequestData) -> HttpResponseData): HttpClient {
        return HttpClient(MockEngine(handler)) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    private val validAuthResponseJson = """
        {
            "access_token": "eyJtoken123",
            "token_type": "bearer",
            "expires_in": 3600,
            "refresh_token": "refresh_abc",
            "user": {"id": "user-1", "email": "test@test.com"}
        }
    """.trimIndent()

    @Test
    fun signInReturnsAuthResponseOn200() = runTest {
        val client = createMockClient {
            respond(
                content = validAuthResponseJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val db = SupabaseDb(
            context = mockContext,
            supabaseUrl = "https://test.supabase.co",
            supabaseKey = "test-key",
            client = client
        )
        val result = db.signIn("test@test.com", "password123")
        assertNotNull(result)
        assertEquals("eyJtoken123", result.accessToken)
        assertEquals("bearer", result.tokenType)
        assertEquals(3600, result.expiresIn)
        assertEquals("refresh_abc", result.refreshToken)
        assertEquals("user-1", result.user?.id)
    }

    @Test
    fun signInReturnsNullOn401() = runTest {
        val client = createMockClient {
            respond(
                content = """{"error":"invalid_grant","error_description":"Invalid login credentials"}""",
                status = HttpStatusCode.Unauthorized,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val db = SupabaseDb(
            context = mockContext,
            supabaseUrl = "https://test.supabase.co",
            supabaseKey = "test-key",
            client = client
        )
        val result = db.signIn("bad@test.com", "wrong")
        assertNull(result)
    }

    @Test
    fun signInReturnsNullOn500() = runTest {
        val client = createMockClient {
            respond(
                content = "Internal Server Error",
                status = HttpStatusCode.InternalServerError
            )
        }
        val db = SupabaseDb(
            context = mockContext,
            supabaseUrl = "https://test.supabase.co",
            supabaseKey = "test-key",
            client = client
        )
        val result = db.signIn("test@test.com", "password")
        assertNull(result)
    }

    @Test
    fun signInReturnsNullWhenUrlIsBlank() = runTest {
        val client = createMockClient {
            error("Should not make any HTTP call when URL is blank")
        }
        val db = SupabaseDb(
            context = mockContext,
            supabaseUrl = "",
            supabaseKey = "test-key",
            client = client
        )
        val result = db.signIn("test@test.com", "password")
        assertNull(result)
    }

    @Test
    fun signInSendsCorrectHeadersAndBody() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = validAuthResponseJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
        val db = SupabaseDb(
            context = mockContext,
            supabaseUrl = "https://test.supabase.co",
            supabaseKey = "my-api-key",
            client = client
        )
        db.signIn("user@example.com", "secret")

        val req = mockEngine.requestHistory.first()
        assertEquals("https://test.supabase.co/auth/v1/token?grant_type=password", req.url.toString())
        assertEquals("my-api-key", req.headers["apikey"])
        assertEquals("Bearer my-api-key", req.headers["Authorization"])
        assertEquals(ContentType.Application.Json, req.body.contentType)
    }

    @Test
    fun signInHandlesNetworkException() = runTest {
        val client = createMockClient {
            throw IOException("Connection refused")
        }
        val db = SupabaseDb(
            context = mockContext,
            supabaseUrl = "https://test.supabase.co",
            supabaseKey = "test-key",
            client = client
        )
        val result = db.signIn("test@test.com", "password")
        assertNull(result)
    }

    @Test
    fun signInHandlesMalformedResponseBody() = runTest {
        val client = createMockClient {
            respond(
                content = "not json at all",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val db = SupabaseDb(
            context = mockContext,
            supabaseUrl = "https://test.supabase.co",
            supabaseKey = "test-key",
            client = client
        )
        val result = db.signIn("test@test.com", "password")
        assertNull(result)
    }
}
