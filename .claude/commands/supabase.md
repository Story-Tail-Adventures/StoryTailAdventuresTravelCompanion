# Supabase Feature Builder

Implement a Supabase-powered feature for the Story Tail Adventures Travel Companion project, targeting **Kobweb (web)** and/or **Mobile (Android/iOS)** using the official `supabase-kt` SDK.

## Instructions

You are adding a Supabase feature to a Kotlin Multiplatform project. Determine the correct platform(s) from the user's request and follow the patterns below.

**User request:** $ARGUMENTS

## Before You Start

1. Read the current Supabase setup:
   - `site/src/jvmMain/kotlin/com/adventures/storytail/travelcompanion/data/SupabaseDb.kt` (existing backend auth)
   - `site/src/jvmMain/kotlin/com/adventures/storytail/travelcompanion/data/UserRepository.kt`
   - `site/src/jvmMain/kotlin/com/adventures/storytail/travelcompanion/models/User.kt`
2. Read `gradle/libs.versions.toml` to check if `supabase-kt` dependencies are already added
3. Read `.env.example` for required environment variables
4. Identify which platform(s) the feature targets: **Kobweb** (site module), **Mobile** (shared + androidApp + iosApp), or **Both**

## Official SDK Reference

**Library:** `io.github.jan-tennert.supabase` (supabase-kt)
**Latest version:** 3.5.0
**Docs:** https://supabase.com/docs/reference/kotlin/introduction
**GitHub:** https://github.com/supabase-community/supabase-kt

### Available Modules

| Module | Artifact | Purpose |
|--------|----------|---------|
| Auth | `auth-kt` | Authentication (email, OAuth, OTP, SSO) |
| Database | `postgrest-kt` | CRUD operations via PostgREST |
| Realtime | `realtime-kt` | Live database subscriptions |
| Storage | `storage-kt` | File upload/download/management |
| Functions | `functions-kt` | Edge function invocation |
| Compose Auth | `compose-auth` | Compose-native auth helpers |
| Compose Auth UI | `compose-auth-ui` | Pre-built auth UI components |

### Ktor Engine Per Platform

| Platform | Engine Artifact |
|----------|----------------|
| JVM (Kobweb backend) | `io.ktor:ktor-client-cio` |
| Android | `io.ktor:ktor-client-cio` |
| iOS | `io.ktor:ktor-client-darwin` |
| JS (Kobweb frontend) | `io.ktor:ktor-client-js` |

**Important:** supabase-kt 3.5.0 uses **Ktor 3.x**. This project currently uses Ktor 2.3.12. When adding the SDK, you may need to upgrade Ktor or handle version alignment via the BOM.

## Dependency Setup

### Version Catalog (`gradle/libs.versions.toml`)

Add under `[versions]`:
```toml
supabase = "3.5.0"
```

Add under `[libraries]`:
```toml
# Supabase BOM (manages all module versions)
supabase-bom = { module = "io.github.jan-tennert.supabase:bom", version.ref = "supabase" }

# Individual modules (version managed by BOM)
supabase-auth = { module = "io.github.jan-tennert.supabase:auth-kt" }
supabase-postgrest = { module = "io.github.jan-tennert.supabase:postgrest-kt" }
supabase-realtime = { module = "io.github.jan-tennert.supabase:realtime-kt" }
supabase-storage = { module = "io.github.jan-tennert.supabase:storage-kt" }
supabase-functions = { module = "io.github.jan-tennert.supabase:functions-kt" }
supabase-compose-auth = { module = "io.github.jan-tennert.supabase:compose-auth" }
supabase-compose-auth-ui = { module = "io.github.jan-tennert.supabase:compose-auth-ui" }

# Ktor engines (version managed by supabase BOM or set explicitly)
ktor-client-android = { module = "io.ktor:ktor-client-android" }
ktor-client-darwin = { module = "io.ktor:ktor-client-darwin" }
ktor-client-js = { module = "io.ktor:ktor-client-js" }
```

### Shared Module (`shared/build.gradle.kts`) — for Mobile

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation(platform(libs.supabase.bom))
        implementation(libs.supabase.auth)       // if auth needed
        implementation(libs.supabase.postgrest)   // if database needed
        implementation(libs.supabase.storage)     // if storage needed
        implementation(libs.supabase.realtime)    // if realtime needed
    }
    androidMain.dependencies {
        implementation(libs.ktor.client.cio)      // or ktor-client-android
    }
    iosMain.dependencies {
        implementation(libs.ktor.client.darwin)
    }
}
```

### Site Module (`site/build.gradle.kts`) — for Kobweb

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation(platform(libs.supabase.bom))
        implementation(libs.supabase.postgrest)   // add modules as needed
    }
    jsMain.dependencies {
        implementation(libs.supabase.auth)        // client-side auth
        implementation(libs.ktor.client.js)
    }
    jvmMain.dependencies {
        implementation(libs.supabase.auth)        // server-side auth
        implementation(libs.ktor.client.cio)
    }
}
```

## Client Initialization Patterns

### Shared Module (Mobile — commonMain)

Create in `shared/src/commonMain/kotlin/com/adventures/storytail/travelcompanion/data/SupabaseClient.kt`:

```kotlin
package com.adventures.storytail.travelcompanion.data

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.SupabaseClient

object SupabaseProvider {
    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "YOUR_SUPABASE_URL",
        supabaseKey = "YOUR_SUPABASE_ANON_KEY"
    ) {
        install(Auth)
        install(Postgrest)
        // install(Storage)
        // install(Realtime)
    }
}
```

> **Note:** For production, inject URL/key via BuildConfig (Android) or expect/actual pattern rather than hardcoding.

### Kobweb Site — JVM Backend

Create or update `site/src/jvmMain/kotlin/com/adventures/storytail/travelcompanion/data/SupabaseDb.kt`:

```kotlin
package com.adventures.storytail.travelcompanion.data

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import com.varabyte.kobweb.api.data.add

@InitApi
fun initSupabase(context: InitApiContext) {
    val url = System.getenv("SUPABASE_URL") ?: ""
    val key = System.getenv("SUPABASE_KEY") ?: ""
    if (url.isBlank() || key.isBlank()) {
        context.logger.warn("SUPABASE_URL or SUPABASE_KEY not set.")
    }
    val client = createSupabaseClient(supabaseUrl = url, supabaseKey = key) {
        install(Auth)
        install(Postgrest)
    }
    context.data.add(client)
}
```

### Kobweb Site — JS Frontend (Client-Side Auth)

```kotlin
package com.adventures.storytail.travelcompanion.data

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.SupabaseClient

object ClientSupabase {
    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "YOUR_SUPABASE_URL",
        supabaseKey = "YOUR_SUPABASE_ANON_KEY"
    ) {
        install(Auth)
    }
}
```

## Feature Implementation Patterns

### Authentication

**Sign up:**
```kotlin
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

suspend fun signUp(email: String, password: String) {
    client.auth.signUpWith(Email) {
        this.email = email
        this.password = password
    }
}
```

**Sign in:**
```kotlin
suspend fun signIn(email: String, password: String) {
    client.auth.signInWith(Email) {
        this.email = email
        this.password = password
    }
}
```

**Sign out:**
```kotlin
suspend fun signOut() {
    client.auth.signOut()
}
```

**Session state:**
```kotlin
val session = client.auth.currentSessionOrNull()
val user = client.auth.currentUserOrNull()
// Collect session changes reactively:
client.auth.sessionStatus.collect { status ->
    when (status) {
        is SessionStatus.Authenticated -> { /* logged in */ }
        is SessionStatus.NotAuthenticated -> { /* logged out */ }
        else -> {}
    }
}
```

### Database (Postgrest)

**Select all:**
```kotlin
import io.github.jan.supabase.postgrest.from

val items = client.from("table_name").select().decodeList<MyDataClass>()
```

**Select with filter:**
```kotlin
val item = client.from("table_name").select {
    filter { eq("column", value) }
}.decodeSingle<MyDataClass>()
```

**Insert:**
```kotlin
client.from("table_name").insert(myObject)
```

**Update:**
```kotlin
client.from("table_name").update(updatedFields) {
    filter { eq("id", itemId) }
}
```

**Delete:**
```kotlin
client.from("table_name").delete {
    filter { eq("id", itemId) }
}
```

### Storage

**Upload:**
```kotlin
import io.github.jan.supabase.storage.storage

val bucket = client.storage.from("bucket-name")
bucket.upload("path/file.png", fileBytes)
```

**Download:**
```kotlin
val bytes = client.storage.from("bucket-name").downloadAuthenticated("path/file.png")
```

**Public URL:**
```kotlin
val url = client.storage.from("bucket-name").publicUrl("path/file.png")
```

### Realtime

```kotlin
import io.github.jan.supabase.realtime.realtime
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.PostgresAction

val channel = client.realtime.channel("my-channel")
val flow = channel.postgresChangeFlow<PostgresAction>("public") {
    table = "table_name"
}
flow.collect { action ->
    when (action) {
        is PostgresAction.Insert -> { /* handle insert */ }
        is PostgresAction.Update -> { /* handle update */ }
        is PostgresAction.Delete -> { /* handle delete */ }
        else -> {}
    }
}
channel.subscribe()
```

### Edge Functions

```kotlin
import io.github.jan.supabase.functions.functions

val response = client.functions.invoke("function-name") {
    body = mapOf("key" to "value")
}
```

## Data Model Pattern

Define `@Serializable` data classes that match your Supabase table schema:

```kotlin
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Destination(
    val id: Int = 0,
    val name: String,
    val description: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("created_at")
    val createdAt: String? = null
)
```

Place in:
- **Mobile:** `shared/src/commonMain/kotlin/.../models/`
- **Kobweb (shared between JS/JVM):** `site/src/commonMain/kotlin/.../models/`
- **Kobweb (platform-specific):** use expect/actual if the model needs platform differences

## Environment Variables

Always read from `.env.example` to verify required variables:

| Variable | Purpose |
|----------|---------|
| `SUPABASE_URL` | Project URL (e.g., `https://xxx.supabase.co`) |
| `SUPABASE_KEY` | Anon (publishable) key for client-side use |

- **Kobweb backend (JVM):** `System.getenv("SUPABASE_URL")`
- **Kobweb frontend (JS):** Inject at build time or hardcode the anon key (it's safe — RLS protects data)
- **Android:** Use `BuildConfig` fields via `build.gradle.kts` `buildConfigField`
- **iOS:** Use a plist or expect/actual pattern

## Android-Specific Setup

Add to `androidApp/src/main/AndroidManifest.xml` (outside `<application>`):
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Checklist

- [ ] Added required supabase-kt modules to version catalog
- [ ] Added BOM platform dependency in the correct build.gradle.kts
- [ ] Added correct Ktor engine per platform
- [ ] Created/updated Supabase client initialization
- [ ] Defined `@Serializable` data models matching table schema
- [ ] Used `client.from("table").select()` pattern (not raw HTTP) for database ops
- [ ] Used `client.auth.signInWith(Email)` pattern (not raw HTTP) for auth
- [ ] Handled errors with try/catch around suspend calls
- [ ] Environment variables documented in `.env.example`
- [ ] Android: INTERNET permission in manifest
- [ ] Kobweb: registered services via `@InitApi` + `context.data.add()`