package com.adventures.storytail.travelcompanion.data

import com.adventures.storytail.travelcompanion.models.User

interface MongoRepository {
    suspend fun checkUserExistence(user: User): User?
}