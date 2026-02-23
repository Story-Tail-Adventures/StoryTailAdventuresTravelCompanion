package com.adventures.storytail.travelcompanion.api

import com.adventures.storytail.travelcompanion.data.MongoDB
import com.adventures.storytail.travelcompanion.models.User
import com.adventures.storytail.travelcompanion.models.UserWithOutPassword
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@Api(routeOverride = "usercheck")
suspend fun userCheck(context: ApiContext) {
    try {
        val userRequest =
            context.req.body?.decodeToString()?.let { Json.decodeFromString<User>(it) }
        val user = userRequest?.let {
            context.data.getValue<MongoDB>()
                .checkUserExistence(
                    User(username = it.username, password = hashPassword(it.password))
                )
        }
        if (user != null) {
            context.res.setBodyText(
                Json.encodeToString<UserWithOutPassword>(
                    UserWithOutPassword(
                        id = user.id,
                        username = user.username,
                    )
                )
            )
        } else {
            val ex = Exception("User doesn't exist")
            context.res.setBodyText(Json.encodeToString(ex))
            context.logger.error(ex.message.toString())
        }
    } catch (ex: Exception) {
        context.logger.error(ex.message.toString())

    }
}

private fun hashPassword(password: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashBytes = messageDigest.digest(password.toByteArray(StandardCharsets.UTF_8))
    val hexString = StringBuffer()

    for (byte in hashBytes) {
        hexString.append(String.format("%02x", byte))
    }
    return hexString.toString()
}