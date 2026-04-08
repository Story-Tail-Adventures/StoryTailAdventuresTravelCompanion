package com.adventures.storytail.travelcompanion.data

import com.adventures.storytail.travelcompanion.models.AuthResponse

interface UserRepository {
    suspend fun signIn(email: String, password: String): AuthResponse?
}
