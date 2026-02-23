package com.adventures.storytail.travelcompanion.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.ObjectIdGenerator
import org.bson.types.ObjectId

@Serializable
 actual data class User(
    @SerialName("_id")
     actual val id: String = (ObjectIdGenerator().generate() as ObjectId).toHexString(),
     actual val username: String = "",
     actual val password: String = ""
)

@Serializable
 actual data class UserWithOutPassword(
    @SerialName(value = "_id")
     actual val id: String = (ObjectIdGenerator().generate() as ObjectId).toHexString(),
     actual val username: String = ""
)
