package com.adventures.storytail.travelcompanion.models

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class AuthSecurityTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun loginRequestWithSqlInjectionPayloadPreservesLiterally() {
        val maliciousEmail = "'; DROP TABLE users; --"
        val maliciousPassword = "' OR '1'='1"
        val request = LoginRequest(email = maliciousEmail, password = maliciousPassword)
        val serialized = json.encodeToString(LoginRequest.serializer(), request)
        val deserialized = json.decodeFromString(LoginRequest.serializer(), serialized)
        assertEquals(maliciousEmail, deserialized.email)
        assertEquals(maliciousPassword, deserialized.password)
    }

    @Test
    fun loginRequestWithXssPayloadPreservesLiterally() {
        val xssEmail = "<script>alert('xss')</script>"
        val request = LoginRequest(email = xssEmail, password = "test")
        val serialized = json.encodeToString(LoginRequest.serializer(), request)
        val deserialized = json.decodeFromString(LoginRequest.serializer(), serialized)
        assertEquals(xssEmail, deserialized.email)
        // Angle brackets should appear in the JSON string, not as unescaped HTML
        assertTrue(serialized.contains("<script>") || serialized.contains("\\u003c"))
    }

    @Test
    fun authResponseWithEmptyAccessToken() {
        val jsonStr = """{"access_token":""}"""
        val response = json.decodeFromString(AuthResponse.serializer(), jsonStr)
        assertTrue(response.accessToken.isBlank())
    }

    @Test
    fun authResponseWithWhitespaceOnlyAccessToken() {
        val jsonStr = """{"access_token":"   "}"""
        val response = json.decodeFromString(AuthResponse.serializer(), jsonStr)
        assertTrue(response.accessToken.isBlank())
    }

    @Test
    fun loginRequestWithExtremelyLongEmail() {
        val longEmail = "a".repeat(10_000) + "@example.com"
        val request = LoginRequest(email = longEmail, password = "pass")
        val serialized = json.encodeToString(LoginRequest.serializer(), request)
        val deserialized = json.decodeFromString(LoginRequest.serializer(), serialized)
        assertEquals(longEmail, deserialized.email)
    }

    @Test
    fun loginRequestWithUnicodeAndEmojiInPassword() {
        val unicodePassword = "\uD83D\uDE00\u00FC\u00E9\u4E16\u754C"
        val request = LoginRequest(email = "test@test.com", password = unicodePassword)
        val serialized = json.encodeToString(LoginRequest.serializer(), request)
        val deserialized = json.decodeFromString(LoginRequest.serializer(), serialized)
        assertEquals(unicodePassword, deserialized.password)
    }

    @Test
    fun malformedJsonThrowsSerializationException() {
        assertFailsWith<SerializationException> {
            json.decodeFromString(LoginRequest.serializer(), "not json at all")
        }
    }

    @Test
    fun partialJsonMissingRequiredFieldThrows() {
        assertFailsWith<SerializationException> {
            json.decodeFromString(LoginRequest.serializer(), """{"email":"a@b.com"}""")
        }
    }
}
