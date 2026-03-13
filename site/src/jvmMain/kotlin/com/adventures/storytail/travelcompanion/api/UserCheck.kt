package com.adventures.storytail.travelcompanion.api

import com.adventures.storytail.travelcompanion.data.SupabaseDb
import com.adventures.storytail.travelcompanion.models.LoginRequest
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Api(routeOverride = "usercheck")
suspend fun userCheck(context: ApiContext) {
    try {
        val loginRequest =
            context.req.body?.decodeToString()?.let { Json.decodeFromString<LoginRequest>(it) }

        if (loginRequest == null) {
            context.res.setBodyText(Json.encodeToString(mapOf("error" to "Invalid request body")))
            return
        }

        val authResponse = context.data.getValue<SupabaseDb>()
            .signIn(email = loginRequest.email, password = loginRequest.password)

        if (authResponse != null) {
            context.res.setBodyText(Json.encodeToString(authResponse))
        } else {
            context.res.setBodyText(Json.encodeToString(mapOf("error" to "Invalid credentials")))
        }
    } catch (ex: Exception) {
        context.logger.error(ex.message.toString())
        context.res.setBodyText(Json.encodeToString(mapOf("error" to "Internal server error")))
    }
}
