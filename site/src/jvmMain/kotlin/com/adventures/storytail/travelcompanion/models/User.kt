package com.adventures.storytail.travelcompanion.models

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
 actual data class User(
     actual val id: String = UUID.randomUUID().toString(),
     actual val username: String = "",
     actual val password: String = ""
)

@Serializable
 actual data class UserWithoutPassword(
     actual val id: String = UUID.randomUUID().toString(),
     actual val username: String = ""
)
