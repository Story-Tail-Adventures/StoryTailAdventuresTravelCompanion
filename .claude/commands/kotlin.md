# Kotlin Development

Write Kotlin code for the Story Tail Adventures Travel Companion — a Kotlin Multiplatform project targeting Android, iOS, and Web (Kobweb).

## Instructions

Follow the project conventions exactly. Read relevant existing files before writing new code.

**User request:** $ARGUMENTS

## Before You Start

Read the files most relevant to the request:

- **Shared logic:** `shared/src/commonMain/kotlin/com/adventures/storytail/travelcompanion/`
- **Android:** `androidApp/src/main/java/com/adventures/storytail/travelcompanion/android/`
- **Web frontend:** `site/src/jsMain/kotlin/com/adventures/storytail/travelcompanion/`
- **Web backend:** `site/src/jvmMain/kotlin/com/adventures/storytail/travelcompanion/`
- **Version catalog:** `gradle/libs.versions.toml`

## Module Architecture

```
Root
├── shared/         KMP library (commonMain, androidMain, iosMain)
├── androidApp/     Android app (Jetpack Compose, Material3)
├── iosApp/         iOS app (static framework from shared)
└── site/           Kobweb web app (jsMain + jvmMain + commonMain)
```

Package: `com.adventures.storytail.travelcompanion`
Android namespace: `com.adventures.storytail.travelcompanion.android`

## Key Versions

| Component | Version | JVM Target |
|-----------|---------|------------|
| Kotlin | 2.0.21 | — |
| AGP | 8.13.2 | — |
| Jetpack Compose | 1.7.5 | — |
| Material3 | 1.3.1 | — |
| JetBrains Compose (KMP) | 1.7.0 | — |
| Kobweb | 0.19.2 | — |
| Ktor | 2.3.12 | — |
| kotlinx.serialization | 1.7.1 | — |
| shared module | — | 17 |
| androidApp | — | 1.8 |
| site (jvmMain) | — | — |

Compiler flag: `-Xexpect-actual-classes` enabled on site module.

## Expect/Actual Pattern

Used for platform-specific implementations across modules.

**commonMain** (declare):
```kotlin
package com.adventures.storytail.travelcompanion

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
```

**androidMain** (implement):
```kotlin
class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}
actual fun getPlatform(): Platform = AndroidPlatform()
```

**iosMain** (implement):
```kotlin
import platform.UIKit.UIDevice
class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
actual fun getPlatform(): Platform = IOSPlatform()
```

For shared data models (site module):
```kotlin
// commonMain
expect class User { val id: String; val username: String; val password: String }

// jsMain
@Serializable actual data class User(actual val id: String = "", actual val username: String = "", actual val password: String = "")

// jvmMain
@Serializable actual data class User(actual val id: String = UUID.randomUUID().toString(), actual val username: String = "", actual val password: String = "")
```

## Serialization

Always use `kotlinx.serialization`:

```kotlin
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

// Data class
@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

// With JSON field mapping
@Serializable
data class AuthResponse(
    @SerialName("access_token") val accessToken: String = "",
    @SerialName("token_type") val tokenType: String = "",
    @SerialName("expires_in") val expiresIn: Int = 0,
    val user: SupabaseUser? = null
)

// Encoding/decoding
Json.decodeFromString<LoginRequest>(jsonString)
Json.encodeToString(authResponse)

// Ktor client configuration
HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
}
```

## Android Patterns

### Activity + Compose
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) { /* content */ }
            }
        }
    }
}
```

### Material3 Theme
```kotlin
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkColorScheme(...) else lightColorScheme(...)
    MaterialTheme(colorScheme = colors, typography = typography, shapes = shapes, content = content)
}
```

### Composable Functions
```kotlin
@Composable
fun GreetingView(text: String) { Text(text = text) }

@Preview
@Composable
fun DefaultPreview() { MyApplicationTheme { GreetingView("Hello!") } }
```

## Kobweb Web Patterns

### Page (file path = route)
```kotlin
@Page
@Composable
fun MyPage() {
    PageLayout { /* content */ }
}
```

### Design System
- Colors: `Theme.Primary.rgb`, `Theme.Secondary.rgb`, etc. (never hardcode hex)
- Headings: `Constants.FONT_FAMILY_HEADING` (Raleway)
- Body: `Constants.FONT_FAMILY_BODY` (Mulish)
- Default: `Constants.FONT_FAMILY` (Roboto)

### CssStyle
```kotlin
val MyStyle = CssStyle {
    base { Modifier.backgroundColor(Theme.White.rgb).transition(Transition.of("color", 300.ms)) }
    hover { Modifier.color(Theme.Primary.rgb) }
}
// Usage: MyStyle.toModifier().width(300.px)
```

### Layout
```kotlin
Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) { }
Column(modifier = Modifier.gap(16.px), horizontalAlignment = Alignment.CenterHorizontally) { }
Row(modifier = Modifier.gap(24.px), verticalAlignment = Alignment.CenterVertically) { }
```

### State
```kotlin
var value by remember { mutableStateOf(initialValue) }
```

### Input/Button
```kotlin
Input(type = InputType.Email, attrs = Modifier.width(350.px).toAttrs { placeholder("Email") })
Button(attrs = Modifier.backgroundColor(Theme.Primary.rgb).onClick { }.toAttrs()) { SpanText("Submit") }
```

### API Routes (jvmMain)
```kotlin
@Api(routeOverride = "myroute")
suspend fun myRoute(context: ApiContext) {
    val body = context.req.body?.decodeToString()?.let { Json.decodeFromString<MyRequest>(it) }
    context.res.setBodyText(Json.encodeToString(result))
}
```

### Service Registration
```kotlin
@InitApi
fun initService(context: InitApiContext) {
    context.data.add(MyService(context))
}
// Access in routes: context.data.getValue<MyService>()
```

## Coroutine Patterns

```kotlin
// Interface with suspend
interface UserRepository {
    suspend fun signIn(email: String, password: String): AuthResponse?
}

// Implementation with Ktor
override suspend fun signIn(email: String, password: String): AuthResponse? {
    return try {
        val response = client.post("$url/auth/v1/token?grant_type=password") {
            header("apikey", apiKey)
            contentType(ContentType.Application.Json)
            setBody(mapOf("email" to email, "password" to password))
        }
        if (response.status == HttpStatusCode.OK) response.body<AuthResponse>() else null
    } catch (e: Exception) {
        context.logger.error("Error: ${e.message}")
        null
    }
}
```

## Testing Patterns

```kotlin
// commonTest (multiplatform)
import kotlin.test.Test
import kotlin.test.assertTrue

class CommonGreetingTest {
    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("Hello"), "Check 'Hello' is mentioned")
    }
}

// androidUnitTest (JUnit)
import org.junit.Assert.assertTrue
import org.junit.Test

class AndroidGreetingTest {
    @Test
    fun testExample() {
        assertTrue("Check Android is mentioned", Greeting().greet().contains("Android"))
    }
}
```

## Dependency Management

All dependencies go in `gradle/libs.versions.toml` — never inline version numbers.

```toml
[versions]
mylib = "1.0.0"

[libraries]
mylib-core = { module = "com.example:mylib-core", version.ref = "mylib" }

[plugins]
mylib-plugin = { id = "com.example.mylib", version.ref = "mylib" }
```

Reference in build.gradle.kts:
```kotlin
dependencies {
    implementation(libs.mylib.core)
}
plugins {
    alias(libs.plugins.mylib.plugin)
}
```

## Rules

- Use `val` over `var` unless mutation is required
- Use data classes for value types, sealed classes for type hierarchies
- Use `@Serializable` on all network data classes
- Use structured concurrency — no `GlobalScope`
- Handle nulls explicitly — avoid `!!`
- Environment variables via `System.getenv()` only, never hardcode secrets
- Add new dependencies to `libs.versions.toml`, not inline
- Follow existing naming: camelCase for functions/properties, PascalCase for classes/composables
- Read existing code before modifying — understand the pattern before changing it