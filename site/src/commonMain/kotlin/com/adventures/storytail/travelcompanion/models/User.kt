package com.adventures.storytail.travelcompanion.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

expect class User {
    val id: String
    val username: String
    val password: String
}

expect class UserWithoutPassword {
    val id: String
    val username: String
}

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class AuthResponse(
    @SerialName("access_token")
    val accessToken: String = "",
    @SerialName("token_type")
    val tokenType: String = "",
    @SerialName("expires_in")
    val expiresIn: Int = 0,
    @SerialName("refresh_token")
    val refreshToken: String = "",
    val user: SupabaseUser? = null
)

@Serializable
data class SupabaseUser(
    val id: String = "",
    val email: String? = null
)
