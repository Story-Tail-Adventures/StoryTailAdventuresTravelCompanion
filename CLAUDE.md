# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Story Tail Adventures Travel Companion** is a Kotlin Multiplatform (KMP) project targeting Android, iOS, and Web (via Kobweb). It is currently in an early/template state with placeholder code; actual travel companion features have not yet been implemented.

## Build & Development Commands

### Gradle (Android + Shared + iOS)

```bash
./gradlew assemble          # Build all modules without testing
./gradlew build             # Build and run all tests
./gradlew test              # Run unit tests only
./gradlew test --max-workers 1 --scan   # CI-style test run
./gradlew clean             # Delete build directories
./gradlew tasks             # List all available tasks
```

### Web (Kobweb — run from `site/` directory)

```bash
cd site
kobweb run                  # Start dev server at http://localhost:8080 (press Q to stop)
kobweb export               # Build for production
kobweb run --env prod       # Run in production mode
kobweb run --env prod --notty  # Headless production mode
```

### Docker (Web)

The `Dockerfile` does a multi-stage build using Java 17 + Kobweb CLI 0.9.13, exporting and serving the Kobweb site.

## Module Architecture

```
Root
├── shared/         Kotlin Multiplatform library — shared business logic
├── androidApp/     Android application (depends on shared)
├── iosApp/         iOS application (depends on shared via static framework)
└── site/           Kobweb full-stack web app (JS frontend + JVM backend)
```

### shared

- **commonMain** — platform-agnostic code (`Greeting`, `Platform` interface)
- **androidMain / iosMain** — `actual` implementations of `expect` declarations
- **commonTest / androidUnitTest / iosTest** — tests per source set

Expect/actual pattern is used for platform-specific behavior:
```kotlin
// commonMain
expect fun getPlatform(): Platform

// androidMain
actual fun getPlatform(): Platform = AndroidPlatform()  // returns SDK version string
```

### androidApp

- Entry point: [androidApp/src/main/java/com/adventures/storytail/travelcompanion/android/MainActivity.kt](androidApp/src/main/java/com/adventures/storytail/travelcompanion/android/MainActivity.kt)
- Theme: Material3 with dark/light support in [MyApplicationTheme.kt](androidApp/src/main/java/com/adventures/storytail/travelcompanion/android/MyApplicationTheme.kt)
- Min SDK: 27 · Target/Compile SDK: 34 · JVM target: 1.8
- Namespace: `com.adventures.storytail.travelcompanion.android`

### site (Kobweb)

- **jsMain/pages/** — file-based routing; each `@Page`-annotated file becomes a route
- **jsMain/components/** — reusable Compose HTML components
- **jvmMain/api/** — JVM backend API routes (currently empty placeholders)
- Entry point: [site/src/jsMain/kotlin/com/adventures/storytail/travelcompanion/AppEntry.kt](site/src/jsMain/kotlin/com/adventures/storytail/travelcompanion/AppEntry.kt)

## Key Dependencies

Managed via the version catalog at [gradle/libs.versions.toml](gradle/libs.versions.toml).

| Dependency | Version |
|---|---|
| Kotlin | 2.0.21 |
| Android Gradle Plugin | 8.7.0 |
| Jetpack Compose | 1.7.3 |
| Compose Material3 | 1.3.0 |
| JetBrains Compose (KMP) | 1.6.11 |
| Kobweb | 0.19.2 |
| Gradle | 8.9 |

## CI/CD

GitHub Actions workflow at [.github/workflows/build.yml](.github/workflows/build.yml):
- **Build job:** `./gradlew assemble --scan` on push/PR to `main`
- **Test job:** `./gradlew test --max-workers 1 --scan` on push/PR to `main`, uploads results to Codecov
- Concurrency is configured to cancel in-progress runs for the same ref

Dependabot is enabled for weekly Gradle dependency updates.

## Gradle Configuration

- JVM memory: 2048M (Gradle daemon + Kotlin daemon)
- Gradle build cache and configuration cache are both **enabled**
- Java toolchain: temurin-17 (see [.java-version](.java-version))
- Kotlin JVM target for `shared`: 17; for `androidApp`: 1.8
