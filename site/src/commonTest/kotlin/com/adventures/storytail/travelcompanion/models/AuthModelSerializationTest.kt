package com.adventures.storytail.travelcompanion.models

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AuthModelSerializationTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun loginRequestSerializesToExpectedJson() {
        val request = LoginRequest(email = "user@example.com", password = "pass123")
        val serialized = json.encodeToString(LoginRequest.serializer(), request)
        assertTrue(serialized.contains("\"email\":\"user@example.com\""))
        assertTrue(serialized.contains("\"password\":\"pass123\""))
    }

    @Test
    fun loginRequestDeserializesFromJson() {
        val jsonStr = """{"email":"user@example.com","password":"pass123"}"""
        val request = json.decodeFromString(LoginRequest.serializer(), jsonStr)
        assertEquals("user@example.com", request.email)
        assertEquals("pass123", request.password)
    }

    @Test
    fun authResponseDeserializesWithAllFields() {
        val jsonStr = """
            {
                "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
                "token_type": "bearer",
                "expires_in": 3600,
                "refresh_token": "refresh_abc123",
                "user": {"id": "user-id-123", "email": "user@example.com"}
            }
        """.trimIndent()
        val response = json.decodeFromString(AuthResponse.serializer(), jsonStr)
        assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9", response.accessToken)
        assertEquals("bearer", response.tokenType)
        assertEquals(3600, response.expiresIn)
        assertEquals("refresh_abc123", response.refreshToken)
        assertEquals("user-id-123", response.user?.id)
        assertEquals("user@example.com", response.user?.email)
    }

    @Test
    fun authResponseDefaultsForMissingOptionalFields() {
        val jsonStr = "{}"
        val response = json.decodeFromString(AuthResponse.serializer(), jsonStr)
        assertEquals("", response.accessToken)
        assertEquals("", response.tokenType)
        assertEquals(0, response.expiresIn)
        assertEquals("", response.refreshToken)
        assertNull(response.user)
    }

    @Test
    fun authResponseIgnoresUnknownKeys() {
        val jsonStr = """
            {
                "access_token": "token123",
                "token_type": "bearer",
                "expires_in": 3600,
                "refresh_token": "refresh123",
                "expires_at": 1700000000,
                "provider_token": "some_provider_token",
                "user": {"id": "abc", "email": "a@b.com", "app_metadata": {}, "aud": "authenticated"}
            }
        """.trimIndent()
        val response = json.decodeFromString(AuthResponse.serializer(), jsonStr)
        assertEquals("token123", response.accessToken)
        assertEquals("abc", response.user?.id)
    }

    @Test
    fun supabaseUserWithNullEmail() {
        val jsonStr = """{"id":"abc"}"""
        val user = json.decodeFromString(SupabaseUser.serializer(), jsonStr)
        assertEquals("abc", user.id)
        assertNull(user.email)
    }

    @Test
    fun supabaseUserWithEmailPresent() {
        val jsonStr = """{"id":"abc","email":"x@y.com"}"""
        val user = json.decodeFromString(SupabaseUser.serializer(), jsonStr)
        assertEquals("abc", user.id)
        assertEquals("x@y.com", user.email)
    }

    @Test
    fun authResponseRoundTripSerialization() {
        val original = AuthResponse(
            accessToken = "token-abc",
            tokenType = "bearer",
            expiresIn = 7200,
            refreshToken = "refresh-xyz",
            user = SupabaseUser(id = "user-1", email = "test@test.com")
        )
        val serialized = json.encodeToString(AuthResponse.serializer(), original)
        val deserialized = json.decodeFromString(AuthResponse.serializer(), serialized)
        assertEquals(original, deserialized)
    }
}
