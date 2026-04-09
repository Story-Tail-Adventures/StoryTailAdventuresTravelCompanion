package com.adventures.storytail.travelcompanion.data

import com.adventures.storytail.travelcompanion.models.AuthResponse
import io.github.cdimascio.dotenv.dotenv
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.serialization.json.Json

@InitApi
fun initSupabase(context: InitApiContext) {
    context.data.add(SupabaseDb(context))
}

class SupabaseDb(
    private val context: InitApiContext,
    supabaseUrl: String? = null,
    supabaseKey: String? = null,
    client: HttpClient? = null
) : UserRepository {

    private val env = dotenv {
        directory = "../"
        ignoreIfMissing = true
    }

    private val resolvedUrl = supabaseUrl ?: env["SUPABASE_URL"]
    private val resolvedKey = supabaseKey ?: env["SUPABASE_KEY"]

    init {
        if (resolvedUrl.isNullOrBlank() || resolvedKey.isNullOrBlank()) {
            context.logger.warn("SUPABASE_URL or SUPABASE_KEY is not set. Auth will not work.")
        } else {
            context.logger.info("Supabase configuration loaded successfully.")
        }
    }

    private val client = client ?: HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResponse? {
        if (resolvedUrl.isNullOrBlank() || resolvedKey.isNullOrBlank()) {
            context.logger.error("Supabase is not configured. Cannot sign in.")
            return null
        }
        return try {
            val response = client.post("$resolvedUrl/auth/v1/token?grant_type=password") {
                header("apikey", resolvedKey)
                header("Authorization", "Bearer $resolvedKey")
                contentType(ContentType.Application.Json)
                setBody(mapOf("email" to email, "password" to password))
            }
            if (response.status == HttpStatusCode.OK) {
                response.body<AuthResponse>()
            } else {
                context.logger.error("Supabase auth failed with status: ${response.status}")
                null
            }
        } catch (e: Exception) {
            context.logger.error("Supabase auth error: ${e.message}")
            null
        }
    }
}
